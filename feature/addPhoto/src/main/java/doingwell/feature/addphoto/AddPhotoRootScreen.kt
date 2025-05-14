package doingwell.feature.addphoto

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddPhotoRootScreen(
    paddingValues: PaddingValues,
    maxPhotoCount: Int,
    currentPhotoCount: Int,
    onClickBackStack: () -> Unit,
    onClickAddPhotos: (ArrayList<Uri>) -> Unit,
) {
    AddPhotoScreen(
        paddingValues = paddingValues,
        maxPhotoCount = maxPhotoCount,
        currentPhotoCount = currentPhotoCount,
        onClickBackStack = onClickBackStack,
        onClickAddPhotos = onClickAddPhotos,
    )
}

@Composable
internal fun AddPhotoScreen(
    paddingValues: PaddingValues,
    maxPhotoCount: Int,
    currentPhotoCount: Int,
    onClickBackStack: () -> Unit,
    onClickAddPhotos: (ArrayList<Uri>) -> Unit,
    modifier: Modifier = Modifier,
) {

}

@Preview
@Composable
private fun AddPhotoScreenPreview() {
    AddPhotoScreen(
        paddingValues = PaddingValues(),
        maxPhotoCount = 10,
        currentPhotoCount = 0,
        onClickBackStack = {},
        onClickAddPhotos = {},
    )
}
