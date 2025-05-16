package doingwell.core.data.repository

import com.hegunhee.model.photo.AlbumSummary
import com.hegunhee.model.photo.AlbumWithPhotos
import doingwell.core.data.datasource.local.PhotoLocalDataSource
import doingwell.core.data.mapper.toModel
import doingwell.core.domain.repository.PhotoRepository
import javax.inject.Inject

class DefaultPhotoRepository @Inject constructor(
    private val photoLocalDataSource: PhotoLocalDataSource,
) : PhotoRepository {

    override suspend fun getAlbumSummaries(): Result<List<AlbumSummary>> {
        return runCatching {
            photoLocalDataSource.getAlbumSummaryResponses().toModel()
        }
    }

    override suspend fun getAlbumWithPhotos(albumName: String): Result<AlbumWithPhotos> {
        return runCatching {
            photoLocalDataSource.getAlbumWithPhotosResponse(albumName).toModel()
        }
    }

}
