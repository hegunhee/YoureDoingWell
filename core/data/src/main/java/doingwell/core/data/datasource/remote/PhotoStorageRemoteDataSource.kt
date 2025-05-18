package doingwell.core.data.datasource.remote

import android.net.Uri
import doingwell.core.data.datasource.remote.model.photo.PhotoInfoResponse

interface PhotoStorageRemoteDataSource {
    suspend fun uploadPhoto(uri: Uri, userId: String): PhotoInfoResponse
    suspend fun deletePhoto(path: String): String

}
