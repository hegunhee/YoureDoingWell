package doingwell.feature.signin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import doingwell.feature.signin.SignInRootScreen

const val SIGN_IN_ROUTE = "SIGN_IN_ROUTE"

fun NavController.navigateSignIn() {
    navigate(SIGN_IN_ROUTE) {
        popUpTo(graph.startDestinationId) { inclusive = true}
    }
}

fun NavGraphBuilder.signInNavGraph(
    paddingValues: PaddingValues
) {
    composable(SIGN_IN_ROUTE) {
        SignInRootScreen(paddingValues)
    }
}