package doingwell.feature.addRecord

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddRecordViewModel @Inject constructor() : ViewModel() {

    private val _photos: MutableStateFlow<List<String>> = MutableStateFlow(listOf())
    val photos: StateFlow<List<String>> = _photos.asStateFlow()

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
}