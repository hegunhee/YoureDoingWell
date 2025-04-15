package doingwell.feature.signin.passwordReset

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hegunhee.youredoingwell.ui.theme.MainGreen
import com.hegunhee.youredoingwell.ui.theme.Typography
import doingwell.feature.signin.R
import doingwell.feature.signin.getEmailBoarderColor
import doingwell.feature.signin.isValidEmail

@Composable
fun PasswordResetRootScreen(
    paddingValues: PaddingValues,
    email: String,
    onClickEmailSend: (String) -> Unit,
) {
    val (emailText, onEmailValueChanged) = rememberSaveable { mutableStateOf(email) }
    val (isEmailSend, onIsEmailSendChanged) = rememberSaveable { mutableStateOf(false) }

    PasswordResetScreen(
        paddingValues = paddingValues,
        email = emailText,
        isEmailSend = isEmailSend,
        onIsEmailSendChanged = onIsEmailSendChanged,
        onEmailValueChanged = onEmailValueChanged,
        onClickEmailSend = onClickEmailSend,
    )
}

@Composable
internal fun PasswordResetScreen(
    paddingValues: PaddingValues,
    email: String,
    isEmailSend: Boolean,
    onEmailValueChanged: (String) -> Unit,
    onClickEmailSend : (String) -> Unit,
    onIsEmailSendChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val itemModifier = modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp)
    val emailBorderColor = getEmailBoarderColor(email)

    Column {
        Text(
            stringResource(R.string.reset_password),
            style = Typography.titleLarge,
            modifier = itemModifier.padding(top = 50.dp),
        )

        Text(
            stringResource(R.string.enter_email),
            modifier = itemModifier,
        )

        OutlinedTextField(
            email,
            onValueChange = onEmailValueChanged,
            placeholder = { Text(stringResource(R.string.enter_email)) },
            modifier = itemModifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = emailBorderColor,
                unfocusedBorderColor = emailBorderColor,
            ),
        )

        Button(
            {
                onClickEmailSend(email)
                onIsEmailSendChanged(true)
            },
            enabled = !isEmailSend && isValidEmail(email),
            colors = ButtonDefaults.buttonColors(containerColor = MainGreen),
            modifier = itemModifier.fillMaxWidth(),
        ) {
            Text(
                stringResource(
                    if (isEmailSend) R.string.password_reset_email_send
                    else R.string.reset_password
                )
            )
        }
    }
}

@Preview
@Composable
private fun PasswordResetScreenPreview() {
    val (emailText, onEmailValueChanged) = rememberSaveable { mutableStateOf("gunhee007/2@gmail.com") }
    val (isEmailSend, onIsEmailSendChanged) = rememberSaveable { mutableStateOf(false) }

    PasswordResetScreen(
        paddingValues = PaddingValues(),
        email = emailText,
        isEmailSend = isEmailSend,
        onEmailValueChanged = onEmailValueChanged,
        onIsEmailSendChanged = onIsEmailSendChanged,
        onClickEmailSend = {},
    )
}