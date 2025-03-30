package doingwell.feature.signin.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import doingwell.feature.signin.passwordReset.PasswordResetRootScreen
import doingwell.feature.signin.signin.SignInRootScreen
import doingwell.feature.signin.signup.SignUpRootScreen

const val SIGN_IN_ROUTE = "SIGN_IN_ROUTE"

const val SIGN_UP_ROUTE = "SIGN_UP_ROUTE"

fun resetPasswordRoute(email: String) : String {
    return "RESET_PASSWORD_ROUTE/$email"
}

fun NavController.navigateSignIn() {
    navigate(SIGN_IN_ROUTE) {
        popUpTo(graph.id) { inclusive = true }
    }
}

fun NavController.navigateSignUp() {
    navigate(SIGN_UP_ROUTE)
}

fun NavController.navigatePasswordReset(email: String) {
    navigate(resetPasswordRoute(email))
}

fun NavGraphBuilder.signInNavGraph(
    paddingValues: PaddingValues,
    onClickSignInButton: (String, String) -> Unit,
    onClickSignUpScreenButton : () -> Unit,
    onClickSignUpButton: (String, String, String) -> Unit,
    onClickPasswordResetButton: (String) -> Unit,
) {
    composable(SIGN_IN_ROUTE) {
        SignInRootScreen(
            paddingValues,
            onClickSignInButton = onClickSignInButton,
            onClickSignUpScreenButton,
            onClickPasswordResetButton = onClickPasswordResetButton,
        )
    }
    composable(SIGN_UP_ROUTE) {
        SignUpRootScreen(
            paddingValues = paddingValues,
            onClickSignUpButton = onClickSignUpButton
        )
    }
    composable(route = resetPasswordRoute("{email}"),
        arguments = listOf(
            navArgument("email") {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val email = navBackStackEntry.arguments?.getString("email") ?: ""
        PasswordResetRootScreen(
            paddingValues = paddingValues,
            email = email,
            onClickEmailSend = {},
        )
    }
}