package doingwell.feature.main.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import doingwell.feature.daily.navigation.navigateDaily
import doingwell.feature.main.app.bottom.BottomNavigationItem
import doingwell.feature.main.screen.navigation.navigateToMain
import doingwell.feature.signin.navigation.navigatePasswordReset
import doingwell.feature.signin.navigation.navigateSignIn
import doingwell.feature.signin.navigation.navigateSignUp

class YoureDoingWellAppState(
    val navController: NavHostController
) {

    val currentDestination: State<NavBackStackEntry?>
        @Composable get() = navController.currentBackStackEntryAsState()

    @Composable
    fun isBottomNavigationRoute() : Boolean{
        return BottomNavigationItem.items.map { it.screenRoute }.contains(currentDestination.value?.destination?.route ?: "")
    }

    fun navigateBottomNavigation(screenRoute: String) {
        navController.navigate(screenRoute) {
            navController.graph.startDestinationRoute?.let {
                popUpTo(it) { saveState }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

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
