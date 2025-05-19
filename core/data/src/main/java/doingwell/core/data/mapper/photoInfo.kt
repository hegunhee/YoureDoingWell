package doingwell.core.data.mapper

import com.hegunhee.model.photo.PhotoInfo
import doingwell.core.data.datasource.remote.model.photo.PhotoInfoResponse

fun List<PhotoInfoResponse>.toModel() : List<PhotoInfo> {
    return this.map { it.toModel() }
}

fun PhotoInfoResponse.toModel() : PhotoInfo {
    return PhotoInfo(
        url = url,
        path = path,
    )
}

fun List<PhotoInfo>.toEntity() : List<PhotoInfoResponse> {
    return this.map { it.toEntity() }
}

fun PhotoInfo.toEntity() : PhotoInfoResponse {
    return PhotoInfoResponse(
        url = url,
        path = path,
    )
}
