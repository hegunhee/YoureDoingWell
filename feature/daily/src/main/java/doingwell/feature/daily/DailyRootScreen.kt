package doingwell.feature.daily

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.model.user.UserData
import doingwell.feature.daily.viewModel.DailyViewModel

@Composable
internal fun DailyRootScreen(
    viewModel: DailyViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    userData: UserData?,
    onClickSignOut: () -> Unit,
) {
    if (userData == null) {
        onClickSignOut()
        return
    }

    LaunchedEffect(userData) {
        viewModel.fetchUserData(userData)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchDailyRecord()
    }

    val dailyRecord = viewModel.dailyRecords.collectAsStateWithLifecycle().value

    DailyScreen(
        paddingValues,
        userData = userData,
        onClickSignOut = onClickSignOut,
    )
}

@Composable
internal fun DailyScreen(
    paddingValues: PaddingValues,
    userData: UserData,
    onClickSignOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
    ) {
        Text(
            stringResource(R.string.daily_record),
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
private fun DailyScreenPreview() {
    DailyScreen(
        paddingValues = PaddingValues(),
        userData = UserData("TEST_UID", "TEST_EMAIL", null),
        onClickSignOut = {},
    )
}
