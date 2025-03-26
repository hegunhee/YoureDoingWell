package doingwell.feature.main.app

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import doingwell.feature.main.screen.navigation.MAIN_ROUTE
import doingwell.feature.main.screen.navigation.mainNavGraph
import doingwell.feature.signin.navigation.signInNavGraph

@Composable
fun YoureDoingWellApp(
    youreDoingWellAppState: YoureDoingWellAppState = rememberYoureDoingWellAppState()
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
