package doingwell.core.data.datasource.remote

import android.net.Uri
import com.google.firebase.storage.StorageReference
import doingwell.core.data.di.qualifier.PhotoStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPhotoStorageRemoteDataSource @Inject constructor(
    @PhotoStorage private val storage: StorageReference,
) : PhotoStorageRemoteDataSource {


    override suspend fun uploadPhoto(uri: Uri, userId: String): String {
        val storageRef = storage.child(userId)
        val imageRef = storageRef.child("${UUID.randomUUID()}.jpg")

        imageRef.putFile(uri).await()
        return imageRef.downloadUrl.await().toString()
    }

    override suspend fun deletePhoto(userId: String, photoUrl: String): String {
        val storageRef = storage.child(userId)
        val imageRef = storageRef.child(photoUrl)

        imageRef.delete().await()
        return photoUrl
    }
}
