package com.example.absher.services.viewmodel.meetings// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.services.data.models.meetings.Meeting
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MeetingViewModel(
    private val getMeetingsUseCase: GetMeetingsUseCase
) : ViewModel() {
    private val _fetchMeetingState = MutableLiveData<FetchMeetingState>()
    val fetchMeetingState: LiveData<FetchMeetingState> = _fetchMeetingState

    val isDarkMode = MutableStateFlow(false)

    private var currentPage = 1
    private val pageSize = 10
    private var isLoading = false
    private var hasMoreData = true
    private val allMeetings = mutableListOf<Meeting>()

    init {
        fetchMeetings()
    }

    fun fetchMeetings() {
        if (isLoading || !hasMoreData) return

        isLoading = true
        _fetchMeetingState.value = FetchMeetingStateLoading()

        viewModelScope.launch {
            try {
                val response = getMeetingsUseCase.execute(
                    from = currentPage,
                    to = pageSize
                )
                val newMeetings  = response ?: emptyList()

                allMeetings.addAll(newMeetings)
                hasMoreData = newMeetings.size == pageSize
                currentPage++

                _fetchMeetingState.postValue(
                    FetchMeetingStateSuccess(allMeetings.toList())
                )
            } catch (e: Exception) {
                _fetchMeetingState.postValue(
                    FetchMeetingStateError(e.message ?: "Unknown error")
                )
            } finally {
                isLoading = false
            }
        }
    }

    fun resetAndFetch() {
        currentPage = 1
        allMeetings.clear()
        hasMoreData = true
        fetchMeetings()
    }
}

// Define states
sealed class FetchMeetingState
class FetchMeetingStateInit : FetchMeetingState()
class FetchMeetingStateLoading : FetchMeetingState()
data class FetchMeetingStateSuccess(val meetings: List<Meeting>?) : FetchMeetingState()
data class FetchMeetingStateError(val error: String) : FetchMeetingState()