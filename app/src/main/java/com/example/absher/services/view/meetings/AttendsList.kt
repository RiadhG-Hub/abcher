package com.example.absher.services.view.meetings


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.absher.R
import com.example.absher.services.data.models.meetings.Attendee
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateError
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateInit
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateLoading
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendStateSuccess
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendsViewModel
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.BackgroundGray
import com.example.absher.ui.theme.BorderCircleColor
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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .border(1.dp, BackgroundGray, MaterialTheme.shapes.small),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ){
                    LazyColumn (modifier = Modifier.padding(8.dp)){
                        items(attendees.size) { index ->
                            AttendeeItem(attendee = attendees[index])
                            if (index < attendees.size - 1) {
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }}
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
            .padding(0.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.padding(end = 8.dp)
                .size(60.dp) // Set size
                .clip(CircleShape) // Clip to a circle
                .border(2.dp,BorderCircleColor, CircleShape) // Add a border
        ) {
            Image(
                painter = painterResource(id = R.drawable.man), // Replace with your image
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Crop the image to fill the circle
            )
        }
        Column {
            Text(
                text = attendee.fullName,
                style = MaterialTheme.typography.displaySmall.copy(

                    color = Gray
                )
            )
            Text(
                text = attendee.jobTitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SubtitleColor
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAttendsList() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    AttendeeItem(attendee = Attendee(
        fullName = "John",
        jobTitle = "Po",
        needsApproval = false,
        approved = true,
        attended = true
    ))}}
}

