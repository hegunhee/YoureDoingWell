package doingwell.core.data.model

import com.hegunhee.model.user.DateTimeInfo
import doingwell.core.data.datasource.remote.model.DateTimeResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDateTime

class DateTimeResponseTest {

    private val now = LocalDateTime.now()

    @Test
    fun givenDateTimeInfo_whenToDateTimeResponse_thenShowTimeStamp() {
        // Given
        val dateTimeInfo = createDateTimeInfo()

        // When
        val dateTimeResponse = DateTimeResponse.of(dateTimeInfo)

        println(dateTimeResponse)

        // Then
        assertThat(dateTimeResponse.dateStamp).isEqualTo(
            listOf(
                dateTimeInfo.year.format(4),
                dateTimeInfo.month.format(2),
                dateTimeInfo.dayOfMonth.format(2)
            ).joinToString("")
        )

        assertThat(dateTimeResponse.timeStamp).isEqualTo(
            listOf(
                dateTimeInfo.hour.format(2),
                dateTimeInfo.minute.format(2)
            ).joinToString("")
        )
    }

    @Test
    fun givenDateTimeInfo_whenToDateTimeResponseToModel_thenSameDateTimeInfo() {
        // Given
        val dateTimeInfo = createDateTimeInfo()

        // When
        val afterDateTimeInfo = DateTimeResponse.of(dateTimeInfo).toModel()

        assertThat(afterDateTimeInfo).isEqualTo(dateTimeInfo)
    }

    private fun createDateTimeInfo(
        year: Int = now.year,
        month: Int = now.monthValue,
        dayOfMonth: Int = now.dayOfMonth,
        hour: Int = now.hour,
        minute: Int = now.minute,
    ): DateTimeInfo {
        return DateTimeInfo(
            year,
            month,
            dayOfMonth,
            hour,
            minute,
        )
    }

    private fun Int.format(digits: Int): String {
        return String.format("%0${digits}d", this)
    }

}
