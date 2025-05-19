package com.hegunhee.model.user.record

import com.hegunhee.model.photo.PhotoInfo
import com.hegunhee.model.user.DateTimeInfo

data class DailyRecord(
    val recordId: Int?,
    val userId: String,
    val title: String,
    val description: String?,
    val photos: List<PhotoInfo>?,
    val startedAt: DateTimeInfo,
    val endedAt: DateTimeInfo,
)
