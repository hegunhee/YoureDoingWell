package doingwell.feature.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hegunhee.model.user.UserData
import com.hegunhee.youredoingwell.ui.theme.Typography
import doingwell.core.ui.text.TitleText

@Composable
fun MainScreenRoot(
    viewModel : MainViewModel = hiltViewModel(),
    userData: UserData?,
    popSignInScreen: () -> Unit,
    popDailyScreen: () -> Unit,
) {
    MainScreen(
        userData = userData,
    )
    LaunchedEffect(userData) {
        if(userData == null) {
            popSignInScreen()
        } else {
            popDailyScreen()
        }
    }
}

@Composable
fun MainScreen(
    userData : UserData?,
    modifier : Modifier = Modifier,
) {
    Column {
        TitleText(
            modifier = modifier.padding(start = 40.dp, top = 100.dp),
            textStyle = Typography.titleLarge
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        userData = null,
    )
}
