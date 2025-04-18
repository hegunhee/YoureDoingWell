package doingwell.feature.addRecord

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hegunhee.model.user.UserData
import doingwell.core.ui.text.item.SmallPhoto

@Composable
fun AddRecordRootScreen(
    paddingValues: PaddingValues,
    userData: UserData?
) {
    if (userData != null) {
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .padding(10.dp)
    ) {
        Text(
            stringResource(R.string.new_record),
            fontSize = 20.sp
        )
        Text(
            stringResource(R.string.photo),
            fontSize = 15.sp,
        )
        Row(
            modifier = modifier,
        ) {
            (0..3).forEach { _ ->
                SmallPhoto(
                    url = null,
                    onClickEmptyPhoto = {},
                    onClickDeletePhoto = {},
                )
                Spacer(modifier = modifier.padding(horizontal = 5.dp))
            }
        }
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(stringResource(R.string.enter_title)) },
            modifier = modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(stringResource(R.string.enter_description)) },
            modifier = modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = modifier.weight(1f))
        Button(
            {},
            modifier = modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_record))
        }
    }
}

@Preview
@Composable
private fun AddRecordScreenPreview() {
    AddRecordScreen(
        paddingValues = PaddingValues(),
        userData = UserData()
    )
}
