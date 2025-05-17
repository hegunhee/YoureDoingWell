package doingwell.feature.addphoto

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hegunhee.model.photo.AlbumSummary
import doingwell.core.ui.component.photo.SelectablePhoto
import doingwell.core.ui.model.SelectablePhoto

@Composable
fun AddPhotoRootScreen(
    viewModel: AddPhotoViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    maxPhotoCount: Int,
    currentPhotoCount: Int,
    onClickBackStack: () -> Unit,
    onClickAddPhotos: (ArrayList<Uri>) -> Unit,
) {
    LaunchedEffect(maxPhotoCount, currentPhotoCount) {
        viewModel.initMaxSelectableCount(maxPhotoCount, currentPhotoCount)
    }

    val albumWithPhotoState = viewModel.albumWithPhotoState.collectAsStateWithLifecycle().value
    val albumSummaries = viewModel.albumSummaries.collectAsStateWithLifecycle().value
    val selectedPhotos: List<SelectablePhoto> =
        viewModel.selectedPhotos.collectAsStateWithLifecycle().value

    AddPhotoScreen(
        paddingValues = paddingValues,
        albumSummaries = albumSummaries,
        albumWithPhotoState = albumWithPhotoState,
        selectedPhotos = selectedPhotos,
        onClickPhoto = viewModel::selectPhoto,
        onClickBackStack = onClickBackStack,
        onClickAddPhotos = onClickAddPhotos,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddPhotoScreen(
    paddingValues: PaddingValues,
    albumSummaries: List<AlbumSummary>,
    albumWithPhotoState: AlbumWithPhotoState,
    selectedPhotos: List<SelectablePhoto>,
    onClickPhoto: (SelectablePhoto) -> Unit,
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
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            when (albumWithPhotoState) {
                AlbumWithPhotoState.Loading -> {
                }

                is AlbumWithPhotoState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = paddingValues
                    ) {
                        items(albumWithPhotoState.albumWithPhotos.photos) { selectablePhoto ->
                            SelectablePhoto(
                                selectablePhoto,
                                onClickPhoto = onClickPhoto,
                            )
                        }
                    }
                }

                is AlbumWithPhotoState.Error -> {
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddPhotoScreenPreview() {
    AddPhotoScreen(
        paddingValues = PaddingValues(),
        albumSummaries = listOf(),
        albumWithPhotoState = AlbumWithPhotoState.Loading,
        selectedPhotos = listOf(),
        onClickPhoto = {},
        onClickBackStack = {},
        onClickAddPhotos = {},
    )
}
