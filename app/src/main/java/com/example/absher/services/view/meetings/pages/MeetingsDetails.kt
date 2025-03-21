package com.example.absher.services.view.meetings.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.absher.R
import com.example.absher.services.view.meetings.AbcherTopAppBar
import com.example.absher.services.view.meetings.AgendaList
import com.example.absher.services.view.meetings.AttachmentList
import com.example.absher.services.view.meetings.AttendsList
import com.example.absher.services.view.meetings.DefaultBackButton
import com.example.absher.services.view.meetings.MeetingInfo
import com.example.absher.services.view.meetings.SvgIcon
import com.example.absher.services.viewmodel.meetings.FetchAgendaViewModel
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttachmentViewModel
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendsViewModel
import com.example.absher.services.viewmodel.meetings.FetchMeetingInfoViewModel
import com.example.absher.services.viewmodel.meetings.MeetingDetailsNavigationSections
import com.example.absher.services.viewmodel.meetings.MeetingDetailsNavigationViewModel
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.BackgroundGray
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeetingsDetails : ComponentActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val meetingId = intent.getIntExtra("MEETING_ID", 0)
        val meetingTitle = intent.getStringExtra("MEETING_TITLE")

        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                AbsherTheme {
                    DetailsWrapper(
                        meetingTitle = meetingTitle.toString(),
                        fetchMeetingAttendsViewModel = hiltViewModel(),
                        fetchAgendaViewModel = hiltViewModel(),
                        fetchMeetingAttachmentViewModel = hiltViewModel(),
                        meetingID = meetingId
                    )
                }
            }
        }
    }

    @Composable
    private fun DetailsWrapper(
        meetingTitle: String = "Meeting Details",
        viewModel: MeetingDetailsNavigationViewModel = hiltViewModel(),
        fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = hiltViewModel(),
        fetchAgendaViewModel: FetchAgendaViewModel = hiltViewModel(),
        fetchMeetingAttachmentViewModel: FetchMeetingAttachmentViewModel = hiltViewModel(),
        meetingID: Int
    ) {
        val selectedIndex = viewModel.selectedNavItem.value
        Scaffold(containerColor = MaterialTheme.colorScheme.surface, topBar = {
            AbcherTopAppBar(title = meetingTitle, navigationIcon = {
                DefaultBackButton()
            }, actions = {
                SvgIcon(
                    R.drawable.notifications_active, modifier = Modifier.padding(end = 16.dp)
                )
            })

        }, content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(padding)

                    .padding(0.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(modifier = Modifier.background(BackgroundGray)) {
                    NavigationTopAppBar(selectedIndex = selectedIndex)
                }
                Wrapper(
                    fetchMeetingAttendsViewModel = fetchMeetingAttendsViewModel,
                    fetchAgendaViewModel = fetchAgendaViewModel,
                    fetchMeetingInfoViewModel = hiltViewModel(),
                    fetchMeetingAttachmentViewModel = fetchMeetingAttachmentViewModel,
                    meetingID = meetingID
                )

            }

        })
    }
}


@Composable
private fun NavigationTopAppBar(
    modifier: Modifier = Modifier,
    selectedIndex: MeetingDetailsNavigationSections,
    viewModel: MeetingDetailsNavigationViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.padding(top = 12.dp)) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                title = stringResource(R.string.meetings),
                icon = R.drawable.meetingtabicon,
                index = MeetingDetailsNavigationSections.Meetings,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(id = R.string.calendar),
                icon = R.drawable.event_note,
                index = MeetingDetailsNavigationSections.Calendar,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(id = R.string.attends),
                icon = R.drawable.meetingtabicon,
                index = MeetingDetailsNavigationSections.Attends,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(id = R.string.attachments),
                icon = R.drawable.file_copy,
                index = MeetingDetailsNavigationSections.Attachments,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
        }
        HorizontalDivider()
    }
}


@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    index: MeetingDetailsNavigationSections,
    selectedIndex: MeetingDetailsNavigationSections,
    onClick: (MeetingDetailsNavigationSections) -> Unit
) {
    val color = if (index == selectedIndex) Color(0xff39836B) else Color(0xff757575)
    Column(
        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick(index)
            }) {
        SvgIcon(drawable = icon, defaultColor = color)
        Text(
            text = title,
            color = color,
            fontSize = 12.sp,
            lineHeight = 20.sp,

            fontWeight = FontWeight(400),


            textAlign = TextAlign.Center,

            )

    }
}


@Composable
private fun Wrapper(
    viewModel: MeetingDetailsNavigationViewModel = hiltViewModel(),
    fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = hiltViewModel(),
    fetchAgendaViewModel: FetchAgendaViewModel = hiltViewModel(),
    fetchMeetingInfoViewModel: FetchMeetingInfoViewModel = hiltViewModel(),
    fetchMeetingAttachmentViewModel: FetchMeetingAttachmentViewModel = hiltViewModel(),
    meetingID: Int
) {
    when (viewModel.selectedNavItem.value) {
        MeetingDetailsNavigationSections.Meetings -> {
            fetchMeetingInfoViewModel.fetchMeetingInfos(meetingID = meetingID)
            MeetingInfo(meetingId = 0, fetchMeetingInfoViewModel = fetchMeetingInfoViewModel)
        }

        MeetingDetailsNavigationSections.Calendar -> {
            fetchAgendaViewModel.fetchMeetingAgendas(meetingID = meetingID)
            AgendaList(meetingId = 0, fetchAgendaViewModel = fetchAgendaViewModel)
        }

        MeetingDetailsNavigationSections.Attends -> {
            fetchMeetingAttendsViewModel.fetchMeetingAttendees(meetingID = meetingID)
            AttendsList(meetingId = 0, fetchMeetingAttendsViewModel = fetchMeetingAttendsViewModel)
        }

        MeetingDetailsNavigationSections.Attachments -> {
            fetchMeetingAttachmentViewModel.fetchMeetingAttachments(meetingID = meetingID)
            AttachmentList(
                meetingId = 0, fetchMeetingAttachmentViewModel = fetchMeetingAttachmentViewModel
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewNavigationItem() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            NavigationTopAppBar(selectedIndex = MeetingDetailsNavigationSections.Attends)

        }
    }
}