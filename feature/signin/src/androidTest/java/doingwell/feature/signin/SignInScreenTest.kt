package doingwell.feature.signin

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import doingwell.feature.signin.signin.SignInScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInScreenTest {

    @get:Rule
    val sut = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = sut.activity.baseContext
    }

    @Test
    fun givenEmptyData_whenScreening_shownEmptyScreen() {
        sut.setContent {
            val (emailText, onEmailTextChanged) = remember { mutableStateOf("") }
            val (passwordText, onPasswordTextChanged) = remember { mutableStateOf("") }

            ShowSignInScreen(
                emailText = emailText,
                onEmailTextChanged = onEmailTextChanged,
                passwordText = passwordText,
                onPasswordTextChanged = onPasswordTextChanged,
            )
        }

        sut.onNodeWithText(context.getString(R.string.enter_email))
            .assertIsDisplayed()

        sut.onNodeWithText(context.getString(R.string.enter_password))
            .assertIsDisplayed()

    }

    @Test
    fun givenPasswordText_whenClickPasswordVisibleButton_shownPasswordText() {
        val password = "123123"
        sut.setContent {
            val (emailText, onEmailTextChanged) = remember { mutableStateOf("") }
            val (passwordText, onPasswordTextChanged) = remember { mutableStateOf(password) }

            ShowSignInScreen(
                emailText = emailText,
                onEmailTextChanged = onEmailTextChanged,
                passwordText = passwordText,
                onPasswordTextChanged = onPasswordTextChanged,
            )
        }

        sut.onNodeWithText(password)
            .assertIsNotDisplayed()

        sut.onNodeWithContentDescription(context.getString(R.string.visible_password))
            .performClick()

        sut.onNodeWithText(password)
            .assertIsDisplayed()
    }

    @Test
    fun givenEmpty_whenScreening_shownOneClickLoginIcons() {
        sut.setContent {
            val (emailText, onEmailTextChanged) = remember { mutableStateOf("") }
            val (passwordText, onPasswordTextChanged) = remember { mutableStateOf("") }

            ShowSignInScreen(
                emailText = emailText,
                onEmailTextChanged = onEmailTextChanged,
                passwordText = passwordText,
                onPasswordTextChanged = onPasswordTextChanged,
            )
        }

        sut.onNodeWithContentDescription(context.getString(R.string.google_auth_content_description))
            .assertIsDisplayed()
    }

    @Composable
    fun ShowSignInScreen(
        paddingValues: PaddingValues = PaddingValues(),
        emailText: String,
        passwordText: String,
        onEmailTextChanged: (String) -> Unit,
        onPasswordTextChanged: (String) -> Unit,
        onClickSignInButton: (String, String) -> Unit = { _, _ -> },
        onClickSignUpScreenButton: () -> Unit = {},
        onClickPasswordResetButton: (String) -> Unit = {},
        onClickGoogleAuthButton: () -> Unit = {},
        modifier: Modifier = Modifier,
    ) {
        SignInScreen(
            paddingValues = paddingValues,
            emailText = emailText,
            passwordText = passwordText,
            onEmailTextChanged = onEmailTextChanged,
            onPasswordTextChanged = onPasswordTextChanged,
            onClickSignInButton = onClickSignInButton,
            onClickSignUpScreenButton = onClickSignUpScreenButton,
            onClickPasswordResetButton = onClickPasswordResetButton,
            onClickGoogleAuthButton = onClickGoogleAuthButton,
            modifier = modifier
        )
    }
}
