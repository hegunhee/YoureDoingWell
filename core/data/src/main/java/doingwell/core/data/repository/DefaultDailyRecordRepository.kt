package doingwell.core.data.repository

import com.hegunhee.model.user.record.DailyRecord
import doingwell.core.data.datasource.remote.DailyRecordRemoteDataSource
import doingwell.core.data.datasource.remote.model.record.DailyRecordResponse
import doingwell.core.data.datasource.remote.model.record.toModel
import doingwell.core.domain.repository.DailyRecordRepository
import javax.inject.Inject

class DefaultDailyRecordRepository @Inject constructor(
    private val dailyRecordRemoteDataSource: DailyRecordRemoteDataSource,
): DailyRecordRepository{

    override suspend fun insertDailyRecord(dailyRecord: DailyRecord): Result<Int> {
        return runCatching {
            dailyRecordRemoteDataSource.insertDailyRecord(DailyRecordResponse.of(dailyRecord))
        }
    }

    override suspend fun findDailyRecords(
        userId: String,
        dateStamp: String
    ): Result<List<DailyRecord>> {
        return runCatching {
            dailyRecordRemoteDataSource.findDailyRecords(
                userId = userId,
                dateStamp = dateStamp,
            ).toModel()
        }
    }

    override suspend fun deleteDailyRecord(
        recordId: Int,
        userId: String,
        dateStamp: String
    ): Result<Int> {
        return runCatching {
            dailyRecordRemoteDataSource.deleteDailyRecord(
                recordId = recordId,
                userId = userId,
                dateStamp = dateStamp,
            )
        }
    }

    override suspend fun dailyRecordsSize(userId: String, dateStamp: String): Result<Int> {
        return runCatching {
            dailyRecordRemoteDataSource.dailyRecordsSize(
                userId = userId,
                dateStamp = dateStamp,
            )
        }
    }

    override suspend fun deleteDailyRecords(userId: String, dateStamp: String): Result<Int> {
        return runCatching {
            dailyRecordRemoteDataSource.deleteDailyRecords(
                userId = userId,
                dateStamp = dateStamp
            )
        }
    }

}
