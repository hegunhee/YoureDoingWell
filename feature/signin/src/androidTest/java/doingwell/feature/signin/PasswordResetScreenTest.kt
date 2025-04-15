package doingwell.feature.signin

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import doingwell.feature.signin.passwordReset.PasswordResetScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PasswordResetScreenTest {

    @get:Rule
    val sut = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = sut.activity.baseContext
    }

    @Test
    fun givenInValidEmail_when_shownEnabledButton() {
        sut.setContent {
            val isValidEmailText = ""
            val (emailText, onEmailValueChanged) = rememberSaveable { mutableStateOf(isValidEmailText) }
            val (isEmailSend, onIsEmailSendChanged) = rememberSaveable { mutableStateOf(false) }

            ShowPasswordResetScreen(
                email = emailText,
                isEmailSend = isEmailSend,
                onEmailValueChanged = onEmailValueChanged,
                onIsEmailSendChanged = onIsEmailSendChanged
            )
        }

        sut.onNode(hasText(context.getString(R.string.reset_password)) and hasClickAction())
            .assertIsNotEnabled()
    }

    @Test
    fun givenValidEmail_when_shownClickableButton() {
        sut.setContent {
            val validEmailText = "gunhee0072@gmail.com"
            val (emailText, onEmailValueChanged) = rememberSaveable { mutableStateOf(validEmailText) }
            val (isEmailSend, onIsEmailSendChanged) = rememberSaveable { mutableStateOf(false) }

            ShowPasswordResetScreen(
                email = emailText,
                isEmailSend = isEmailSend,
                onEmailValueChanged = onEmailValueChanged,
                onIsEmailSendChanged = onIsEmailSendChanged
            )
        }

        sut.onNode(hasText(context.getString(R.string.reset_password)) and hasClickAction())
            .assertIsEnabled()
    }

    @Test
    fun givenValidEmail_whenClickButton_shownSendEmailText() {
        sut.setContent {
            val validEmailText = "gunhee0072@gmail.com"
            val (emailText, onEmailValueChanged) = rememberSaveable { mutableStateOf(validEmailText) }
            val (isEmailSend, onIsEmailSendChanged) = rememberSaveable { mutableStateOf(false) }

            ShowPasswordResetScreen(
                email = emailText,
                isEmailSend = isEmailSend,
                onEmailValueChanged = onEmailValueChanged,
                onIsEmailSendChanged = onIsEmailSendChanged
            )
        }

        sut.onNode(hasText(context.getString(R.string.reset_password)) and hasClickAction())
            .assertIsEnabled()
            .performClick()

        sut.onNodeWithText(context.getString(R.string.password_reset_email_send))
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Composable
    fun ShowPasswordResetScreen(
        paddingValues: PaddingValues = PaddingValues(),
        email: String,
        isEmailSend: Boolean,
        onEmailValueChanged: (String) -> Unit,
        onClickEmailSend: (String) -> Unit = {},
        onIsEmailSendChanged: (Boolean) -> Unit = {},
        modifier: Modifier = Modifier,
    ) {
        PasswordResetScreen(
            paddingValues = paddingValues,
            email = email,
            isEmailSend = isEmailSend,
            onEmailValueChanged = onEmailValueChanged,
            onClickEmailSend = onClickEmailSend,
            onIsEmailSendChanged = onIsEmailSendChanged,
            modifier = modifier,
        )

    }

}
