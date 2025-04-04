package doingwell.feature.main.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hegunhee.daily.navigation.navigateDaily
import doingwell.feature.main.screen.navigation.navigateToMain
import doingwell.feature.signin.navigation.navigatePasswordReset
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
        navController.navigateDaily()
    }

    fun navigateToResetPasswordReset(email: String) {
        navController.navigatePasswordReset(email)
    }

    fun navigateToMain() {
        navController.navigateToMain()
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}
