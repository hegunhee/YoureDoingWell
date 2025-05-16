package doingwell.core.domain.usecase.photo

import com.hegunhee.model.photo.AlbumWithPhotos
import doingwell.core.domain.repository.PhotoRepository
import javax.inject.Inject

class GetAlbumWithPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
) {

    suspend operator fun invoke(albumName: String) : Result<AlbumWithPhotos> {
        return photoRepository.getAlbumWithPhotos(albumName)
    }

}
