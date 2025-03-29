package doingwell.feature.main.app.auth

sealed interface AuthState {
    data object SignIn : AuthState
    data object SignOut : AuthState
}
