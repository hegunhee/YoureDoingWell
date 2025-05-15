package doingwell.core.data.local

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import doingwell.core.data.datasource.local.DefaultPhotoLocalDataSource
import doingwell.core.data.datasource.local.PhotoLocalDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoLocalDataSourceTest {

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )

    private lateinit var context: Context
    private lateinit var contentResolver: ContentResolver
    private lateinit var sut: PhotoLocalDataSource

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        contentResolver = context.contentResolver
        sut = DefaultPhotoLocalDataSource(context)
    }

    @Test
    fun given_whenGetAlbumSummary_thenWorksFine() = runBlocking {
        // Given

        // When
        val photoSummaries = sut.getAlbumSummaryResponses()

        // Then
        println(photoSummaries)
    }

    @Test
    fun givenAlbumName_whenGetAlbumWithPhotos_thenWorksFine() = runBlocking {
        // Given
        val albumSummary = sut.getAlbumSummaryResponses().getOrNull(0) ?: throw IllegalStateException("사진이 존재하지 않습니다.")

        // When
        val albumWithPhotos = sut.getAlbumWithPhotosResponse(albumSummary.albumName)

        // Then
        assertEquals(albumWithPhotos.size,albumSummary.photoCount)
        assertEquals(albumWithPhotos.albumName, albumSummary.albumName)


    }

}
