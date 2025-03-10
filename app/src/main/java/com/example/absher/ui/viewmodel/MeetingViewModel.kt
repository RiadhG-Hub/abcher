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

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50IjoiRjNnWTA0SkduQjNDYlErUGZNZXhaM29MeHV3dlVvaE9JM2doZlB0bGllQT0iLCJkZXBhcnRtZW50IjoibmdUdmhBeXdiOFQxL3R3TWxRWkhBTzcrdzU2TVcwMzV3M1ZMU2cycnV1OD0iLCJuYW1lIjoick82WnM1dEJacGF0RjcycUhSTWNpMWx0b04zZFR3andKOEZGRWxSWWJTMVpScEtZQjIvUExsdktqaUVDZzhUZiIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMSIsImxhbmciOiJhciIsImV4cCI6MTc0MTY0OTEzMiwiaXNzIjoiSW50YWxpbyIsImF1ZCI6IkludGFsaW8ifQ.Ca_tPRc8SAQBY3yKCeqfWfW1a3EY-0J-khkUU2XZy8E"

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