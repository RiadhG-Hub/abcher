package com.example.absher.services.viewmodel// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.services.data.models.MeetingAgendaResponse
import com.example.absher.services.data.models.MeetingInfoResponse
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import kotlinx.coroutines.launch

class FetchMeetingInfoViewModel(
    private val getMeetingsUseCase: GetMeetingsUseCase
) : ViewModel() {
    private val _fetchMeetingState = MutableLiveData<FetchMeetingInfoState>()
    val fetchMeetingState: LiveData<FetchMeetingInfoState> = _fetchMeetingState


    fun FetchMeetingInfos(meetingID: Int) {


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