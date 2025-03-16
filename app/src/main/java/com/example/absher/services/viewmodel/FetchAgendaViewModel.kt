package com.example.absher.services.viewmodel// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.services.data.models.MeetingAgendaResponse
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import kotlinx.coroutines.launch

class FetchAgendaViewModel(
    private val getMeetingsUseCase: GetMeetingsUseCase
) : ViewModel() {
    private val _fetchMeetingState = MutableLiveData<FetchMeetingAgendaState>()
    val fetchMeetingState: LiveData<FetchMeetingAgendaState> = _fetchMeetingState


    fun fetchMeetingAgendas(meetingID: Int) {


        _fetchMeetingState.value = FetchMeetingAgendaStateLoading()

        viewModelScope.launch {
            try {
                val response: MeetingAgendaResponse? =
                    getMeetingsUseCase.fetchMeetingAgendas(meetingId = meetingID)




                _fetchMeetingState.postValue(
                    FetchMeetingAgendaStateSuccess(response)
                )
            } catch (e: Exception) {
                _fetchMeetingState.postValue(
                    FetchMeetingAgendaStateError(e.message ?: "Unknown error")
                )

            }
        }


    }
}

// Define states
sealed class FetchMeetingAgendaState
class FetchMeetingAgendaStateInit : FetchMeetingAgendaState()
class FetchMeetingAgendaStateLoading : FetchMeetingAgendaState()
data class FetchMeetingAgendaStateSuccess(val meetingAgenda: MeetingAgendaResponse?) :
    FetchMeetingAgendaState()

data class FetchMeetingAgendaStateError(val error: String) : FetchMeetingAgendaState()