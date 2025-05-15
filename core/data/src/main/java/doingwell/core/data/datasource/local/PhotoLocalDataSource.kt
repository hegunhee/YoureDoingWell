package doingwell.core.data.datasource.local

import doingwell.core.data.datasource.local.model.AlbumSummaryResponse
import doingwell.core.data.datasource.local.model.AlbumWithPhotosResponse

interface PhotoLocalDataSource {

    suspend fun getAlbumSummaryResponses(): List<AlbumSummaryResponse>

    suspend fun getAlbumWithPhotosResponse(albumName: String): AlbumWithPhotosResponse

}
