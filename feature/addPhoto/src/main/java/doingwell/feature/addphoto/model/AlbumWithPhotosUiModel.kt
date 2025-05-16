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
        fun of(albumWithPhotos: AlbumWithPhotos): AlbumWithPhotosUiModel {
            with(albumWithPhotos) {
                return AlbumWithPhotosUiModel(
                    albumName = albumName,
                    size = size,
                    photos = photos.map { SelectablePhoto(it.toUri(), null) }
                )
            }
        }
    }

}
