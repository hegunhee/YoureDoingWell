package doingwell.core.data.repository

import doingwell.core.data.datasource.local.PhotoLocalDataSource
import doingwell.core.data.datasource.local.model.AlbumWithPhotosResponse
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PhotoRepositoryTest {

    @InjectMocks
    private lateinit var sut: DefaultPhotoRepository

    @Mock
    private lateinit var photoLocalDataSource: PhotoLocalDataSource

    @Test
    fun given_whenGetAlbumSummaries_thenReturnsAlbumSummaries() {
        runBlocking {
            // Given
            whenever(photoLocalDataSource.getAlbumSummaryResponses()).thenReturn(listOf())

            // When
            val albumSummaries = sut.getAlbumSummaries().getOrThrow()

            // Then
            assertThat(albumSummaries.isEmpty()).isEqualTo(true)
            verify(photoLocalDataSource).getAlbumSummaryResponses()
        }
    }

    @Test
    fun givenAlbumName_whenGetAlbumAndPhotos_thenReturnsAlbumAndPhotos() {
        runBlocking {
            // Given
            val albumName = "albumName"
            val albumWithPhotosResponse = createAlbumWithPhotosResponse(albumName)
            whenever(photoLocalDataSource.getAlbumWithPhotosResponse(albumName)).thenReturn(albumWithPhotosResponse)

            // When
            val albumWithPhotos = sut.getAlbumWithPhotos(albumName).getOrThrow()

            // Then
            assertThat(albumWithPhotos.albumName).isEqualTo(albumName)
            verify(photoLocalDataSource).getAlbumWithPhotosResponse(albumName)
        }
    }

    private fun createAlbumWithPhotosResponse(albumName: String): AlbumWithPhotosResponse {
        return AlbumWithPhotosResponse(
            albumName = albumName,
            size = 0,
            photos = listOf()
        )
    }

}
