package doingwell.core.domain.repository

import com.hegunhee.model.user.record.DailyRecord

interface DailyRecordRepository {

    suspend fun insertDailyRecord(dailyRecord: DailyRecord) : Result<Int>

    suspend fun findDailyRecords(userId: String, dateStamp: String) : Result<List<DailyRecord>>

    suspend fun deleteDailyRecord(recordId: Int, userId: String, dateStamp: String) : Result<Int>

    suspend fun dailyRecordsSize(userId: String, dateStamp: String) : Result<Int>

    suspend fun deleteDailyRecords(userId: String, dateStamp: String) : Result<Int>

}
