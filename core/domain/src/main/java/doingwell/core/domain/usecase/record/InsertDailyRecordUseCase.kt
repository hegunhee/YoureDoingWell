package doingwell.core.domain.usecase.record

import com.hegunhee.model.user.record.DailyRecord
import doingwell.core.domain.repository.DailyRecordRepository
import javax.inject.Inject

class InsertDailyRecordUseCase @Inject constructor(
    private val repository: DailyRecordRepository,
) {

    suspend operator fun invoke(dailyRecord: DailyRecord) : Result<Int> {
        return repository.insertDailyRecord(dailyRecord)
    }
}