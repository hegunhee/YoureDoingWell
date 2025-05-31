package doingwell.core.domain.usecase.record

import com.hegunhee.model.user.record.DailyRecord
import doingwell.core.domain.repository.DailyRecordRepository
import javax.inject.Inject

class GetDailyRecordsUseCase @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository,
) {

    suspend operator fun invoke(userId: String, dateStamp: String): Result<List<DailyRecord>> {
        return dailyRecordRepository.findDailyRecords(userId, dateStamp)
    }

}
