package doingwell.core.data.datasource.remote.model.record

import com.hegunhee.model.user.record.DailyRecord
import doingwell.core.data.datasource.remote.model.DateTimeResponse

data class DailyRecordResponse(
    val userId: String = "",
    val title: String = "",
    val description: String? = null,
    val photos: List<String>? = null,
    val startedAt: DateTimeResponse = DateTimeResponse(),
    val endedAt: DateTimeResponse = DateTimeResponse(),
) {

    fun toModel() : DailyRecord {
        return DailyRecord(
            userId,
            title,
            description,
            photos,
            startedAt.toModel(),
            endedAt.toModel(),
        )
    }

    companion object {
        fun of(
            dailyRecord: DailyRecord
        ): DailyRecordResponse = with(dailyRecord) {
            return DailyRecordResponse(
                userId,
                title,
                description,
                photos,
                startedAt = DateTimeResponse.of(startedAt),
                endedAt = DateTimeResponse.of(endedAt),
            )
        }
    }
}
