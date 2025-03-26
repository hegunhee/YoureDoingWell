package doingwell.feature.main.screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import doingwell.feature.main.screen.MainScreenRoot

const val MAIN_ROUTE = "MAIN_ROUTE"

fun NavGraphBuilder.mainNavGraph(
    paddingValues: PaddingValues,
    popSignInScreen: () -> Unit,
) {
    composable(MAIN_ROUTE) {
        MainScreenRoot(
            popSignInScreen = popSignInScreen,
        )
    }
}
