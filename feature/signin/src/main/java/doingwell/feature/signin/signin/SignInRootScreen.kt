package doingwell.feature.signin.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import doingwell.feature.signin.isValidEmail

@Composable
fun SignInRootScreen(
    paddingValues: PaddingValues,
    onClickSignInButton: (String, String) -> Unit,
    onClickSignUpScreenButton : () -> Unit,
    onClickPasswordResetButton: (String) -> Unit,
) {
    val (emailText, onEmailTextChanged) = rememberSaveable { mutableStateOf("") }
    val (passwordText, onPasswordTextChanged) = rememberSaveable { mutableStateOf("") }

    SignInScreen(
        paddingValues = paddingValues,
        emailText = emailText,
        passwordText = passwordText,
        onEmailTextChanged = onEmailTextChanged,
        onPasswordTextChanged = onPasswordTextChanged,
        onClickSignInButton = onClickSignInButton,
        onClickSignUpScreenButton = onClickSignUpScreenButton,
        onClickPasswordResetButton = onClickPasswordResetButton,
    )
}

@Composable
fun SignInScreen(
    paddingValues: PaddingValues,
    emailText: String,
    passwordText: String,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onClickSignInButton : (String, String) -> Unit,
    onClickSignUpScreenButton : () -> Unit,
    onClickPasswordResetButton: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (passwordVisible, onChangedPasswordVisible) = remember { mutableStateOf(false) }
    val passwordVisibleImage = if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff

    val emailBorderColor = getEmailBoarderColor(emailText)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
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
        )

        Button(
            onClick = { onClickSignInButton(emailText,passwordText)},
            colors = ButtonDefaults.buttonColors(containerColor = MainGreen),
            modifier = itemModifier,
        ) {
            Text(
                text = stringResource(R.string.login),
                color = Color.White,
            )
        }

        Row(
            modifier = itemModifier,
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                stringResource(R.string.reset_password),
                modifier = modifier.clickable { onClickPasswordResetButton(if (isValidEmail(emailText)) emailText else "") }
            )
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
            Text(
                stringResource(R.string.sign_up),
                modifier = modifier.clickable { onClickSignUpScreenButton() }
            )
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    val (emailText, onEmailTextChanged) = rememberSaveable { mutableStateOf("") }
    val (passwordText, onPasswordTextChanged) = rememberSaveable { mutableStateOf("") }

    SignInScreen(
        paddingValues = PaddingValues(10.dp),
        emailText = emailText,
        passwordText = passwordText,
        onEmailTextChanged = onEmailTextChanged,
        onPasswordTextChanged = onPasswordTextChanged,
        onClickSignInButton = {email, password -> },
        onClickSignUpScreenButton = {},
        onClickPasswordResetButton = {},
    )
}
