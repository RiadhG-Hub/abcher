package com.example.absher.services.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.view.ui.theme.AbsherTheme
import com.example.absher.services.viewmodel.*

class MeetingListPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AbsherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MeetingListScreen(
                        modifier = Modifier.padding(innerPadding),

                    )
                }
            }
        }
    }
}

@Composable
fun MeetingListScreen(
    viewModel: MeetingViewModel   = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle(initialValue = false)
    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        Box(modifier = modifier.fillMaxSize()) {
            MeetingListViewHandler(viewModel)
        }
    }
}

@Composable
fun MeetingListViewHandler(viewModel: MeetingViewModel) {
    val meetingsState by viewModel.fetchMeetingState.observeAsState(FetchMeetingStateInit())

    LaunchedEffect(Unit) { viewModel.fetchMeetings() }

    when (meetingsState) {
        is FetchMeetingStateLoading -> LoadingView()
        is FetchMeetingStateSuccess -> SuccessView((meetingsState as FetchMeetingStateSuccess).meetings)
        is FetchMeetingStateError -> ErrorView((meetingsState as FetchMeetingStateError).error)
        else -> EmptyStateView()
    }
}

@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessView(meetings: List<Meeting>?) {
    if (meetings.isNullOrEmpty()) {
        EmptyStateView()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(meetings.size) { index ->
                MeetingCard(meetings[index])
            }
        }
    }
}

@Composable
private fun ErrorView(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Implement retry logic */ }) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyStateView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "No meetings found")
    }
}
