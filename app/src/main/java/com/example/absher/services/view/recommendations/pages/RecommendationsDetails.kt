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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.BackgroundGray
import com.example.absher.ui.theme.CustomTextStyles
import dagger.hilt.android.AndroidEntryPoint

private object RecommendationDetailsStyles {
    val selectedColor = Color(0xff39836B)
    val unselectedColor = Color(0xff757575)
    val backgroundColor = Color(0xFFF2F2F2)
    
    val navigationItemText = CustomTextStyles.SmallRegular12
}

@AndroidEntryPoint
class RecommendationsDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val meetingId = intent.getIntExtra("MEETING_ID", 0)
        val meetingTitle = intent.getStringExtra("MEETING_TITLE") ?: "التوصيات"

        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                AbsherTheme() {
                DetailsWrapper(
                    meetingTitle = meetingTitle,
                    meetingID = meetingId
                )}
            }
        }
    }
}

/**
 * Top app bar for the recommendation details screen.
 * 
 * @param title The title to display in the app bar
 */
@Composable
private fun RecommendationDetailsTopBar(title: String) {
    AbcherTopAppBar(
        title = title,
        navigationIcon = { DefaultBackButton() },
        actions = {
            SvgIcon(
                R.drawable.notifications_active,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    )
}

/**
 * Main wrapper composable for the recommendation details screen.
 * Handles the overall layout and navigation between different sections.
 *
 * @param meetingTitle The title of the meeting/recommendation
 * @param meetingID The ID of the meeting/recommendation
 * @param viewModel The view model for navigation
 * @param fetchMeetingAttendsViewModel The view model for fetching attendees
 * @param fetchAgendaViewModel The view model for fetching agenda
 * @param fetchMeetingAttachmentViewModel The view model for fetching attachments
 * @param recommendationViewModel The view model for recommendation details
 */
@Composable
private fun DetailsWrapper(
    meetingTitle: String,
    meetingID: Int,
    viewModel: RecommendationDetailsNavigationViewModel = hiltViewModel(),
    fetchMeetingAttendsViewModel: FetchMeetingAttendsViewModel = hiltViewModel(),
    fetchAgendaViewModel: FetchAgendaViewModel = hiltViewModel(),
    fetchMeetingAttachmentViewModel: FetchMeetingAttachmentViewModel = hiltViewModel(),
    recommendationViewModel: RecommendationViewModel = hiltViewModel()
) {
    val selectedIndex = viewModel.selectedNavItem.value

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { RecommendationDetailsTopBar(title = meetingTitle) }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                ,
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
    viewModel: RecommendationDetailsNavigationViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.padding(top = 12.dp)) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                title = stringResource(R.string.meetings),
                icon = R.drawable.meeting_note,
                index = RecommendationDetailsNavigationSections.Info,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(id = R.string.calendar),
                icon = R.drawable.show_chart,
                index = RecommendationDetailsNavigationSections.Progress,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(id = R.string.attends),
                icon = R.drawable.meetingtabicon,
                index = RecommendationDetailsNavigationSections.Attends,
                selectedIndex = selectedIndex,
                onClick = { newSelection ->
                    viewModel.selectNavItem(newSelection)
                })
            NavigationItem(
                title = stringResource(id = R.string.attachments),
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

/**
 * A navigation item composable that displays an icon and text.
 * The item's appearance changes based on whether it is selected or not.
 *
 * @param modifier Modifier to be applied to the composable
 * @param title The text to be displayed below the icon
 * @param icon The resource ID of the icon to be displayed
 * @param index The section this navigation item represents
 * @param selectedIndex The currently selected section
 * @param onClick Callback invoked when the item is clicked
 */
@Composable
private fun NavigationItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    index: RecommendationDetailsNavigationSections,
    selectedIndex: RecommendationDetailsNavigationSections,
    onClick: (RecommendationDetailsNavigationSections) -> Unit
) {
    val color = if (index == selectedIndex) RecommendationDetailsStyles.selectedColor else RecommendationDetailsStyles.unselectedColor
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(8.dp)
            .clickable(
                onClick = { onClick(index) },
                role = androidx.compose.ui.semantics.Role.Tab
            )
    ) {
        SvgIcon(drawable = icon, defaultColor = color)
        Text(
            text = title,
            color = color,
            style = RecommendationDetailsStyles.navigationItemText
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
    Box(

        contentAlignment = Alignment.CenterStart
    ) {
        when (viewModel.selectedNavItem.value) {
            RecommendationDetailsNavigationSections.Info -> {
                recommendationViewModel.fetchRecommendationInfo(recommendationID = recommendationId)

                RecommendationInfo(meetingId = recommendationId, recommendationViewModel = recommendationViewModel)
            }
            RecommendationDetailsNavigationSections.Progress -> {
                var isLoading by remember { mutableStateOf(true) }
                LaunchedEffect(recommendationId) {
                    fetchAgendaViewModel.fetchMeetingAgendas(meetingID = recommendationId)
                    isLoading = false
                }
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    AgendaList(meetingId = recommendationId, fetchAgendaViewModel = fetchAgendaViewModel)
                }
            }
            RecommendationDetailsNavigationSections.Attends -> {
                var isLoading by remember { mutableStateOf(true) }
                LaunchedEffect(recommendationId) {
                    fetchMeetingAttendsViewModel.fetchMeetingAttendees(meetingID = recommendationId)
                    isLoading = false
                }
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    AttendsList(meetingId = recommendationId, fetchMeetingAttendsViewModel = fetchMeetingAttendsViewModel)
                }
            }
            RecommendationDetailsNavigationSections.Attachments -> {
                var isLoading by remember { mutableStateOf(true) }
                LaunchedEffect(recommendationId) {
                    fetchMeetingAttachmentViewModel.fetchMeetingAttachments(meetingID = recommendationId)
                    isLoading = false
                }
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    AttachmentList(
                        meetingId = recommendationId,
                        fetchMeetingAttachmentViewModel = fetchMeetingAttachmentViewModel
                    )
                }
            }
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