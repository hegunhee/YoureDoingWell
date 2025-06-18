package doingwell.feature.addphoto

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.net.toUri
import com.hegunhee.model.photo.AlbumSummary
import doingwell.core.ui.model.SelectablePhoto
import doingwell.feature.addphoto.model.AlbumWithPhotosUiModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddPhotoScreenTest {

    @get:Rule
    val sut = createAndroidComposeRule<ComponentActivity>()

    private lateinit var context: Context

    @Before
    fun initContext() {
        context = sut.activity.baseContext
    }

    @Test
    fun givenEmptyState_whenScreening_shownNotClickableFinishButton() {
        // given
        sut.setContent {
            CreateAddPhotoScreen(
                albumWithPhotoState = AlbumWithPhotoState.Success(
                    AlbumWithPhotosUiModel("", 0, listOf())
                )
            )
        }

        // when & shown
        sut
            .onNodeWithText(context.getString(R.string.finish))
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }

    @Test
    fun givenAlbums_whenClickAlbumName_shownAlbumList() {
        // given
        sut.setContent {
            var selectedAlbum by remember { mutableStateOf(AlbumSummary("Pictures", 0)) }
            val albumSummaries by remember { mutableStateOf(getAlbums()) }

            val onClickAlbumSummary: (AlbumSummary) -> Unit = { albumSummary ->
                selectedAlbum = albumSummary
            }

            CreateAddPhotoScreen(
                selectedAlbum = selectedAlbum,
                albumSummaries = albumSummaries,
                onClickAlbumSummary = onClickAlbumSummary
            )
        }

        // when
        sut
            .onNodeWithText("Pictures")
            .performClick()

        sut
            .onNodeWithText("Camera", substring = true)
            .assertIsDisplayed()
            .performClick()

        sut
            .onNodeWithText("Camera", substring = true)
            .assertIsDisplayed()

        // then
        sut
            .onNodeWithText("Pictures")
            .assertIsNotDisplayed()
    }

    @Test
    fun givenClickedPhotos_whenClickPhoto_shownHideButton() {
        // given
        sut.setContent {
            var photoCount by remember { mutableStateOf(1) }
            val photos = getPhotos().mapIndexed { index, selectablePhoto ->
                if(index == 0) {
                    selectablePhoto.copy(selectCount = 1)
                } else {
                    selectablePhoto
                }
            }

            var albumWithPhotoState by remember {
                mutableStateOf(
                    AlbumWithPhotoState.Success(
                        albumWithPhotos = AlbumWithPhotosUiModel(
                            "Pictures",
                            photos.size,
                            photos
                        )
                    )
                )
            }

            var selectedPhoto by remember { mutableStateOf(listOf(photos[0])) }

            val onClickPhoto: (SelectablePhoto) -> Unit = { photo ->
                val newPhotos = albumWithPhotoState.albumWithPhotos.photos.map {
                    if(it.photo == photo.photo) {
                        if(it.selectCount == null) {
                            selectedPhoto = selectedPhoto + photo.copy(selectCount = photoCount)
                            it.copy(selectCount = photoCount)
                        } else {
                            selectedPhoto = selectedPhoto.filter { it.photo != photo.photo }
                            it.copy(selectCount = null)
                        }
                    } else {
                        it
                    }
                }
                if (photo.selectCount == null) {
                    albumWithPhotoState =
                        albumWithPhotoState.copy(albumWithPhotoState.albumWithPhotos.copy(photos = newPhotos))
                }
            }
            CreateAddPhotoScreen(
                albumWithPhotoState = albumWithPhotoState,
                selectedPhotos = selectedPhoto,
                onClickPhoto = onClickPhoto
            )
        }

        sut
            .onNodeWithText("1")
            .assertIsDisplayed()

        sut
            .onNodeWithText(context.getString(R.string.finish))
            .assertHasClickAction()

        // when
        sut
            .onNodeWithText("1")
            .performClick()

        // shown
        sut
            .onNodeWithText("1")
            .isNotDisplayed()

        sut
            .onNodeWithText(context.getString(R.string.finish))
            .assertHasNoClickAction()
    }

    @Composable
    private fun CreateAddPhotoScreen(
        selectedAlbum: AlbumSummary = AlbumSummary("", 0),
        albumSummaries: List<AlbumSummary> = listOf(),
        albumWithPhotoState: AlbumWithPhotoState = AlbumWithPhotoState.Loading,
        selectedPhotos: List<SelectablePhoto> = listOf(),
        onClickAlbumSummary: (AlbumSummary) -> Unit = {},
        onClickPhoto: (SelectablePhoto) -> Unit = {},
        onClickBackStack: () -> Unit = {},
        onClickAddPhotos: (ArrayList<Uri>) -> Unit = {},
    ) {
        AddPhotoScreen(
            paddingValues = PaddingValues(),
            selectedAlbum = selectedAlbum,
            albumSummaries = albumSummaries,
            albumWithPhotoState = albumWithPhotoState,
            selectedPhotos = selectedPhotos,
            onClickAlbumSummary = onClickAlbumSummary,
            onClickPhoto = onClickPhoto,
            onClickBackStack = onClickBackStack,
            onClickAddPhotos = onClickAddPhotos,
        )
    }

    private fun getPhotos(): List<SelectablePhoto> {
        return listOf(
            "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d",
            "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e",
            "https://images.unsplash.com/photo-1494790108377-be9c29b29330",
            "https://images.unsplash.com/photo-1541696432-82c6da8ce7bf",
            "https://images.unsplash.com/photo-1522075469751-3a6694fb2f61",
        ).map { SelectablePhoto(it.toUri()) }
    }

    private fun getAlbums(): List<AlbumSummary> {
        return listOf(
            AlbumSummary("Pictures", 0),
            AlbumSummary("Camera", 0)
        )
    }
}
