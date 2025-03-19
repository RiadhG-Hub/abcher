package com.example.absher.services.viewmodel.recommendations

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationDetailsNavigationViewModel @Inject constructor() : ViewModel() {
    // Use mutableStateOf for Compose state
    private val _selectedNavItem = mutableStateOf(RecommendationDetailsNavigationSections.Info)
    val selectedNavItem = _selectedNavItem // Expose as a read-only State

    fun selectNavItem(item: RecommendationDetailsNavigationSections) {
        viewModelScope.launch {
            _selectedNavItem.value = item
        }
    }
}

enum class RecommendationDetailsNavigationSections {
    Info, Progress, Attends, Attachments
}

