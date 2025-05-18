package doingwell.core.data.remote

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.storage
import doingwell.core.data.datasource.local.DefaultPhotoLocalDataSource
import doingwell.core.data.datasource.local.PhotoLocalDataSource
import doingwell.core.data.datasource.remote.DefaultPhotoStorageRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DefaultPhotoStorageRemoteDataSourceTest {

    private lateinit var sut : DefaultPhotoStorageRemoteDataSource

    private lateinit var context: Context
    private lateinit var contentResolver: ContentResolver
    private lateinit var photoDataSource: PhotoLocalDataSource

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )

    @Before
    fun initContext() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        contentResolver = context.contentResolver
        photoDataSource = DefaultPhotoLocalDataSource(context)

        if (FirebaseApp.getApps(context).isEmpty()) {
            val options = FirebaseOptions.Builder()
                .setApplicationId(FirebaseTestParams.APPLICATION_ID)
                .setApiKey(FirebaseTestParams.API_KEY)
                .setDatabaseUrl(FirebaseTestParams.STORAGE_URL)
                .build()
            FirebaseApp.initializeApp(context, options)
        }
        sut = DefaultPhotoStorageRemoteDataSource(
            Firebase.storage.getReferenceFromUrl(FirebaseTestParams.STORAGE_URL).child("TEST")
        )
    }

    @Test
    fun givenLocalPhotoUri_whenUploadPhoto_thenReturnsPhotoInfo() {
        runBlocking {
            // given
            val userId = "userId"
            val albumSummaries = photoDataSource.getAlbumSummaryResponses()
            if (albumSummaries.isEmpty()) {
                throw IllegalStateException("사진이 없습니다.")
            }
            val photo =
                photoDataSource.getAlbumWithPhotosResponse(albumSummaries[0].albumName).photos[0]

            // when
            val photoInfo = sut.uploadPhoto(photo, userId)

            // Then
            assertTrue(photoInfo.path.contains(userId))
            sut.deletePhoto(photoInfo.path)
        }
    }

    @Test
    fun givenPhotoPath_whenDeletePhoto_thenDeletePhoto() {
        runBlocking {
            // given
            val userId = "userId"
            val albumSummaries = photoDataSource.getAlbumSummaryResponses()
            if (albumSummaries.isEmpty()) {
                throw IllegalStateException("사진이 없습니다.")
            }
            val photo =
                photoDataSource.getAlbumWithPhotosResponse(albumSummaries[0].albumName).photos[0]
            val photoInfo = sut.uploadPhoto(photo, userId)

            // when
            sut.deletePhoto(photoInfo.path)

            // Then
            assertThrows(StorageException::class.java) {
                runBlocking {
                    sut.deletePhoto(photoInfo.path)
                }
            }
        }
    }
}
