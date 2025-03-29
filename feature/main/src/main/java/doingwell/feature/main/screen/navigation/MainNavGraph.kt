package doingwell.feature.main.screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hegunhee.model.user.UserData
import doingwell.feature.main.screen.MainScreenRoot

const val MAIN_ROUTE = "MAIN_ROUTE"

fun NavGraphBuilder.mainNavGraph(
    paddingValues: PaddingValues,
    userData: UserData?,
    popSignInScreen: () -> Unit,
) {
    composable(MAIN_ROUTE) {
        MainScreenRoot(
            userData = userData,
            popSignInScreen = popSignInScreen,
        )
    }
}
