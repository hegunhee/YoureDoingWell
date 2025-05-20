package doingwell.feature.addRecord.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.model.user.DateTimeInfo
import com.hegunhee.model.user.record.DailyRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import doingwell.core.domain.usecase.photoStorage.UploadPhotoUseCase
import doingwell.core.domain.usecase.record.InsertDailyRecordUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddRecordViewModel @Inject constructor(
    private val uplocdPhotoUseCase: UploadPhotoUseCase,
    private val insertDailyRecordUseCase: InsertDailyRecordUseCase,
) : ViewModel() {

    private val _photos: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val photos: StateFlow<List<String>> = _photos.asStateFlow()

    private val now : LocalDateTime = LocalDateTime.now()

    private val _startedAt: MutableStateFlow<DateTimeInfo> = MutableStateFlow(DateTimeInfo.nowToDateTimeInfo(now))
    val startedAt: StateFlow<DateTimeInfo> = _startedAt.asStateFlow()

    private val _endedAt: MutableStateFlow<DateTimeInfo> = MutableStateFlow(DateTimeInfo.nowToDateTimeInfo(now))
    val endedAt : StateFlow<DateTimeInfo> = _endedAt.asStateFlow()

    private val _uiEvent: MutableSharedFlow<AddRecordUiEvent> = MutableSharedFlow()
    val uiEvent : SharedFlow<AddRecordUiEvent> = _uiEvent.asSharedFlow()

    fun addPhotos(photos: List<Uri>) {
        val photoStrings = photos.map { it.toString() }
        _photos.update { currentPhotos ->
            (currentPhotos + photoStrings).distinct()
        }
    }

    fun removePhoto(photo: String) {
        _photos.update { currentPhotos ->
            currentPhotos - photo
        }
    }

    fun addEndTime(hour: Int = 0, minute: Int) {
        val newHour = (hour + endedAt.value.hour) + ((endedAt.value.minute + minute) / 60)
        val newMinute = (endedAt.value.minute + minute) % 60
        if(newHour >= 23 && newMinute >= 59) {

            return
        }

        _endedAt.update { endTime ->
            endTime.copy(hour = newHour, minute = newMinute)
        }
    }

    fun returnEndedAt() {
        _endedAt.update {
            startedAt.value
        }
    }

    fun saveRecord(title: String, description: String, userId: String) {
        if (title.isEmpty()) return

        viewModelScope.launch {
            val uploadedPhotos = try {
                if (photos.value.isNotEmpty()) {
                    photos.value.map { photo ->
                        async { uplocdPhotoUseCase(photo, userId) }
                    }.awaitAll().map { it.getOrThrow() }
                } else null
            } catch (e : Exception) {
                _uiEvent.emit(AddRecordUiEvent.PhotoError)
                return@launch
            }

            insertDailyRecordUseCase(
                DailyRecord(
                    recordId = null,
                    userId = userId,
                    title = title,
                    description = description.ifBlank { null },
                    photos = uploadedPhotos,
                    startedAt = startedAt.value,
                    endedAt = endedAt.value
                )
            )

            _uiEvent.emit(AddRecordUiEvent.Save)
        }
    }
}
