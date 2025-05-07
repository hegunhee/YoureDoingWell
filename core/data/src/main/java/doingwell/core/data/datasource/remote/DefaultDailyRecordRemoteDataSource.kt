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

    override suspend fun insertDailyRecord(dailyRecordResponse: DailyRecordResponse): Int {
        val ref = database.child(
            getUserDailyPath(
                dailyRecordResponse.userId,
                dailyRecordResponse.startedAt.dateStamp
            )
        )

        val result = suspendCancellableCoroutine<Int> { continuation ->
            ref.runTransaction(object : Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val currentCount = currentData.child("count").getValue(Int::class.java) ?: 0
                    if (currentCount == 0) {
                        currentData.child("count").value = 0
                    }

                    val updatedRecord = dailyRecordResponse.copy(
                        recordId = currentCount
                    )

                    currentData.child("records").child(currentCount.toString()).setValue(updatedRecord)
                    currentData.child("count").setValue(currentCount + 1)

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
                        val recordId = currentData?.child("count")?.getValue(Int::class.java)
                        if(recordId == null) {
                            continuation.resumeWithException(IllegalStateException("기록이 정상적으로 저장되지 않았습니다."))
                        } else {
                            continuation.resume(recordId - 1)
                        }
                    }
                }
            })
        }

        return result
    }

    override suspend fun findDailyRecords(
        userId: String,
        dateStamp: String,
    ): List<DailyRecordResponse> {
        val ref = database.child(getUserDailyPath(userId, dateStamp)).child("records").ref

        val snapshot = ref.get().await()

        val result = mutableListOf<DailyRecordResponse>()
        for (recordNode in snapshot.children) {
            val record = recordNode.getValue(DailyRecordResponse::class.java)
            if (record != null) {
                result.add(record)
            }
        }

        return result
    }

    override suspend fun deleteDailyRecord(
        recordId: Int,
        userId: String,
        dateStamp: String,
    ): Int {
        database.child(getUserDailyPath(userId, dateStamp)).child("records")
            .child(recordId.toString()).removeValue().await()
        return recordId
    }

    override suspend fun dailyRecordsSize(userId: String, dateStamp: String): Int {
        val snapshot = database.child(getUserDailyPath(userId, dateStamp)).child("records").get().await()
        return snapshot.childrenCount.toInt()
    }

    override suspend fun deleteDailyRecords(userId: String, dateStamp: String): Int {
        val beforeCount = database.child(getUserDailyPath(userId, dateStamp)).child("records").get().await().childrenCount
        database.child(getUserDailyPath(userId, dateStamp)).child("records").removeValue().await()
        val afterCount = database.child(getUserDailyPath(userId, dateStamp)).child("records").get().await().childrenCount
        return (beforeCount - afterCount).toInt()
    }

    private fun getUserDailyPath(userId: String, dataStamp: String): String {
        return "record/daily/$userId/$dataStamp"
    }

}
