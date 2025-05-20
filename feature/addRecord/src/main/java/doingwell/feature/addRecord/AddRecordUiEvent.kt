package doingwell.feature.addRecord

sealed interface AddRecordUiEvent {

    data object Save : AddRecordUiEvent
}
