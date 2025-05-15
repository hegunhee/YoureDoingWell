package doingwell.core.data.datasource.local.model

import android.net.Uri

data class AlbumWithPhotosResponse(
    val albumName: String,
    val size: Int,
    val photos: List<Uri>
)
