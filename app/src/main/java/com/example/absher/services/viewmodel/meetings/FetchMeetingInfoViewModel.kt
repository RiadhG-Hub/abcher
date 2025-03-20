package com.example.absher.services.viewmodel.meetings// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.services.data.models.meetings.MeetingInfoResponse
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchMeetingInfoViewModel @Inject constructor(
    private val getMeetingsUseCase: GetMeetingsUseCase
) : ViewModel() {
    private val _fetchMeetingState = MutableLiveData<FetchMeetingInfoState>()
    val fetchMeetingState: LiveData<FetchMeetingInfoState> = _fetchMeetingState


    fun fetchMeetingInfos(meetingID: Int) {


        _fetchMeetingState.value = FetchMeetingInfoStateLoading()

        viewModelScope.launch {
            try {
                val response: MeetingInfoResponse? =
                    getMeetingsUseCase.fetchMeetingInfo(meetingId = meetingID)




                _fetchMeetingState.postValue(
                    FetchMeetingInfoStateSuccess(response)
                )
            } catch (e: Exception) {
                _fetchMeetingState.postValue(
                    FetchMeetingInfoStateError(e.message ?: "Unknown error")
                )

            }
        }


    }
}

// Define states
sealed class FetchMeetingInfoState
class FetchMeetingInfoStateInit : FetchMeetingInfoState()
class FetchMeetingInfoStateLoading : FetchMeetingInfoState()
data class FetchMeetingInfoStateSuccess(val meetingAgenda: MeetingInfoResponse?) :
    FetchMeetingInfoState()

data class FetchMeetingInfoStateError(val error: String) : FetchMeetingInfoState()