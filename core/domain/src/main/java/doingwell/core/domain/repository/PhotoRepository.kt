package doingwell.core.domain.repository

import com.hegunhee.model.photo.AlbumSummary
import com.hegunhee.model.photo.AlbumWithPhotos

interface PhotoRepository {

    suspend fun getAlbumSummaries(): Result<List<AlbumSummary>>

    suspend fun getAlbumAndPhotos(albumName: String): Result<AlbumWithPhotos>

}
