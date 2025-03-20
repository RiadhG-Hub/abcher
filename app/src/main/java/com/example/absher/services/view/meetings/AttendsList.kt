package com.example.absher.services.view.meetings


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.absher.R
import com.example.absher.services.data.models.meetings.Attendee
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateError
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateInit
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateLoading
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateSuccess
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendsViewModel
import com.example.absher.ui.theme.Gray
import com.example.absher.ui.theme.SubtitleColor

@Composable
fun AttendsList(
    meetingId: Int,
    fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = hiltViewModel()
) {
    val fetchMeetingState by fetchMeetingAttendsViewModel.fetchMeetingState.observeAsState(
        FetchMeetingAttendStateInit()
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (fetchMeetingState) {
            is FetchMeetingAttendStateError -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = (fetchMeetingState as FetchMeetingAttendStateError).error,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { fetchMeetingAttendsViewModel.fetchMeetingAttendees(meetingId) }) {
                        Text(
                            text = stringResource(R.string.retry),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            is FetchMeetingAttendStateInit -> {
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is FetchMeetingAttendStateLoading -> CircularProgressIndicator()
            is FetchMeetingAttendStateSuccess -> {
                val attendees = (fetchMeetingState as FetchMeetingAttendStateSuccess).meetingAttends?.data
                if (attendees.isNullOrEmpty()) {
                    Text(
                        text = stringResource(R.string.no_attendees),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn {
                        items(attendees.size) { index ->
                            AttendeeItem(attendee = attendees[index])
                            if (index < attendees.size - 1) {
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AttendeeItem(attendee: Attendee) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = attendee.fullName,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = SubtitleColor
                )
            )
            Text(
                text = attendee.jobTitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Gray
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewAttendsList() {
    AttendsList(0)
}
