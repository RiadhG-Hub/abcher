package com.example.absher.services.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.absher.services.viewmodel.MeetingViewModel

@Composable
fun MeetingListScreen(viewModel: MeetingViewModel = hiltViewModel()) {
    val meetings by viewModel.meetings.observeAsState(emptyList())
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle(initialValue = false)

    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(

        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Button(
                    onClick = { viewModel.fetchMeetings() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Fetch Meetings")
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(meetings) { meeting ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Title: ${meeting.title}", style = MaterialTheme.typography.titleMedium)
                                Text("Date: ${meeting.date}")
                                Text("Time: ${meeting.startTime} - ${meeting.endTime}")
                                Text("Location: ${meeting.location ?: "N/A"}")
                                Text("Status: ${meeting.statusName}")
                            }
                        }
                    }
                }
            }
        }
    }
}
