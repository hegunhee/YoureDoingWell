package doingwell.feature.main.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

class YoureDoingWellAppState(
    val navController: NavHostController
) {

    val currentDestination: State<NavBackStackEntry?>
        @Composable get() = navController.currentBackStackEntryAsState()

    fun popBackStack() {
        navController.popBackStack()
    }
}
