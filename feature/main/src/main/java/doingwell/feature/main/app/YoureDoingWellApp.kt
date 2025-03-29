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
