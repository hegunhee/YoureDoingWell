package doingwell.feature.addphoto.model

import androidx.core.net.toUri
import com.hegunhee.model.photo.AlbumWithPhotos
import doingwell.core.ui.model.SelectablePhoto

data class AlbumWithPhotosUiModel(
    val albumName: String,
    val size: Int,
    val photos: List<SelectablePhoto>,
) {

    companion object {
        fun of(
            albumWithPhotos: AlbumWithPhotos,
            selectedPhotos: List<SelectablePhoto> = listOf()
        ): AlbumWithPhotosUiModel {
            return AlbumWithPhotosUiModel(
                albumName = albumWithPhotos.albumName,
                size = albumWithPhotos.photos.size,
                photos = if (selectedPhotos.isEmpty()) {
                    albumWithPhotos.photos.map {
                        SelectablePhoto(
                            photo = it.toUri(),
                            selectCount = null,
                        )
                    }
                } else {
                    albumWithPhotos.photos.map { it ->
                        val photo = it.toUri()
                        SelectablePhoto(
                            photo = photo,
                            selectCount = selectedPhotos.find { it.photo == photo }?.selectCount
                        )
                    }
                }
            )
        }
    }

}
