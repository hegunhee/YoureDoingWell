package doingwell.feature.daily.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtil {
    private val now = LocalDate.now()
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun getTodayDateStamp() : String {
        return now.format(formatter)
    }
}
