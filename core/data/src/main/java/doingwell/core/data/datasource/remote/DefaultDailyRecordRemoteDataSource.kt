package doingwell.core.data.datasource.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.Transaction.Handler
import doingwell.core.data.datasource.remote.model.record.DailyRecordResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DefaultDailyRecordRemoteDataSource @Inject constructor(
    private val database: DatabaseReference
) : DailyRecordRemoteDataSource {

    override suspend fun insertDailyRecord(dailyRecordResponse: DailyRecordResponse): String {
        val ref = database.child(
            getUserDailyPath(
                dailyRecordResponse.userId,
                dailyRecordResponse.startedAt.dateStamp
            )
        )

        suspendCancellableCoroutine { continuation ->
            ref.runTransaction(object : Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val currentCount = currentData.child("count").getValue(Int::class.java) ?: 0
                    if (currentCount == 0) {
                        currentData.child("count").value = 0
                    }

                    val newCount = currentCount + 1

                    val updatedRecord = dailyRecordResponse.copy(
                        recordId = newCount
                    )

                    currentData.child("records").child(newCount.toString()).setValue(updatedRecord)
                    currentData.child("count").setValue(newCount)

                    return Transaction.success(currentData)
                }

                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    if (error != null) {
                        continuation.resumeWithException(error.toException())
                    } else {
                        continuation.resume(Unit)
                    }
                }
            })
        }

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

    private fun getUserDailyPath(userId: String, dataStamp: String): String {
        return "record/daily/$userId/$dataStamp"
    }

    private fun getDailyPath(userId: String, dateStamp: String, key: String): String {
        return "record/daily/$userId/$dateStamp/$key"
    }

}
