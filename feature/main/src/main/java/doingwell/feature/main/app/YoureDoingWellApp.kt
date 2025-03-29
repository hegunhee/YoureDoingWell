package doingwell.feature.main.app

import android.content.Context
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import doingwell.feature.main.app.auth.YoureDoingWellAuthViewModel
import doingwell.feature.main.screen.navigation.MAIN_ROUTE
import doingwell.feature.main.screen.navigation.mainNavGraph
import doingwell.feature.signin.navigation.signInNavGraph

@Composable
fun YoureDoingWellApp(
    youreDoingWellAppState: YoureDoingWellAppState = rememberYoureDoingWellAppState(),
    youreDoingWellAuthViewModel: YoureDoingWellAuthViewModel = hiltViewModel(),
) {
    Scaffold { paddingValues ->
        NavHost(
            navController = youreDoingWellAppState.navController,
            startDestination = MAIN_ROUTE
        ) {
            mainNavGraph(
                paddingValues = paddingValues,
                popSignInScreen = youreDoingWellAppState::navigateToSignIn,
            )

            signInNavGraph(
                paddingValues = paddingValues,
                onClickSignInButton = youreDoingWellAuthViewModel::signInWithEmailAndPassword,
                onClickSignUpScreenButton = youreDoingWellAppState::navigateToSignUp,
                popUpBack = youreDoingWellAppState::popBackStack,
                onClickSignUpButton = youreDoingWellAuthViewModel::signUpWithEmail,
            )

            composable("DAILY") {
                Button({ youreDoingWellAuthViewModel.signOut() }) {
                    Text("정상 로그인 $userData 로그아웃 버튼")
                }
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            youreDoingWellAuthViewModel.authState.collect {
                when(it) {
                    AuthState.SignOut -> {
                        youreDoingWellAppState.navController.navigate(MAIN_ROUTE)
                    }
                    AuthState.SignIn -> {
                        youreDoingWellAppState.navController.navigate("DAILY")
                    }
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
