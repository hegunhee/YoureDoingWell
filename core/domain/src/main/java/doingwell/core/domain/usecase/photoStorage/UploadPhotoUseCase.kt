package doingwell.core.domain.usecase.photoStorage

import com.hegunhee.model.photo.PhotoInfo
import doingwell.core.domain.repository.PhotoStorageRepository
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(
    private val photoStorageRepository: PhotoStorageRepository,
){

    suspend operator fun invoke(url: String, userId: String) : Result<PhotoInfo> {
        return photoStorageRepository.uploadPhoto(url, userId)
    }
}
