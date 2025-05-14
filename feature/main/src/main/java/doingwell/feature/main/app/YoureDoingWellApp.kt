package doingwell.feature.main.app

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import doingwell.feature.daily.navigation.dailyNavGraph
import com.hegunhee.model.user.UserData
import doingwell.feature.addRecord.navigation.addRecordNavGraph
import doingwell.feature.addphoto.navigation.addPhotoNavGraph
import doingwell.feature.main.app.auth.AuthState
import doingwell.feature.main.app.auth.YoureDoingWellAuthViewModel
import doingwell.feature.main.app.bottom.MainBottomNavigation
import doingwell.feature.main.app.bottom.SETTING_ROUTE
import doingwell.feature.main.screen.navigation.MAIN_ROUTE
import doingwell.feature.main.screen.navigation.mainNavGraph
import doingwell.feature.signin.navigation.signInNavGraph
import kotlinx.coroutines.launch

@Composable
fun YoureDoingWellApp(
    youreDoingWellAppState: YoureDoingWellAppState = rememberYoureDoingWellAppState(),
    youreDoingWellAuthViewModel: YoureDoingWellAuthViewModel = hiltViewModel(),
) {
    val userData : UserData? = youreDoingWellAuthViewModel.userData.collectAsStateWithLifecycle().value
    Scaffold(
        bottomBar = {
            if(youreDoingWellAppState.isBottomNavigationRoute()) {
                MainBottomNavigation(backStackEntry = youreDoingWellAppState.currentDestination, onItemClick = youreDoingWellAppState::navigateBottomNavigation)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = youreDoingWellAppState.navController,
            startDestination = MAIN_ROUTE
        ) {
            mainNavGraph(
                paddingValues = paddingValues,
                userData = userData,
                popSignInScreen = youreDoingWellAppState::navigateToSignIn,
                popDailyScreen = youreDoingWellAppState::navigateToDaily,
            )

            signInNavGraph(
                paddingValues = paddingValues,
                onClickSignInButton = youreDoingWellAuthViewModel::signInWithEmailAndPassword,
                onClickSignUpScreenButton = youreDoingWellAppState::navigateToSignUp,
                onClickSignUpButton = youreDoingWellAuthViewModel::signUpWithEmail,
                onClickPasswordResetButton = youreDoingWellAppState::navigateToResetPasswordReset,
                onClickEmailSend = youreDoingWellAuthViewModel::resetPasswordWithEmail,
                onClickGoogleSignIn = youreDoingWellAuthViewModel::signInWithGoogle,
            )

            dailyNavGraph(
                paddingValues = paddingValues,
                userData = userData,
                onClickSignOut = youreDoingWellAuthViewModel::signOut,
            )

            addRecordNavGraph(
                paddingValues = paddingValues,
                userData = userData,
                onClickSignOut = youreDoingWellAuthViewModel::signOut,
                onClickAddPhoto = youreDoingWellAppState::navigateAddPhoto,
                getAddedPhoto = youreDoingWellAppState::getPhotoCurrentStackEntry,
            )

            addPhotoNavGraph(
                paddingValues = paddingValues,
                onClickBackStack = youreDoingWellAppState::popBackStack,
                onClickAddPhotos = youreDoingWellAppState::setPhotoBackStackEntry,
            )

            composable(SETTING_ROUTE) {
                if(userData == null) {
                    youreDoingWellAuthViewModel.signOut()
                } else {
                    Column {
                        Text(userData.toString())
                        Text("setting_screen")
                    }
                }
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            launch {
                youreDoingWellAuthViewModel.authState.collect {
                    when(it) {
                        AuthState.SignOut -> {
                            youreDoingWellAppState.navigateToMain()
                        }
                        AuthState.SignIn -> {
                            youreDoingWellAppState.navigateToDaily()
                        }
                    }
                }
            }
            launch {
                youreDoingWellAuthViewModel.toastMessage.collect {stringRes ->
                    Toast.makeText(context, context.getString(stringRes), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun rememberYoureDoingWellAppState(
    navController: NavHostController = rememberNavController(),
): YoureDoingWellAppState {
    return remember(navController) {
        YoureDoingWellAppState(
            navController
        )
    }
}
