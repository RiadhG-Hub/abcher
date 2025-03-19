package com.example.absher.services.view.recommendations.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absher.R
import com.example.absher.services.view.meetings.AbcherTopAppBar
import com.example.absher.services.view.meetings.AgendaList
import com.example.absher.services.view.meetings.AttachmentList
import com.example.absher.services.view.meetings.AttendsList
import com.example.absher.services.view.meetings.DefaultBackButton
import com.example.absher.services.view.meetings.SvgIcon
import com.example.absher.services.view.recommendations.RecommendationInfo
import com.example.absher.services.viewmodel.meetings.FetchAgendaViewModel
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttachmentViewModel
import com.example.absher.services.viewmodel.meetings.FetchMeetingAttendsViewModel
import com.example.absher.services.viewmodel.recommendations.RecommendationDetailsNavigationSections
import com.example.absher.services.viewmodel.recommendations.RecommendationDetailsNavigationViewModel
import com.example.absher.services.viewmodel.recommendations.RecommendationViewModel
import com.example.absher.ui.theme.BackgroundGray

class RecommendationsDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val meetingId = intent.getIntExtra("MEETING_ID", 0)
        val meetingTitle = intent.getStringExtra("MEETING_TITLE") ?: "Meeting Details"

        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                DetailsWrapper(
                    meetingTitle = meetingTitle,
                    meetingID = meetingId
                )
            }
        }
    }
}

@Composable
private fun DetailsWrapper(
    meetingTitle: String,
    meetingID: Int,
    viewModel: RecommendationDetailsNavigationViewModel = viewModel(),
    fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = viewModel(),
    fetchAgendaViewModel: FetchAgendaViewModel = viewModel(),
    fetchMeetingAttachmentViewModel: FetchMeetingAttachmentViewModel = viewModel(),
    recommendationViewModel: RecommendationViewModel = viewModel()
) {
    val selectedIndex = viewModel.selectedNavItem.value
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            AbcherTopAppBar(
                title = meetingTitle,
                navigationIcon = { DefaultBackButton() },
                actions = {
                    SvgIcon(
                        R.drawable.notifications_active,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.background(BackgroundGray)) {
                NavigationTopAppBar(selectedIndex = selectedIndex)
            }
            Wrapper(
                viewModel = viewModel,
                fetchMeetingAttendsViewModel = fetchMeetingAttendsViewModel,
                fetchAgendaViewModel = fetchAgendaViewModel,
                fetchMeetingAttachmentViewModel = fetchMeetingAttachmentViewModel,
                recommendationId = meetingID,
                recommendationViewModel = recommendationViewModel
            )
        }
    }
}

@Composable
private fun NavigationTopAppBar(
    modifier: Modifier = Modifier,
    selectedIndex: RecommendationDetailsNavigationSections,
    viewModel: RecommendationDetailsNavigationViewModel = viewModel()
) {
    Column(modifier = Modifier.padding(top = 12.dp)) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                title = stringResource(R.string.meetings),
                icon = R.drawable.note,
                index = RecommendationDetailsNavigationSections.Info,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(
                    id = R.string.calendar
                ),
                icon = R.drawable.show_chart,
                index = RecommendationDetailsNavigationSections.Progress,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(
                    id = R.string.attends
                ),
                icon = R.drawable.meetingtabicon,
                index = RecommendationDetailsNavigationSections.Attends,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(
                    id = R.string.attachments
                ),
                icon = R.drawable.file_copy,
                index = RecommendationDetailsNavigationSections.Attachments,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
        }
        HorizontalDivider()
    }
}

@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    index: RecommendationDetailsNavigationSections,
    selectedIndex: RecommendationDetailsNavigationSections,
    onClick: (RecommendationDetailsNavigationSections) -> Unit
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
    viewModel: RecommendationDetailsNavigationViewModel,
    fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel,
    fetchAgendaViewModel: FetchAgendaViewModel,
    fetchMeetingAttachmentViewModel: FetchMeetingAttachmentViewModel,
    recommendationId: Int,
    recommendationViewModel: RecommendationViewModel
) {
    when (viewModel.selectedNavItem.value) {
        RecommendationDetailsNavigationSections.Info -> {
            recommendationViewModel.fetchRecommendationInfo(recommendationID = recommendationId)
            RecommendationInfo(meetingId = recommendationId, recommendationViewModel = recommendationViewModel)
        }
        RecommendationDetailsNavigationSections.Progress -> {
            fetchAgendaViewModel.fetchMeetingAgendas(meetingID = recommendationId)
            AgendaList(meetingId = recommendationId, fetchAgendaViewModel = fetchAgendaViewModel)
        }
        RecommendationDetailsNavigationSections.Attends -> {
            fetchMeetingAttendsViewModel.fetchMeetingAttendees(meetingID = recommendationId)
            AttendsList(meetingId = recommendationId, fetchMeetingAttendsViewModel = fetchMeetingAttendsViewModel)
        }
        RecommendationDetailsNavigationSections.Attachments -> {
            fetchMeetingAttachmentViewModel.fetchMeetingAttachments(meetingID = recommendationId)
            AttachmentList(
                meetingId = recommendationId,
                fetchMeetingAttachmentViewModel = fetchMeetingAttachmentViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNavigationItem() {
    com.example.absher.ui.theme.AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            NavigationTopAppBar(selectedIndex = RecommendationDetailsNavigationSections.Attends)
        }
    }
}