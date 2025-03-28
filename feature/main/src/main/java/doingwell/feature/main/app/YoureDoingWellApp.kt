package doingwell.feature.main.app

import android.content.Context
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import doingwell.feature.main.screen.navigation.MAIN_ROUTE
import doingwell.feature.main.screen.navigation.mainNavGraph
import doingwell.feature.signin.navigation.signInNavGraph
import kotlinx.coroutines.CoroutineScope

@Composable
fun YoureDoingWellApp(
    youreDoingWellAppState: YoureDoingWellAppState = rememberYoureDoingWellAppState(),
    youreDoingWellAuthState: YoureDoingWellAuthState = rememberYoureDoingWellAuthState(),
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
                onClickSignUpScreenButton = youreDoingWellAppState::navigateToSignUp,
                popUpBack = youreDoingWellAppState::popBackStack,
                onClickSignUpButton = youreDoingWellAuthState::signUpWithEmail,
            )
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

@Composable
fun rememberYoureDoingWellAuthState(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): YoureDoingWellAuthState {
    return remember(context, coroutineScope) {
        YoureDoingWellAuthState(context, coroutineScope)
    }
}