package doingwell.feature.signin

import androidx.compose.ui.graphics.Color
import com.hegunhee.youredoingwell.ui.theme.MainGreen

fun getEmailBoarderColor(email: String): Color {
    return if (isValidEmail(email)) {
        MainGreen
    } else {
        if (email.isEmpty()) {
            Color.Unspecified
        } else {
            Color.Red
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return email.matches(emailRegex)
}

fun getSignUpPasswordBorderColor(password: String, recheckPassword: String): Color {
    return if (password.isBlank()) {
        Color.Unspecified
    } else if (password != recheckPassword) {
        Color.Red
    } else {
        MainGreen
    }
}
