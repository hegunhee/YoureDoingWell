package doingwell.core.data.mapper

import com.hegunhee.model.photo.PhotoInfo
import doingwell.core.data.datasource.remote.model.photo.PhotoInfoResponse

fun PhotoInfoResponse.toModel() : PhotoInfo {
    return PhotoInfo(
        url = url,
        path = path,
    )
}
