package doingwell.feature.daily.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hegunhee.model.user.UserData
import com.hegunhee.model.user.record.DailyRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import doingwell.core.domain.usecase.record.GetDailyRecordsUseCase
import doingwell.feature.daily.util.DateUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val getDailyRecordsUseCase: GetDailyRecordsUseCase,
) : ViewModel() {

    private val _userData: MutableStateFlow<UserData?> = MutableStateFlow(null)
    val userData: StateFlow<UserData?> = _userData.asStateFlow()

    private val _timeStamp : MutableStateFlow<String> = MutableStateFlow(DateUtil.getTodayDateStamp())
    val timeStamp: StateFlow<String> = _timeStamp.asStateFlow()

    private val _dailyRecords: MutableStateFlow<List<DailyRecord>> = MutableStateFlow(listOf())
    val dailyRecords: StateFlow<List<DailyRecord>> = _dailyRecords.asStateFlow()

    fun fetchUserData(userData: UserData) {
        _userData.update {
            userData
        }
    }

    fun fetchDailyRecord() {
        viewModelScope.launch {
            val userId = userData.value?.uid ?: return@launch

            getDailyRecordsUseCase(userId, timeStamp.value).onSuccess { records ->
                _dailyRecords.update {
                    records
                }
            }.onFailure {

            }
        }
    }
}
