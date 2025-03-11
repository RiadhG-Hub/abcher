package com.example.absher.ui.viewmodel// MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.model.Meeting
import com.example.absher.model.MeetingSearchRequest
import com.example.absher.model.network.RetrofitClient
import kotlinx.coroutines.launch

class MeetingViewModel : ViewModel() {
    private val _meetings = MutableLiveData<List<Meeting>>()
    val meetings: LiveData<List<Meeting>> = _meetings


    private val _meetingsState = MutableLiveData<FetchMeetingState>(FetchMeetingStateInit())
    val meetingsState: LiveData<FetchMeetingState> = _meetingsState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50IjoiYytldFZQN0xNaGI3K3creHYzWUJNSm82cHZRMWFnSkUzcnBuUmlUbzBycz0iLCJkZXBhcnRtZW50IjoiUnNua2F1bWlWa0QvOWE3WUl4NUFNMHBOU2wzRG0xWVNzdmtPVURmQzl6RT0iLCJuYW1lIjoicEJGcThValVKK2lRd2pVWnZoQmtnSStPWFlnRTlxT3J1QXBzZzFCRmNYaWdYNWtkbHJ4L1U0VGM2eDdhMmc4byIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMSIsImxhbmciOiJhciIsImV4cCI6MTc0MTY4ODU2MiwiaXNzIjoiSW50YWxpbyIsImF1ZCI6IkludGFsaW8ifQ.vVn23hvZZ7woupqjciK8d9s9Hm6PAD4hwbE7u_mpkRA"



    fun fetchMeetingsWithState() {
        _meetingsState.value = FetchMeetingStateLoading()
        viewModelScope.launch {
            try {
                val request = MeetingSearchRequest(
                    statusId = null,
                    committeeId = null,
                    toDate = null,
                    fromDate = null,
                    onlyMyMeetings = true,
                    title = null,
                    location = null
                )

                val response = RetrofitClient.meetingApiService.searchMeetings(
                    page = 1,
                    pageSize = 10,
                    authToken = authToken,
                    request = request
                )

                if (response.success) {
                    _meetingsState.value = FetchMeetingStateSuccess(response.data.data);
                } else {
                    _meetingsState.value = FetchMeetingStateError(response.message ?: "Unknown error occurred")

                }
            } catch (e: Exception) {
                _meetingsState.value = FetchMeetingStateError(e.message ?: "Network error occurred")

            }
        }
    }


    fun fetchMeetings() {
        viewModelScope.launch {
            try {
                val request = MeetingSearchRequest(
                    statusId = null,
                    committeeId = null,
                    toDate = null,
                    fromDate = null,
                    onlyMyMeetings = true,
                    title = null,
                    location = null
                )

                val response = RetrofitClient.meetingApiService.searchMeetings(
                    page = 1,
                    pageSize = 10,
                    authToken = authToken,
                    request = request
                )

                if (response.success) {
                    _meetings.value = response.data.data
                } else {
                    _error.value = response.message ?: "Unknown error occurred"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Network error occurred"
            }
        }
    }
}



  open class  FetchMeetingState {

}

class  FetchMeetingStateSuccess(val meetings: List<Meeting>) : FetchMeetingState()
class  FetchMeetingStateError(val error: String) : FetchMeetingState()
class  FetchMeetingStateLoading : FetchMeetingState()
class  FetchMeetingStateInit : FetchMeetingState()