package doingwell.feature.addphoto

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddPhotoScreen(
    paddingValues: PaddingValues,
    maxPhotoCount: Int,
    currentPhotoCount: Int,
    onClickBackStack: () -> Unit,
    onClickAddPhotos: (ArrayList<Uri>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
          TopAppBar(
              navigationIcon = {
                  IconButton(
                      onClick = onClickBackStack
                  ) {
                      Icon(
                          Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                          contentDescription = stringResource(
                              R.string.back_button
                          )
                      )
                  }
              },
              title = { Text("전체 사진") },
          )
        },
    ) { innerPadding ->
    }
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
