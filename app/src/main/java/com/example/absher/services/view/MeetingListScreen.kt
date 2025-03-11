package com.example.absher.services.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.absher.services.viewmodel.FetchMeetingStateError
import com.example.absher.services.viewmodel.FetchMeetingStateInit
import com.example.absher.services.viewmodel.FetchMeetingStateLoading
import com.example.absher.services.viewmodel.FetchMeetingStateSuccess
import com.example.absher.services.viewmodel.MeetingViewModel


@Composable
fun MeetingListViewHandler(viewModel: MeetingViewModel, modifier: Modifier = Modifier) {
    // Ensure meetings are fetched
    LaunchedEffect(Unit) {
        viewModel.fetchMeetings()
    }

    // Observe state changes
    val meetingsState by viewModel.fetchMeetingState.observeAsState()

    when (meetingsState) {
        is FetchMeetingStateLoading -> {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }

        is FetchMeetingStateSuccess -> {
            val meetings = (meetingsState as FetchMeetingStateSuccess).meetings
            if (meetings.isNullOrEmpty()) {
                Text(
                    "No meetings found",
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                )}
            else {
            LazyColumn {
                items(meetings.size) { index ->
                    MeetingCard(meetings[index] )
                }
            }}}


        is FetchMeetingStateError -> {
            val message = (meetingsState as FetchMeetingStateError).error
            ErrorView(message)
        }

        else -> {
            Text(
                "No meetings found",
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
fun ErrorView(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = Color.Red)
        Button(onClick = { /* Retry fetching meetings */ }) {
            Text("Retry")
        }
    }
}

@Composable
fun MeetingListScreen(viewModel: MeetingViewModel = hiltViewModel()) {
    val meetings by viewModel.fetchMeetingState.observeAsState(FetchMeetingStateInit())
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

                MeetingListViewHandler(viewModel = viewModel)
            }
        }
    }


}
