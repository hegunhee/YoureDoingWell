package com.hegunhee.daily

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hegunhee.model.user.UserData

@Composable
fun DailyRootScreen(
    paddingValues: PaddingValues,
    userData : UserData?,
    onClickSignOut: () -> Unit,
) {
    if(userData == null) {
        onClickSignOut()
        return
    }

    DailyScreen(
        paddingValues,
        userData = userData,
        onClickSignOut = onClickSignOut,
    )
}

@Composable
fun DailyScreen(
    paddingValues: PaddingValues,
    userData: UserData,
    onClickSignOut: () -> Unit,
) {
    Column {
        Text(userData.uid)
        Text(userData.email ?: "")
        Button({onClickSignOut()}) {
            Text("로그아웃")
        }
    }
}

@Preview
@Composable
fun DailyScreenPreview() {
    DailyScreen(
        paddingValues = PaddingValues(),
        userData = UserData("TEST_UID","TEST_EMAIL",null),
        onClickSignOut = {},
    )
}
