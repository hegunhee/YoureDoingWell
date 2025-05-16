package doingwell.core.data.mapper

import com.hegunhee.model.photo.AlbumSummary
import com.hegunhee.model.photo.AlbumWithPhotos
import doingwell.core.data.datasource.local.model.AlbumSummaryResponse
import doingwell.core.data.datasource.local.model.AlbumWithPhotosResponse

fun List<AlbumSummaryResponse>.toModel() : List<AlbumSummary> {
    return this.map { it.toModel() }
}

internal fun AlbumSummaryResponse.toModel() : AlbumSummary {
    return AlbumSummary(
        this.albumName,
        this.photoCount,
    )
}

internal fun AlbumWithPhotosResponse.toModel() : AlbumWithPhotos {
    return AlbumWithPhotos(
        this.albumName,
        this.size,
        this.photos.map { it.toString() }
    )
}
