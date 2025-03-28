package doingwell.feature.main.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import doingwell.feature.main.screen.navigation.navigateToMain
import doingwell.feature.signin.navigation.SIGN_IN_ROUTE
import doingwell.feature.signin.navigation.navigateSignIn
import doingwell.feature.signin.navigation.navigateSignUp

class YoureDoingWellAppState(
    val navController: NavHostController
) {

    val currentDestination: State<NavBackStackEntry?>
        @Composable get() = navController.currentBackStackEntryAsState()

    fun navigateToSignIn() {
        navController.navigateSignIn()
    }

    fun navigateToSignUp() {
        navController.navigateSignUp()
    }

    fun navigateToDaily() {
        navController.navigate("DAILY") {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
    }

    fun navigateToMain() {
        navController.navigateToMain()
    }
    fun popBackStack() {
        navController.popBackStack()
    }
}
