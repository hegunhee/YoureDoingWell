package doingwell.feature.addRecord.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hegunhee.model.user.UserData
import doingwell.feature.addRecord.AddRecordRootScreen

const val ADD_RECORD_ROUTE = "ADD_RECORD"

fun NavGraphBuilder.addRecordNavGraph(
    paddingValues: PaddingValues,
    userData: UserData?,
    onClickSignOut : () -> Unit,
) {
    composable(ADD_RECORD_ROUTE) {
        AddRecordRootScreen(
            paddingValues = paddingValues,
            userData = userData,
        )
    }
}
