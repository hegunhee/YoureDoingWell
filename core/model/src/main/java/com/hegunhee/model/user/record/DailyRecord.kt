package com.hegunhee.model.user.record

import com.hegunhee.model.user.DateTimeInfo

data class DailyRecord(
    val userId: String,
    val title: String,
    val description: String?,
    val photos: List<String>?,
    val startedAt: DateTimeInfo,
    val endedAt: DateTimeInfo,
)
