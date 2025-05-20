package com.hegunhee.model.user

import java.time.LocalDateTime

data class DateTimeInfo(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val hour: Int,
    val minute: Int,
) {
    fun getDateText(): String {
        return "${year}. ${month}. $dayOfMonth"
    }

    fun getTimeText(): String {
        return "${hour}:${minute}"
    }

    companion object {
        fun nowToDateTimeInfo(now : LocalDateTime) : DateTimeInfo{
            return DateTimeInfo(
                now.year,
                now.monthValue,
                now.dayOfMonth,
                now.hour,
                now.minute
            )
        }
    }
}
