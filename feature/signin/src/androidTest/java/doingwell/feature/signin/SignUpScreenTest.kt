package doingwell.feature.signin

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import doingwell.feature.signin.signup.SignUpScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpScreenTest {

    @get:Rule
    val sut = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = sut.activity.baseContext
    }

    @Test
    fun givenEmptyData_whenScreening_shownHintText() {
        sut.setContent {
            val (emailText, onEmailTextChanged) = rememberSaveable { mutableStateOf("") }
            val (passwordText, onPasswordTextChanged) = rememberSaveable { mutableStateOf("") }
            val (recheckPasswordText, onRecheckPasswordTextChanged) = rememberSaveable {
                mutableStateOf(
                    ""
                )
            }

            ShowSignUpScreen(
                emailText = emailText,
                passwordText = passwordText,
                recheckPasswordText = recheckPasswordText,
                onEmailTextChanged = onEmailTextChanged,
                onPasswordTextChanged = onPasswordTextChanged,
                onRecheckPasswordTextChanged = onRecheckPasswordTextChanged,
            )
        }

        sut.onNodeWithText(context.getString(R.string.enter_email))
            .assertIsDisplayed()

        sut.onNodeWithText(context.getString(R.string.enter_password))
            .assertIsDisplayed()

        sut.onNodeWithText(context.getString(R.string.re_check_password))
            .assertIsDisplayed()
    }

    @Test
    fun givenPasswordRecheckPassword_whenClickPasswordVisibleButton_shownPasswordRecheckPassword() {
        val password = "123"
        val recheckPassword = "1234"
        sut.setContent {
            val (emailText, onEmailTextChanged) = rememberSaveable { mutableStateOf("") }
            val (passwordText, onPasswordTextChanged) = rememberSaveable { mutableStateOf(password) }
            val (recheckPasswordText, onRecheckPasswordTextChanged) = rememberSaveable { mutableStateOf(recheckPassword) }

            ShowSignUpScreen(
                emailText = emailText,
                passwordText = passwordText,
                recheckPasswordText = recheckPasswordText,
                onEmailTextChanged = onEmailTextChanged,
                onPasswordTextChanged = onPasswordTextChanged,
                onRecheckPasswordTextChanged = onRecheckPasswordTextChanged,
            )
        }

        sut.onNodeWithText(password)
            .assertIsNotDisplayed()

        sut.onNodeWithText(recheckPassword)
            .assertIsNotDisplayed()

        sut.onNodeWithContentDescription(context.getString(R.string.visible_password))
            .performClick()

        sut.onNodeWithText(password)
            .assertIsDisplayed()


        sut.onNodeWithText(recheckPassword)
            .assertIsDisplayed()

    }

    @Composable
    fun ShowSignUpScreen(
        paddingValues: PaddingValues = PaddingValues(),
        emailText: String,
        passwordText: String,
        recheckPasswordText: String,
        onEmailTextChanged: (String) -> Unit,
        onPasswordTextChanged: (String) -> Unit,
        onRecheckPasswordTextChanged: (String) -> Unit = {},
        onClickSignUpButton: (String, String, String) -> Unit = { _, _, _ -> },
        modifier: Modifier = Modifier,
    ) {
        SignUpScreen(
            paddingValues = paddingValues,
            emailText = emailText,
            passwordText = passwordText,
            recheckPasswordText = recheckPasswordText,
            onEmailTextChanged = onEmailTextChanged,
            onPasswordTextChanged = onPasswordTextChanged,
            onRecheckPasswordTextChanged = onRecheckPasswordTextChanged,
            onClickSignUpButton = onClickSignUpButton,
            modifier = modifier,
        )
    }
}
