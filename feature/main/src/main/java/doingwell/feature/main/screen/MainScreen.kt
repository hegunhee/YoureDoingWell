package doingwell.feature.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hegunhee.youredoingwell.ui.theme.MainGreen
import com.hegunhee.youredoingwell.ui.theme.Typography

@Composable
fun MainScreenRoot() {
    MainScreen()
}

@Composable
fun MainScreen(
    modifier : Modifier = Modifier,
) {
    Column {
        Text(
            text = titleString(),
            modifier = modifier.padding(start = 40.dp, top = 100.dp),
            style = Typography.titleLarge
        )
    }
}

private fun titleString() : AnnotatedString {
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
fun MainScreenPreview() {
    MainScreen()
}