package doingwell.feature.addphoto

import doingwell.feature.addphoto.model.AlbumWithPhotosUiModel

sealed interface AlbumWithPhotoState{

    data object Loading: AlbumWithPhotoState

    data class Success(
        val albumWithPhotos: AlbumWithPhotosUiModel,
    ) : AlbumWithPhotoState

    data class Error(
        val exception: Throwable,
    ) : AlbumWithPhotoState

}
