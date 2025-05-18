package doingwell.feature.addRecord.navigation

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hegunhee.model.user.UserData
import doingwell.feature.addRecord.AddRecordRootScreen

const val ADD_RECORD_ROUTE = "ADD_RECORD"

fun NavGraphBuilder.addRecordNavGraph(
    paddingValues: PaddingValues,
    userData: UserData?,
    onClickSignOut: () -> Unit,
    onClickAddPhoto: (maxPhotoCount: Int, currentPhotoCount: Int) -> Unit,
    getAddedPhoto: () -> List<Uri>?,
    onRemovePhotoSavedStateHandle: () -> Unit,
) {
    composable(ADD_RECORD_ROUTE) {
        AddRecordRootScreen(
            paddingValues = paddingValues,
            userData = userData,
            onClickAddPhoto = onClickAddPhoto,
            getAddedPhoto = getAddedPhoto,
            onPhotoRemoveSavedStateHandle = onRemovePhotoSavedStateHandle,
        )
    }
}
