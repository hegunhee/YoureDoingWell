package doingwell.feature.main.screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hegunhee.model.user.UserData
import doingwell.feature.main.screen.MainScreenRoot

const val MAIN_ROUTE = "MAIN_ROUTE"

fun NavController.navigateToMain() {
    navigate(MAIN_ROUTE) {
        popUpTo(graph.startDestinationId) { inclusive = true }
    }
}

fun NavGraphBuilder.mainNavGraph(
    paddingValues: PaddingValues,
    userData: UserData?,
    popSignInScreen: () -> Unit,
    popDailyScreen: () -> Unit,
) {
    composable(MAIN_ROUTE) {
        MainScreenRoot(
            userData = userData,
            popSignInScreen = popSignInScreen,
            popDailyScreen = popDailyScreen,
        )
    }
}
