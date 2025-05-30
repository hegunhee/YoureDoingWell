package doingwell.feature.addphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.model.photo.AlbumSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import doingwell.core.domain.usecase.photo.GetAlbumSummariesUseCase
import doingwell.core.domain.usecase.photo.GetAlbumWithPhotosUseCase
import doingwell.core.ui.model.SelectablePhoto
import doingwell.feature.addphoto.model.AlbumWithPhotosUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPhotoViewModel @Inject constructor(
    val getAlbumSummariesUseCase: GetAlbumSummariesUseCase,
    val getAlbumWithPhotosUseCase: GetAlbumWithPhotosUseCase,
) : ViewModel() {

    val albumSummaries: StateFlow<List<AlbumSummary>> = flow {
        emit(getAlbumSummariesUseCase().getOrThrow())
    }.catch { throwable ->

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = listOf()
    )

    private val _selectedAlbum: MutableStateFlow<AlbumSummary> =
        MutableStateFlow(AlbumSummary("", 0))
    val selectedAlbum: StateFlow<AlbumSummary> = _selectedAlbum.asStateFlow()

    private val _selectedPhotos: MutableStateFlow<List<SelectablePhoto>> =
        MutableStateFlow(listOf())
    val selectedPhotos: StateFlow<List<SelectablePhoto>> = _selectedPhotos.asStateFlow()

    val albumWithPhotoState: StateFlow<AlbumWithPhotoState> =
        selectedAlbum.combine(selectedPhotos) { selectedAlbum, selectedPhotos ->
            if (selectedAlbum.albumName.isEmpty()) {
                AlbumWithPhotoState.Loading
            } else {
                val albumWithPhotos =
                    getAlbumWithPhotosUseCase(selectedAlbum.albumName).getOrThrow()
                val albumWithPhotosUiModel =
                    AlbumWithPhotosUiModel.of(albumWithPhotos, selectedPhotos)
                AlbumWithPhotoState.Success(albumWithPhotosUiModel)
            }
        }.catch { error ->
            AlbumWithPhotoState.Error(error)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500L),
            initialValue = AlbumWithPhotoState.Loading
        )

    private val maxSelectableCount: MutableStateFlow<Int> = MutableStateFlow(0)

    private val currentSelectedCount: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            albumSummaries.collect { summaries ->
                if (summaries.isNotEmpty() && selectedAlbum.value.albumName.isBlank()) {
                    _selectedAlbum.update {
                        albumSummaries.value[0]
                    }
                }
            }
        }
    }

    fun initMaxSelectableCount(maxSelectCount: Int, currentSelectCount: Int) {
        maxSelectableCount.update {
            maxSelectCount - currentSelectCount
        }
    }

    fun selectPhoto(selectedPhoto: SelectablePhoto) {
        if (selectedPhoto.selectCount == null) {
            if (maxSelectableCount.value > currentSelectedCount.value) {
                _selectedPhotos.update { photos ->
                    photos + selectedPhoto.copy(selectCount = currentSelectedCount.value + 1)
                }
                currentSelectedCount.value += 1
            }
        } else {
            _selectedPhotos.update { photos ->
                val selectedCount = photos.first { it == selectedPhoto }.selectCount ?: throw IllegalStateException("Count가 올바르지 않은 상태")
                val filteredPhotos = photos.filter { it != selectedPhoto }
                
                filteredPhotos.map { photo ->
                    if ((photo.selectCount ?: 0) > selectedCount) {
                        photo.copy(selectCount = (photo.selectCount ?: 0) - 1)
                    } else {
                        photo
                    }
                }
            }
            currentSelectedCount.value -= 1
        }
    }

    fun selectAlbum(album: AlbumSummary) {
        _selectedAlbum.update {
            album
        }
    }

}
