package doingwell.core.data.datasource.remote.model.record

import com.hegunhee.model.user.record.DailyRecord
import doingwell.core.data.datasource.remote.model.DateTimeResponse
import doingwell.core.data.datasource.remote.model.photo.PhotoInfoResponse
import doingwell.core.data.mapper.toEntity
import doingwell.core.data.mapper.toModel

data class DailyRecordResponse(
    val recordId: Int? = null,
    val userId: String = "",
    val title: String = "",
    val description: String? = null,
    val photos: List<PhotoInfoResponse>? = null,
    val startedAt: DateTimeResponse = DateTimeResponse(),
    val endedAt: DateTimeResponse = DateTimeResponse(),
) {

    fun toModel() : DailyRecord {
        return DailyRecord(
            recordId,
            userId,
            title,
            description,
            photos?.toModel(),
            startedAt.toModel(),
            endedAt.toModel(),
        )
    }

    companion object {
        fun of(
            dailyRecord: DailyRecord
        ): DailyRecordResponse = with(dailyRecord) {
            return DailyRecordResponse(
                recordId,
                userId,
                title,
                description,
                photos?.toEntity(),
                startedAt = DateTimeResponse.of(startedAt),
                endedAt = DateTimeResponse.of(endedAt),
            )
        }
    }
}

fun List<DailyRecordResponse>.toModel() : List<DailyRecord> {
    return this.map { it.toModel() }
}