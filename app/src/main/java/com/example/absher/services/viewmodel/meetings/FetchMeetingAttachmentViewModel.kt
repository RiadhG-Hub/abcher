package com.example.absher.services.viewmodel.meetings// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.services.data.models.meetings.MeetingAttachmentResponse
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import kotlinx.coroutines.launch

class FetchMeetingAttachmentViewModel(
    private val getMeetingsUseCase: GetMeetingsUseCase
) : ViewModel() {
    private val _fetchMeetingState = MutableLiveData<FetchMeetingAttachmentState>()
    val fetchMeetingAttachmentState: LiveData<FetchMeetingAttachmentState> = _fetchMeetingState


    fun fetchMeetingAttachments(meetingID: Int) {


        _fetchMeetingState.value = FetchMeetingAttachmentStateLoading()

        viewModelScope.launch {
            try {
                val response = getMeetingsUseCase.fetchMeetingAttachments(meetingId = meetingID)




                _fetchMeetingState.postValue(
                    FetchMeetingAttachmentStateSuccess(response)
                )
            } catch (e: Exception) {
                _fetchMeetingState.postValue(
                    FetchMeetingAttachmentStateError(e.message ?: "Unknown error")
                )

            }
        }


    }
}

// Define states
sealed class FetchMeetingAttachmentState
class FetchMeetingAttachmentStateInit : FetchMeetingAttachmentState()
class FetchMeetingAttachmentStateLoading : FetchMeetingAttachmentState()
data class FetchMeetingAttachmentStateSuccess(val meetingAttachment: MeetingAttachmentResponse?) :
    FetchMeetingAttachmentState()

data class FetchMeetingAttachmentStateError(val error: String) : FetchMeetingAttachmentState()