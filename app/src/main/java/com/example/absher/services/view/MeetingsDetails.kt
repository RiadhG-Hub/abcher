package com.example.absher.services.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absher.R
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.domain.repository.MeetingRepository
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import com.example.absher.services.viewmodel.FetchAgendaViewModel
import com.example.absher.services.viewmodel.FetchMeetingAttendsViewModel
import com.example.absher.services.viewmodel.FetchMeetingInfoViewModel
import com.example.absher.services.viewmodel.MeetingDetailsNavigationSections
import com.example.absher.services.viewmodel.MeetingDetailsNavigationViewModel
import com.example.absher.ui.theme.AbsherTheme

class MeetingsDetails : ComponentActivity() {
    val fetchMeetingAttendsViewModel = FetchMeetingAttendsViewModel(
        GetMeetingsUseCase(
            MeetingRepository(
                RemoteMeetingDataSource(
                    MeetingApiAdapter(

                    )
                )
            )
        )
    )

    val fetchMeetingInfoViewModel = FetchMeetingInfoViewModel(
        GetMeetingsUseCase(
            MeetingRepository(
                RemoteMeetingDataSource(
                    MeetingApiAdapter(

                    )
                )
            )
        )
    )

    val fetchAgendaViewModel = FetchAgendaViewModel(
        GetMeetingsUseCase(
            MeetingRepository(
                RemoteMeetingDataSource(
                    MeetingApiAdapter(

                    )
                )
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /// val meetingId = intent.getIntExtra("MEETING_ID", 0)
        val meetingTitle = intent.getStringExtra("MEETING_TITLE" )

        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                DetailsWrapper(
                    meetingTitle = meetingTitle.toString(),
                    fetchMeetingAttendsViewModel = fetchMeetingAttendsViewModel,
                    fetchAgendaViewModel = fetchAgendaViewModel
                )

            }
        }
    }

    @Composable
    fun DetailsWrapper(
        modifier: Modifier = Modifier,
         meetingTitle: String = "Meeting Details",
        viewModel: MeetingDetailsNavigationViewModel = viewModel(),
        fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = viewModel(),
        fetchAgendaViewModel: FetchAgendaViewModel = viewModel(),
    ) {
        val selectedIndex = viewModel.selectedNavItem.value
        Scaffold(
            topBar = {
                AbcherTopAppBar(title = meetingTitle , navigationIcon = {
                    DefaultBackButton()
                }, actions = {
                    SvgIcon(R.drawable.notifications_active, modifier = Modifier.padding(end = 16.dp))
                })

            },
            content = { padding ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    NavigationTopAppBar(selectedIndex = selectedIndex)
                    Wrapper(
                        fetchMeetingAttendsViewModel = fetchMeetingAttendsViewModel,
                        fetchAgendaViewModel = fetchAgendaViewModel,
                        fetchMeetingInfoViewModel=fetchMeetingInfoViewModel
                    )

                }

            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingDetailsTopAppBar() {
    TopAppBar(
        title = { Text("My App") },
        navigationIcon = {
            IconButton(onClick = { /* Handle navigation */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = { /* Handle search */ }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }
    )
}


@Composable
private fun NavigationTopAppBar(
    modifier: Modifier = Modifier,
    selectedIndex: MeetingDetailsNavigationSections,
    viewModel: MeetingDetailsNavigationViewModel = viewModel()
) {
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
            }
        )
        NavigationItem(
            title = "جدول الأعمال",
            icon = R.drawable.calendar_today,
            index = MeetingDetailsNavigationSections.Calendar,
            selectedIndex = selectedIndex,

            onClick = { newSelection ->
                viewModel.selectNavItem(newSelection)

            }
        )
        NavigationItem(
            title = "الحضور",
            icon = R.drawable.meetingtabicon,
            index = MeetingDetailsNavigationSections.Attends,
            selectedIndex = selectedIndex,

            onClick = { newSelection ->

                viewModel.selectNavItem(newSelection)
            }
        )
        NavigationItem(
            title = "المرفقات",
            icon = R.drawable.meetingtabicon,
            index = MeetingDetailsNavigationSections.Attachments,
            selectedIndex = selectedIndex,

            onClick = { newSelection ->
                viewModel.selectNavItem(newSelection)

            }
        )
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
    val color = if (index == selectedIndex) Color(0xff39836B) else
        Color(0xff757575)
    Column(
        verticalArrangement = Arrangement.Center,

        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
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
    viewModel: MeetingDetailsNavigationViewModel = viewModel(),
    fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = viewModel(),
    fetchAgendaViewModel: FetchAgendaViewModel = viewModel(),
    fetchMeetingInfoViewModel: FetchMeetingInfoViewModel = viewModel()
) {
    when (viewModel.selectedNavItem.value) {
        MeetingDetailsNavigationSections.Meetings -> {
            fetchMeetingInfoViewModel.FetchMeetingInfos(meetingID = 2103)
            MeetingInfo(meetingId = 0, fetchMeetingInfoViewModel = fetchMeetingInfoViewModel)
        }

        MeetingDetailsNavigationSections.Calendar -> {
            fetchAgendaViewModel.fetchMeetingAgendas(meetingID = 2103)
            AgendaList(meetingId = 0, fetchAgendaViewModel = fetchAgendaViewModel)
        }

        MeetingDetailsNavigationSections.Attends -> {
            fetchMeetingAttendsViewModel.fetchMeetingAttendees(meetingID = 2103)
            AttendsList(meetingId = 0, fetchMeetingAttendsViewModel = fetchMeetingAttendsViewModel)

        }

        MeetingDetailsNavigationSections.Attachments -> {
            Text(text = viewModel.selectedNavItem.value.name)
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