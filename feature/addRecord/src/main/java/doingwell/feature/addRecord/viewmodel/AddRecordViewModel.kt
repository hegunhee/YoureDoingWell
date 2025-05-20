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

    fun saveRecord(title: String, description: String, userId: String) {
        if (title.isEmpty()) return

        val localDateTime = LocalDateTime.now()
        val dateTimeInfo = createDateTimeInfo(localDateTime)



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
                    startedAt = dateTimeInfo,
                    endedAt = dateTimeInfo
                )
            )

            _uiEvent.emit(AddRecordUiEvent.Save)
        }
    }

    private fun createDateTimeInfo(localDateTime: LocalDateTime): DateTimeInfo {
        return DateTimeInfo(
            localDateTime.year,
            localDateTime.monthValue,
            localDateTime.dayOfMonth,
            localDateTime.hour,
            localDateTime.minute,
        )
    }
}
