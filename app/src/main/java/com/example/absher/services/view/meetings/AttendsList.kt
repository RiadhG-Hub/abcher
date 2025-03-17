package com.example.absher.services.view.meetings


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateError
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateInit
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateLoading
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateSuccess
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendsViewModel
import com.example.absher.services.viewmodel.meetings.MeetingDetailsNavigationViewModel
import com.example.absher.ui.theme.BackgroundGray

@Composable
fun AttendsList(
    modifier: Modifier = Modifier,
    viewModel: MeetingDetailsNavigationViewModel = viewModel(),
    meetingId: Int = 0,
    fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = viewModel()
) {
    val fetchMeetingState by fetchMeetingAttendsViewModel.fetchMeetingState.observeAsState(
        FetchMeetingAttendStateInit()
    )

    when (fetchMeetingState) {
        is FetchMeetingAttendStateInit -> {
            Text(text = "Initializing...", modifier = modifier.padding(16.dp))
        }

        is FetchMeetingAttendStateLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FetchMeetingAttendStateSuccess -> {
            val attendees =
                (fetchMeetingState as FetchMeetingAttendStateSuccess).meetingAttends!!.data
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .border(1.dp, BackgroundGray, MaterialTheme.shapes.small),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ){
            Column(modifier = modifier.padding(8.dp)) {
                attendees.forEach { attendee ->
                    MeetingsAttendsCardView(title = attendee.fullName, subtitle = attendee.jobTitle)
                }
            }}
        }

        is FetchMeetingAttendStateError -> {
            val errorMessage = (fetchMeetingState as FetchMeetingAttendStateError).error
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error: $errorMessage", color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { fetchMeetingAttendsViewModel.fetchMeetingAttendees(meetingId) }) {
                    Text("Retry")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAttendsList() {
    AttendsList()
}
