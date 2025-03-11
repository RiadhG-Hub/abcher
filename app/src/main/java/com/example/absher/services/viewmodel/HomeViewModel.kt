package com.example.absher.services.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    // Use mutableStateOf for Compose state
    private val _selectedNavItem = mutableStateOf("home")
    val selectedNavItem = _selectedNavItem // Expose as a read-only State

    fun selectNavItem(item: String) {
        viewModelScope.launch {
            _selectedNavItem.value = item
        }
    }
}