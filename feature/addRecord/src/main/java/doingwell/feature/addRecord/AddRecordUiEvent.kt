package doingwell.feature.addRecord

sealed interface AddRecordUiEvent {

    data object Save : AddRecordUiEvent

    data object PhotoError : AddRecordUiEvent
}
