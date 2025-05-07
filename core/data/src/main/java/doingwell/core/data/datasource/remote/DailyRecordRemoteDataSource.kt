package doingwell.core.data.datasource.remote

import doingwell.core.data.datasource.remote.model.record.DailyRecordResponse

interface DailyRecordRemoteDataSource {

    suspend fun insertDailyRecord(dailyRecordResponse: DailyRecordResponse): String

    suspend fun findDailyRecords(
        userId: String,
        dateStamp: String,
    ): List<DailyRecordResponse>

    suspend fun deleteDailyRecord(
        userId: String,
        title: String,
        dateStamp: String,
        timeStamp: String
    ): String
}
