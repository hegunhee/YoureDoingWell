package doingwell.core.data.datasource.local

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import doingwell.core.data.datasource.local.model.AlbumSummaryResponse
import doingwell.core.data.datasource.local.model.AlbumWithPhotosResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultPhotoLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) : PhotoLocalDataSource {

    override suspend fun getAlbumSummaryResponses(): List<AlbumSummaryResponse> = withContext(Dispatchers.IO){
        val projections = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        val sortOrder = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} ASC"

        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projections,
            null,
            null,
            sortOrder
        )

        val albumPhotoCountMap = mutableMapOf<String, Int>()

        cursor?.use {
            val albumNameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            
            while (it.moveToNext()) {
                val albumName = it.getString(albumNameColumn)
                albumPhotoCountMap[albumName] = (albumPhotoCountMap[albumName] ?: 0) + 1
            }
        }

        return@withContext albumPhotoCountMap.map { (albumName, count) ->
            AlbumSummaryResponse(
                albumName = albumName,
                photoCount = count
            )
        }
    }

    override suspend fun getAlbumWithPhotosResponse(albumName: String): AlbumWithPhotosResponse = withContext(Dispatchers.IO) {
        val projections = arrayOf(
            MediaStore.Images.Media._ID,
        )

        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(albumName)

        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projections,
            selection,
            selectionArgs,
            null,
        )

        val result = mutableListOf<Uri>()

        cursor?.use {
            val idColum = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while(it.moveToNext()) {
                val id = it.getLong(idColum)
                val contentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                result.add(contentUri)
            }
        }

        return@withContext AlbumWithPhotosResponse(
            albumName = albumName,
            size = result.size,
            photos = result.toList(),
        )
    }
}
