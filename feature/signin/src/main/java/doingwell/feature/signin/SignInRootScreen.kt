package doingwell.feature.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hegunhee.youredoingwell.ui.theme.MainGreen
import com.hegunhee.youredoingwell.ui.theme.Typography

@Composable
fun SignInRootScreen(
    paddingValues: PaddingValues,
) {
    SignInScreen(
        paddingValues = paddingValues,
    )
}

@Composable
fun SignInScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = modifier.padding(top = 100.dp))
        Text(
            titleString(),
            style = Typography.titleLarge,
        )

        Spacer(modifier = modifier.padding(top = 15.dp))
        val itemModifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .padding(top = 10.dp)

        OutlinedTextField(
            "",
            onValueChange = {},
            placeholder = { Text(stringResource(R.string.enter_email)) },
            modifier = itemModifier,
        )

        OutlinedTextField(
            "",
            onValueChange = {},
            placeholder = { Text(stringResource(R.string.enter_password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = itemModifier,
        )

        Button(
            onClick = {},
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
            Text(stringResource(R.string.find_password))
            Spacer(modifier = modifier.padding(horizontal = 10.dp))
            Text(stringResource(R.string.sign_up))
        }
    }
}

private fun titleString(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = MainGreen)) {
            append("당")
        }
        append("신은")
        append("\n")
        withStyle(style = SpanStyle(color = MainGreen)) {
            append("잘")
        }
        append("하고 있다")
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        paddingValues = PaddingValues(10.dp)
    )
}