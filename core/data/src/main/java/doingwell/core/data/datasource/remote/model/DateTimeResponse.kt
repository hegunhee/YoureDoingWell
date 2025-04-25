package doingwell.core.data.datasource.remote.model

import com.hegunhee.model.user.DateTimeInfo

data class DateTimeResponse(
    val year: Int = 0,
    val month: Int = 0,
    val dayOfMonth: Int = 0,
    val dateStamp: String = "",
    val hour: Int = 0,
    val minute: Int = 0,
    val timeStamp: String = "",
) {

    fun toModel(): DateTimeInfo {
        return DateTimeInfo(
            year = year,
            month = month,
            dayOfMonth = dayOfMonth,
            hour = hour,
            minute = minute,
        )
    }

    companion object {
        fun of(dateTimeInfo: DateTimeInfo): DateTimeResponse = with(dateTimeInfo) {
            val dateStamp = listOf(
                year.format(4),
                month.format(2),
                dayOfMonth.format(2),
            ).joinToString("")

            val timeStamp = listOf(
                hour.format(2),
                minute.format(2)
            ).joinToString("")

            return DateTimeResponse(
                year = year,
                month = month,
                dayOfMonth = dayOfMonth,
                dateStamp = dateStamp,
                hour = hour,
                minute = minute,
                timeStamp = timeStamp
            )
        }

        private fun Int.format(digits: Int): String {
            return String.format("%0${digits}d", this)
        }
    }
}
