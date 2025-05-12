package doingwell.core.data.repository

import com.hegunhee.model.user.DateTimeInfo
import com.hegunhee.model.user.record.DailyRecord
import doingwell.core.data.datasource.remote.DefaultDailyRecordRemoteDataSource
import doingwell.core.data.datasource.remote.DefaultUserRemoteDataSource
import doingwell.core.data.datasource.remote.model.record.DailyRecordResponse
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class DailyRecordRepositoryTest {

    @InjectMocks
    private lateinit var sut: DefaultDailyRecordRepository

    @Mock
    private lateinit var dailyRecordDataSource: DefaultDailyRecordRemoteDataSource

    @Test
    fun givenDailyRecord_whenInsertDailyRecord_thenReturnsRecordId() {
        runBlocking {
            // Given
            val dailyRecord = createDailyRecord("userId")
            whenever(dailyRecordDataSource.insertDailyRecord(DailyRecordResponse.of(dailyRecord))).thenReturn(
                0
            )

            // When
            val recordId = sut.insertDailyRecord(dailyRecord).getOrThrow()

            // Then
            assertThat(recordId).isEqualTo(0)
            verify(dailyRecordDataSource).insertDailyRecord(DailyRecordResponse.of(dailyRecord))

        }
    }

    @Test
    fun givenUserIdAndDateStamp_whenFindDailyRecords_thenReturnsDailyRecords() {
        runBlocking {
            // Given
            val userId = "userId"
            val dateStamp = todayDateStamp()
            whenever(dailyRecordDataSource.findDailyRecords(userId, dateStamp)).thenReturn(listOf())

            // When
            val dailyRecords = sut.findDailyRecords(userId, dateStamp).getOrThrow()

            // Then
            assertThat(dailyRecords.isEmpty()).isEqualTo(true)
            verify(dailyRecordDataSource).findDailyRecords(userId, dateStamp)
        }
    }

    @Test
    fun givenRecordIdAndUserIdAndDateStamp_whenDeleteDailyRecords_thenReturnsRecordId() {
        runBlocking {
            // Given
            val recordId = 0
            val userId = "userId"
            val dateStamp = todayDateStamp()
            whenever(
                dailyRecordDataSource.deleteDailyRecord(
                    recordId,
                    userId,
                    dateStamp
                )
            ).thenReturn(recordId)

            // When
            val returnedRecordId = sut.deleteDailyRecord(recordId, userId, dateStamp).getOrThrow()

            // Then
            assertThat(returnedRecordId).isEqualTo(recordId)
            verify(dailyRecordDataSource).deleteDailyRecord(recordId, userId, dateStamp)
        }
    }

    @Test
    fun givenUserIdAndDateStamp_whenGetDailyRecordsSize_thenReturnSize() {
        runBlocking {
            // Given
            val userId = "userId"
            val dateStamp = todayDateStamp()
            whenever(dailyRecordDataSource.dailyRecordsSize(userId, dateStamp)).thenReturn(0)

            // When
            val dailyRecordsSize = sut.dailyRecordsSize(userId, dateStamp).getOrThrow()

            // Then
            assertThat(dailyRecordsSize).isEqualTo(0)
            verify(dailyRecordDataSource).dailyRecordsSize(userId, dateStamp)
        }
    }

    @Test
    fun givenUserIdAndDateStamp_whenDeleteDailyRecords_thenReturnsDeletedCount() {
        runBlocking {
            // Given
            val userId = "userId"
            val dateStamp = todayDateStamp()
            whenever(dailyRecordDataSource.deleteDailyRecords(userId, dateStamp)).thenReturn(0)

            // When
            val deletedCount = sut.deleteDailyRecords(userId, dateStamp).getOrThrow()

            // Then
            assertThat(deletedCount).isEqualTo(0)
            verify(dailyRecordDataSource).deleteDailyRecords(userId, dateStamp)
        }
    }

    private fun createDailyRecord(userId: String): DailyRecord {
        return DailyRecord(
            null,
            userId,
            "",
            "",
            listOf(),
            createDateTimeInfo(),
            createDateTimeInfo()
        )
    }

    private fun createDateTimeInfo(
        localDateTime: LocalDateTime = LocalDateTime.now()
    ): DateTimeInfo {
        return DateTimeInfo(
            localDateTime.year,
            localDateTime.monthValue,
            localDateTime.dayOfMonth,
            localDateTime.hour,
            localDateTime.minute
        )
    }

    private fun todayDateStamp(): String {
        val now = LocalDateTime.now()
        return listOf(
            now.year.toString().format(4),
            now.monthValue.toString().format(2),
            now.dayOfMonth.toString().format(2),
        ).joinToString("")
    }

}
