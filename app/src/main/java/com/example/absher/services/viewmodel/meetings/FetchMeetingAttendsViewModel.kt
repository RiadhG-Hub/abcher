package com.example.absher.services.viewmodel.meetings// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.services.data.models.meetings.AttendeeResponse
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FetchMeetingAttendsViewModel @Inject constructor(
    private val getMeetingsUseCase: GetMeetingsUseCase
) : ViewModel() {
    private val _fetchMeetingState = MutableLiveData<FetchMeetingAttendState>()
    val fetchMeetingState: LiveData<FetchMeetingAttendState> = _fetchMeetingState


    fun fetchMeetingAttendees(meetingID: Int) {


        _fetchMeetingState.value = FetchMeetingAttendStateLoading()

        viewModelScope.launch {
            try {
                val response = getMeetingsUseCase.fetchMeetingAttendees(meetingId = meetingID)




                _fetchMeetingState.postValue(
                    FetchMeetingAttendStateSuccess(response)
                )
            } catch (e: Exception) {
                _fetchMeetingState.postValue(
                    FetchMeetingAttendStateError(e.message ?: "Unknown error")
                )

            }
        }


    }
}

// Define states
sealed class FetchMeetingAttendState
class FetchMeetingAttendStateInit : FetchMeetingAttendState()
class FetchMeetingAttendStateLoading : FetchMeetingAttendState()
data class FetchMeetingAttendStateSuccess(val meetingAttends: AttendeeResponse?) :
    FetchMeetingAttendState()

data class FetchMeetingAttendStateError(val error: String) : FetchMeetingAttendState()