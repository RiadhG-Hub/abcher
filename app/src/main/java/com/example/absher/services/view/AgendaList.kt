package com.example.absher.services.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.absher.services.viewmodel.FetchAgendaViewModel
import com.example.absher.services.viewmodel.FetchMeetingAgendaStateError
import com.example.absher.services.viewmodel.FetchMeetingAgendaStateInit
import com.example.absher.services.viewmodel.FetchMeetingAgendaStateLoading
import com.example.absher.services.viewmodel.FetchMeetingAgendaStateSuccess
import com.example.absher.services.viewmodel.MeetingDetailsNavigationViewModel

@Composable
fun AgendaList(
    modifier: Modifier = Modifier,
    viewModel: MeetingDetailsNavigationViewModel = viewModel(),
    meetingId: Int = 0,
    fetchAgendaViewModel: FetchAgendaViewModel = viewModel()
) {
    val fetchMeetingState by fetchAgendaViewModel.fetchMeetingState.observeAsState(
        FetchMeetingAgendaStateInit())

    when (fetchMeetingState) {
        is FetchMeetingAgendaStateInit -> {
            Text(text = "Initializing...", modifier = modifier.padding(16.dp))
        }

        is FetchMeetingAgendaStateLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FetchMeetingAgendaStateSuccess -> {
            val attendees = (fetchMeetingState as FetchMeetingAgendaStateSuccess).meetingAgenda!!.data
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start ) {
                attendees.forEach { agenda ->
                    MeetingAgendaCardView(text = agenda.title)
                }
            }
        }

        is FetchMeetingAgendaStateError -> {
            val errorMessage = (fetchMeetingState as FetchMeetingAgendaStateError).error
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error: $errorMessage", color = Color.Red )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { fetchAgendaViewModel.fetchMeetingAgendas(meetingId) }) {
                    Text("Retry")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAgendaList() {
    AgendaList()
}
