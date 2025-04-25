package com.hegunhee.model.user

data class TimeInfo(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val hour: Int,
    val minute: Int,
) {
    fun getDateText(): String {
        return "${2024}. ${month}. $dayOfMonth"
    }

    fun getTimeText(): String {
        return "${hour}:${minute}"
    }
}
