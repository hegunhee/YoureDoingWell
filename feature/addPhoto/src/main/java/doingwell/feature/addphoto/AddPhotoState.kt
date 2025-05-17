package doingwell.feature.addphoto

import doingwell.feature.addphoto.model.AlbumWithPhotosUiModel

sealed interface AddPhotoState {

    data object Loading: AddPhotoState

    data class Success(
        val albumWithPhotos: AlbumWithPhotosUiModel,
    ) : AddPhotoState

    data class Error(
        val exception: Throwable,
    ) : AddPhotoState

}
