package doingwell.feature.main.screen

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.hegunhee.model.user.UserData
import com.hegunhee.youredoingwell.ui.theme.Typography
import doingwell.core.ui.text.TitleText

@Composable
fun MainScreenRoot(
    userData: UserData?,
    popSignInScreen: () -> Unit,
    popDailyScreen: () -> Unit,
) {
    val context = LocalContext.current
    val photoPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    }
    
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
    
    LaunchedEffect(Unit) {
        ActivityCompat.requestPermissions(
            context as android.app.Activity,
            photoPermission,
            1000
        )
    }
}

@Composable
internal fun MainScreen(
    userData: UserData?,
    modifier: Modifier = Modifier,
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
