package doingwell.feature.addRecord

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hegunhee.model.user.UserData

@Composable
fun AddRecordRootScreen(
    paddingValues: PaddingValues,
    userData: UserData?
) {
    if(userData != null) {
        AddRecordScreen(
            paddingValues = paddingValues,
            userData = userData,
        )
    }
}

@Composable
internal fun AddRecordScreen(
    paddingValues: PaddingValues,
    userData: UserData,
) {
    Text(userData.toString())
}

@Preview
@Composable
private fun AddRecordScreenPreview() {
    AddRecordScreen(
        paddingValues = PaddingValues(),
        userData = UserData()
    )
}
