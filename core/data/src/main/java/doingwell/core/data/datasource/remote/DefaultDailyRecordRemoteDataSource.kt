package doingwell.core.data.datasource.remote

import com.google.firebase.database.DatabaseReference
import doingwell.core.data.datasource.remote.model.record.DailyRecordResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultDailyRecordRemoteDataSource @Inject constructor(
    private val database: DatabaseReference
) : DailyRecordRemoteDataSource {

    override suspend fun insertDailyRecord(dailyRecordResponse: DailyRecordResponse): String {
        database
            .child(
                getDailyPath(
                    dailyRecordResponse.userId,
                    dailyRecordResponse.startedAt.dateStamp,
                    dailyRecordResponse.key
                )
            )
            .setValue(dailyRecordResponse)
            .await()

        return dailyRecordResponse.key
    }

    override suspend fun findDailyRecords(userId: String): List<DailyRecordResponse> {
        val ref = database.child("record/daily/$userId").ref
        val snapshot = ref.get().await()

        val result = mutableListOf<DailyRecordResponse>()
        for (dateNode in snapshot.children) {
            for (recordNode in dateNode.children) {
                val record = recordNode.getValue(DailyRecordResponse::class.java)
                if (record != null) {
                    result.add(record)
                }
            }
        }

        return result
    }

    override suspend fun deleteDailyRecord(
        userId: String,
        title: String,
        dateStamp: String,
        timeStamp: String
    ): String {
        val key = "${title}_${dateStamp}_${timeStamp}"
        database.child(
            getDailyPath(
                userId,
                dateStamp,
                key
            )
        ).removeValue().await()
        return key
    }

    private fun getDailyPath(userId: String, dateStamp: String, key: String): String {
        return "record/daily/$userId/$dateStamp/$key"
    }

}
