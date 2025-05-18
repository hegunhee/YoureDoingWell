package doingwell.core.data.repository

import androidx.core.net.toUri
import com.hegunhee.model.photo.PhotoInfo
import doingwell.core.data.datasource.remote.PhotoStorageRemoteDataSource
import doingwell.core.data.mapper.toModel
import doingwell.core.domain.repository.PhotoStorageRepository
import javax.inject.Inject

class DefaultPhotoStorageRepository @Inject constructor(
    private val photoStorageRemoteDataSource: PhotoStorageRemoteDataSource,
) : PhotoStorageRepository {
    override suspend fun uploadPhoto(uri: String, userId: String): Result<PhotoInfo> {
        return runCatching {
            photoStorageRemoteDataSource.uploadPhoto(uri.toUri(), userId).toModel()
        }
    }

    override suspend fun deletePhoto(path: String): Result<String> {
        return runCatching {
            photoStorageRemoteDataSource.deletePhoto(path)
        }
    }
}
