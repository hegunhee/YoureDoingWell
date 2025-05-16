package doingwell.core.domain.usecase.photo

import com.hegunhee.model.photo.AlbumSummary
import doingwell.core.domain.repository.PhotoRepository
import javax.inject.Inject

class GetAlbumSummariesUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {

    suspend operator fun invoke(): Result<List<AlbumSummary>> {
        return photoRepository.getAlbumSummaries()
    }

}
