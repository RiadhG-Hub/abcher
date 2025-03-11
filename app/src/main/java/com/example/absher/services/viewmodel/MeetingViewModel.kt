package com.example.absher.services.viewmodel// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


// Define states
sealed class FetchMeetingState
class FetchMeetingStateInit : FetchMeetingState()
class FetchMeetingStateLoading : FetchMeetingState()
data class FetchMeetingStateSuccess(val meetings: List<Meeting>?) : FetchMeetingState()
data class FetchMeetingStateError(val error: String) : FetchMeetingState()

@HiltViewModel
class MeetingViewModel @Inject constructor(
    // Inject your repository or use case here
    // private val meetingRepository: MeetingRepository
) : ViewModel() {

    private val _fetchMeetingState = MutableLiveData<FetchMeetingState>(FetchMeetingStateInit())
    val fetchMeetingState: LiveData<FetchMeetingState> = _fetchMeetingState

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun fetchMeetings() {
        viewModelScope.launch {
            _fetchMeetingState.value = FetchMeetingStateLoading()
            try {
                // Replace with actual data fetching logic
                // val meetings = meetingRepository.getMeetings()
                val meetings = listOf<Meeting>() // Placeholder
                _fetchMeetingState.value = FetchMeetingStateSuccess(meetings)
            } catch (e: Exception) {
                _fetchMeetingState.value = FetchMeetingStateError(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }
}