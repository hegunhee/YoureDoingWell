package com.hegunhee.model.user

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
}
