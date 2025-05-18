package doingwell.core.data.datasource.remote

import android.net.Uri
import doingwell.core.data.datasource.remote.model.photo.PhotoInfo

interface PhotoStorageRemoteDataSource {
    suspend fun uploadPhoto(uri: Uri, userId: String): PhotoInfo
    suspend fun deletePhoto(path: String): String

}
