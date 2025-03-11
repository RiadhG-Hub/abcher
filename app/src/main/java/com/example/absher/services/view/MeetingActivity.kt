package com.example.absher.services.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider

import com.example.absher.services.data.models.Meeting
import com.example.absher.services.viewmodel.FetchMeetingStateError
import com.example.absher.services.viewmodel.FetchMeetingStateLoading
import com.example.absher.services.viewmodel.FetchMeetingStateSuccess
import com.example.absher.services.viewmodel.MeetingViewModel
import com.example.absher.ui.screens.ui.theme.AbsherTheme

// Activity
class MeetingActivity : ComponentActivity() {
    private lateinit var viewModel: MeetingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeViewModel()
        setupContent()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this)[MeetingViewModel::class.java]
        viewModel.fetchMeetings()
    }

    private fun setupContent() {
        setContent {
            AbsherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MeetingListScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

// Composable UI Components
@Composable
fun MeetingListScreen(
    viewModel: MeetingViewModel,
    modifier: Modifier = Modifier
) {
    val meetingsState = viewModel.fetchMeetingState
    val stateValue = meetingsState.value

    when (stateValue) {
        is FetchMeetingStateLoading -> LoadingView(modifier)
        is FetchMeetingStateSuccess -> SuccessView(stateValue.meetings, modifier)
        is FetchMeetingStateError -> ErrorView(stateValue.error, modifier)
        else -> EmptyStateView(modifier)
    }
}

@Composable
private fun LoadingView(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
private fun SuccessView(meetings: List<Meeting>?, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(meetings?.size ?: 0) {
            MeetingCard(meeting = meetings?.get(it) ?: Meeting(null,null,null,null,null,null,null,null,null,null,null,null) )
        }



    }
}

@Composable
internal fun ErrorView(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = Color.Red
        )
        Button(onClick = { /* Retry logic */ }) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyStateView(modifier: Modifier) {
    Text(
        text = "No meetings found",
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// Preview
