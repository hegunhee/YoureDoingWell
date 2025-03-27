package doingwell.feature.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hegunhee.youredoingwell.ui.theme.Typography
import doingwell.core.ui.text.TitleText

@Composable
fun MainScreenRoot(
    viewModel : MainViewModel = hiltViewModel(),
    popSignInScreen: () -> Unit,
) {
    val firebaseAuth = remember { Firebase.auth }
    MainScreen(
        firebaseAuth = firebaseAuth,
        popSignInScreen = popSignInScreen,
    )
}

@Composable
fun MainScreen(
    firebaseAuth: FirebaseAuth?,
    modifier : Modifier = Modifier,
    popSignInScreen : () -> Unit,
) {
    if(firebaseAuth?.currentUser == null) {
        popSignInScreen()
    }
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
        firebaseAuth = null,
        popSignInScreen = {},
    )
}
