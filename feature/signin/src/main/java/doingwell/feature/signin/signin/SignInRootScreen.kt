package doingwell.feature.signin.signin

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.common.api.ApiException
import com.hegunhee.youredoingwell.ui.theme.MainGreen
import com.hegunhee.youredoingwell.ui.theme.Typography
import doingwell.core.ui.text.TitleText
import doingwell.feature.signin.R
import doingwell.feature.signin.getEmailBoarderColor
import doingwell.feature.signin.isValidEmail

@Composable
fun SignInRootScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onClickSignInButton: (String, String) -> Unit,
    onClickSignUpScreenButton : () -> Unit,
    onClickPasswordResetButton: (String) -> Unit,
    onClickGoogleSignIn : (String) -> Unit,
) {
    val (emailText, onEmailTextChanged) = rememberSaveable { mutableStateOf("") }
    val (passwordText, onPasswordTextChanged) = rememberSaveable { mutableStateOf("") }

    val googleAuthLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            if(result.resultCode == Activity.RESULT_OK) {
                val credentials = viewModel.signInClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                googleIdToken?.let(onClickGoogleSignIn)
            }
        } catch (e: ApiException) {
            e.printStackTrace()
            throw e
        }
    }

    val onClickGoogleAuth = {
        viewModel.signInClient.signOut()
        viewModel.signInClient.beginSignIn(viewModel.signInRequest)
            .addOnSuccessListener { result ->
                val intentSenderRequest = IntentSenderRequest
                    .Builder(result.pendingIntent.intentSender)
                    .build()
                googleAuthLauncher.launch(intentSenderRequest)
            }
        Unit
    }

    SignInScreen(
        paddingValues = paddingValues,
        emailText = emailText,
        passwordText = passwordText,
        onEmailTextChanged = onEmailTextChanged,
        onPasswordTextChanged = onPasswordTextChanged,
        onClickSignInButton = onClickSignInButton,
        onClickSignUpScreenButton = onClickSignUpScreenButton,
        onClickPasswordResetButton = onClickPasswordResetButton,
        onClickGoogleAuthButton = onClickGoogleAuth,
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
    onClickGoogleAuthButton: () -> Unit,
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

        Row(
            modifier = itemModifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Gray))
            Text(
                text = stringResource(R.string.another_login),
                modifier = modifier
                    .padding(horizontal = 10.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                fontSize = 13.sp,
                maxLines = 1,
            )
            Spacer(modifier = modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Gray))
        }

        Spacer(modifier = modifier.padding(top = 20.dp))

        Row {
            IconButton({ onClickGoogleAuthButton() }) {
                Icon(
                    painter = painterResource(R.drawable.google_auth),
                    contentDescription = stringResource(
                        R.string.google_auth_content_description
                    ),
                    tint = Color.Unspecified
                )
            }
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
        onClickGoogleAuthButton = {},
    )
}
