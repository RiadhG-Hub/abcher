package com.example.absher.services.viewmodel// viewmodel/MeetingViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import javax.inject.Inject

@HiltViewModel
class MeetingViewModel @Inject constructor(
    private val getMeetingsUseCase: GetMeetingsUseCase,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val _meetings = MutableLiveData<List<Meeting>>()
    val meetings: LiveData<List<Meeting>> get() = _meetings

    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[DARK_MODE_KEY] ?: false }

    fun fetchMeetings() {
        viewModelScope.launch {
            val meetingList = getMeetingsUseCase()
            _meetings.postValue(meetingList)
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[DARK_MODE_KEY] = enabled
            }
        }
    }
}