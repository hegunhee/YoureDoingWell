package doingwell.core.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.hegunhee.youredoingwell.ui.theme.MainGreen
import com.hegunhee.youredoingwell.ui.theme.Typography

@Composable
fun TitleText(
    modifier : Modifier = Modifier,
    textStyle: TextStyle = Typography.titleLarge,
) {
    Text(
        titleString(),
        style = textStyle,
        modifier = modifier,
    )
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
private fun TitleTextPreview() {
    TitleText()
}
