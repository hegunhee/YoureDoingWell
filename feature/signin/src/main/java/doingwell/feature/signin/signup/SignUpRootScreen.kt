package doingwell.feature.signin.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hegunhee.youredoingwell.ui.theme.MainGreen
import com.hegunhee.youredoingwell.ui.theme.Typography
import doingwell.core.ui.text.TitleText
import doingwell.feature.signin.R
import doingwell.feature.signin.getEmailBoarderColor
import doingwell.feature.signin.getSignUpPasswordBorderColor

@Composable
fun SignUpRootScreen(
    paddingValues: PaddingValues,
    onClickSignUpButton: (String, String, String) -> Unit,
) {
    val (emailText, onEmailTextChanged) = rememberSaveable { mutableStateOf("") }
    val (passwordText, onPasswordTextChanged) = rememberSaveable { mutableStateOf("") }
    val (recheckPasswordText, onRecheckPasswordTextChanged) = rememberSaveable { mutableStateOf("") }

    SignUpScreen(
        paddingValues = paddingValues,
        emailText = emailText,
        passwordText = passwordText,
        recheckPasswordText = recheckPasswordText,
        onEmailTextChanged = onEmailTextChanged,
        onPasswordTextChanged = onPasswordTextChanged,
        onRecheckPasswordTextChanged = onRecheckPasswordTextChanged,
        onClickSignUpButton = onClickSignUpButton,
    )
}

@Composable
internal fun SignUpScreen(
    paddingValues: PaddingValues,
    emailText: String,
    passwordText: String,
    recheckPasswordText: String,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onRecheckPasswordTextChanged: (String) -> Unit,
    onClickSignUpButton: (String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (passwordVisible, onChangedPasswordVisible) = remember { mutableStateOf(false) }
    val passwordVisibleImage = if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff

    val emailBorderColor = getEmailBoarderColor(emailText)

    val passwordBorderColor = getSignUpPasswordBorderColor(passwordText, recheckPasswordText)


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        Spacer(modifier = modifier.padding(top = 100.dp))
        TitleText(
            textStyle = Typography.titleLarge,
        )

        Spacer(modifier = modifier.padding(top = 15.dp))
        val itemModifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .padding(top = 10.dp)

        OutlinedTextField(
            emailText,
            onValueChange = onEmailTextChanged,
            placeholder = { Text(stringResource(R.string.enter_email)) },
            modifier = itemModifier,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = emailBorderColor,
                unfocusedBorderColor = emailBorderColor,
            ),
        )

        OutlinedTextField(
            passwordText,
            onValueChange = onPasswordTextChanged,
            placeholder = { Text(stringResource(R.string.enter_password)) },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton({ onChangedPasswordVisible(!passwordVisible) }) {
                    Icon(imageVector = passwordVisibleImage, contentDescription = stringResource(R.string.visible_password))
                }
            },
            modifier = itemModifier,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = passwordBorderColor,
                unfocusedBorderColor = passwordBorderColor,
            )
        )

        OutlinedTextField(
            recheckPasswordText,
            onValueChange = onRecheckPasswordTextChanged,
            placeholder = { Text(stringResource(R.string.re_check_password)) },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            modifier = itemModifier,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = passwordBorderColor,
                unfocusedBorderColor = passwordBorderColor,
            )
        )

        Button(
            onClick = { onClickSignUpButton(emailText, passwordText, recheckPasswordText) },
            colors = ButtonDefaults.buttonColors(containerColor = MainGreen),
            modifier = itemModifier,
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    val (emailText, onEmailTextChanged) = rememberSaveable { mutableStateOf("") }
    val (passwordText, onPasswordTextChanged) = rememberSaveable { mutableStateOf("") }
    val (recheckPasswordText, onRecheckPasswordTextChanged) = rememberSaveable { mutableStateOf("") }

    SignUpScreen(
        paddingValues = PaddingValues(),
        emailText = emailText,
        passwordText = passwordText,
        recheckPasswordText = recheckPasswordText,
        onEmailTextChanged = onEmailTextChanged,
        onPasswordTextChanged = onPasswordTextChanged,
        onRecheckPasswordTextChanged = onRecheckPasswordTextChanged,
        onClickSignUpButton = { email, password, recheckPassword-> },
    )
}
