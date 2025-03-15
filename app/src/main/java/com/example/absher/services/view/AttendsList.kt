package com.example.absher.services.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.absher.services.viewmodel.FetchMeetingAttendStateError
import com.example.absher.services.viewmodel.FetchMeetingAttendStateInit
import com.example.absher.services.viewmodel.FetchMeetingAttendStateLoading
import com.example.absher.services.viewmodel.FetchMeetingAttendStateSuccess
import com.example.absher.services.viewmodel.FetchMeetingAttendsViewModel
import com.example.absher.services.viewmodel.MeetingDetailsNavigationViewModel

@Composable
fun AttendsList(
    modifier: Modifier = Modifier,
    viewModel: MeetingDetailsNavigationViewModel = viewModel(),
    meetingId: Int = 0,
    fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = viewModel()
) {
    val fetchMeetingState by fetchMeetingAttendsViewModel.fetchMeetingState.observeAsState(FetchMeetingAttendStateInit())

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
            val attendees = (fetchMeetingState as FetchMeetingAttendStateSuccess).meetingAttends!!.data
            Column(modifier = modifier.padding(16.dp)) {
                attendees.forEach { attendee ->
                    MeetingsAttendsCardView(title = attendee.fullName, subtitle = attendee.jobTitle)
                }
            }
        }

        is FetchMeetingAttendStateError -> {
            val errorMessage = (fetchMeetingState as FetchMeetingAttendStateError).error
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error: $errorMessage", color = Color.Red )
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
