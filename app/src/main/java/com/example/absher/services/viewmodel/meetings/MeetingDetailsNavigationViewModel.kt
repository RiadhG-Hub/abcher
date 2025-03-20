package com.example.absher.services.viewmodel.meetings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingDetailsNavigationViewModel @Inject constructor() : ViewModel() {
    // Use mutableStateOf for Compose state
    private val _selectedNavItem = mutableStateOf(MeetingDetailsNavigationSections.Meetings)
    val selectedNavItem = _selectedNavItem // Expose as a read-only State

    fun selectNavItem(item: MeetingDetailsNavigationSections) {
        viewModelScope.launch {
            _selectedNavItem.value = item
        }
    }
}

enum class MeetingDetailsNavigationSections {
    Meetings, Calendar, Attends, Attachments
}

