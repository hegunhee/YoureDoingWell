package doingwell.feature.signin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    paddingValues: PaddingValues
) {
    Text("로그인 화면 입니다.")
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        paddingValues = PaddingValues(10.dp)
    )
}