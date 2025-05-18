package doingwell.core.data.datasource.remote

import android.net.Uri
import com.google.firebase.storage.StorageReference
import doingwell.core.data.datasource.remote.model.photo.PhotoInfo
import doingwell.core.data.di.qualifier.PhotoStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPhotoStorageRemoteDataSource @Inject constructor(
    @PhotoStorage private val storage: StorageReference,
) : PhotoStorageRemoteDataSource {


    override suspend fun uploadPhoto(uri: Uri, userId: String): PhotoInfo {
        val storagePath = "Photos/$userId/${UUID.randomUUID()}.jpg"
        val imageRef = storage.child(storagePath)

        imageRef.putFile(uri).await()
        return PhotoInfo(
            imageRef.downloadUrl.await().toString(),
            storagePath,
        )
    }

    override suspend fun deletePhoto(path: String): String {
        val imageRef = storage.child(path)
        imageRef.delete().await()

        return path
    }
}
