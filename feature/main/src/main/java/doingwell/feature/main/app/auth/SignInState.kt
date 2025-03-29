package doingwell.feature.main.app.auth

import com.hegunhee.model.user.UserData

data class SignInState(
    val isSignIn: Boolean,
    val userData: UserData?,
    val errorMessage: String?,
) {
    companion object {
        val DEFAULT = SignInState(
            isSignIn = false,
            userData = null,
            errorMessage = null,
        )
    }
}
