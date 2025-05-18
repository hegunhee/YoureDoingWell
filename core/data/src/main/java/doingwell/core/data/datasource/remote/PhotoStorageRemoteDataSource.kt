package doingwell.core.data.datasource.remote

import android.net.Uri

interface PhotoStorageRemoteDataSource {
    suspend fun uploadPhoto(uri: Uri, userId: String): String
    suspend fun deletePhoto(userId: String, photoUrl: String): String

}
