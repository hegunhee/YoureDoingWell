package doingwell.feature.addRecord.viewmodel

sealed interface AddRecordUiEvent {

    data object Save : AddRecordUiEvent

    data object PhotoError : AddRecordUiEvent
}
