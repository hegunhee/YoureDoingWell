package doingwell.core.domain.repository

import com.hegunhee.model.photo.PhotoInfo

interface PhotoStorageRepository {
    suspend fun uploadPhoto(uri: String, userId: String): Result<PhotoInfo>
    suspend fun deletePhoto(path: String): Result<String>
}
