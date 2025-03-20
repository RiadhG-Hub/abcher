package com.example.absher.ui.theme

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    private val _isDarkTheme = MutableStateFlow(sharedPreferences.getBoolean("is_dark_theme", false))
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun toggleTheme() {
        viewModelScope.launch {
            _isDarkTheme.value = !_isDarkTheme.value
            sharedPreferences.edit().putBoolean("is_dark_theme", _isDarkTheme.value).apply()
        }
    }
} 