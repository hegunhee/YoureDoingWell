package doingwell.core.data.datasource.remote

import doingwell.core.data.datasource.remote.model.record.DailyRecordResponse

interface DailyRecordRemoteDataSource {

    suspend fun insertDailyRecord(dailyRecordResponse: DailyRecordResponse): Int

    suspend fun findDailyRecords(
        userId: String,
        dateStamp: String,
    ): List<DailyRecordResponse>

    suspend fun deleteDailyRecord(
        recordId: Int,
        userId: String,
        dateStamp: String,
    ): Int

    suspend fun dailyRecordsSize(
        userId: String,
        dateStamp: String,
    ): Int
}
