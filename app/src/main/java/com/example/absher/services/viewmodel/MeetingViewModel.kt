package com.example.absher.services.viewmodel// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// Define states
sealed class FetchMeetingState
class FetchMeetingStateInit : FetchMeetingState()
class FetchMeetingStateLoading : FetchMeetingState()
data class FetchMeetingStateSuccess(val meetings: List<Meeting>?) : FetchMeetingState()
data class FetchMeetingStateError(val error: String) : FetchMeetingState()





@HiltViewModel
class MeetingViewModel @Inject constructor(
    private val getMeetingsUseCase: GetMeetingsUseCase
) : ViewModel() {

    // StateFlow for dark mode (as in your original code)
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    // LiveData for meeting fetch state
    private val _fetchMeetingState = MutableLiveData<FetchMeetingState>(FetchMeetingStateInit())
    val fetchMeetingState: LiveData<FetchMeetingState> = _fetchMeetingState

    fun fetchMeetings() {
        viewModelScope.launch {
            _fetchMeetingState.value = FetchMeetingStateLoading()
            try {
                val meetings = getMeetingsUseCase.execute()
                _fetchMeetingState.value = FetchMeetingStateSuccess(meetings)
            } catch (e: Exception) {
                _fetchMeetingState.value = FetchMeetingStateError(e.message ?: "Unknown error")
            }
        }
    }

    // Optional: Toggle dark mode if needed
    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }
}