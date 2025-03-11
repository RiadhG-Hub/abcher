package com.example.absher.services.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.absher.R
import com.example.absher.services.adapter.AuthInterceptor
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.adapter.TokenManager
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.domain.repository.MeetingRepository
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import com.example.absher.services.view.ui.theme.AbsherTheme
import com.example.absher.services.viewmodel.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

// Path: view/MeetingListPage.kt


class MeetingListPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AbsherTheme {
                Scaffold(

                    topBar = { MeetingListScreenTopAppBar() },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MeetingListScreen(
                        modifier = Modifier.padding(innerPadding), viewModel  = MeetingViewModel(
                            GetMeetingsUseCase(
                                MeetingRepository(
                                    RemoteMeetingDataSource(
                                        MeetingApiAdapter(
                                            AuthInterceptor(
                                                TokenManager(
                                                    LocalContext.current.getSharedPreferences("app_prefs",
                                                        MODE_PRIVATE
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                            ),
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingListScreenTopAppBar() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(
            color = Color(0xFFCDB372),
            shape = RoundedCornerShape(
                bottomEnd = 16.dp, // Bottom-right corner
                bottomStart = 16.dp // Bottom-left corner
            )
        )){
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start, // Align content to the right
                    modifier = Modifier.fillMaxWidth()
                ) {


                    // Add spacing between icon and text
                    SvgIcon(R.drawable.right)
                    Text(
                        text = stringResource(id = R.string.team),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }

}

@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessView(meetings: List<Meeting>?) {
    if (meetings.isNullOrEmpty()) {
        EmptyStateView()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(meetings.size) { index ->
                MeetingCard(meetings[index])
            }
        }
    }
}

@Composable
private fun ErrorView(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Retry logic here if needed */ }) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyStateView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "No meetings found")
    }
}


// MeetingListPage.kt
@Composable
fun MeetingListScreen(
    viewModel: MeetingViewModel,
    modifier: Modifier = Modifier
) {
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle(initialValue = false)
    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        Box(modifier = modifier.fillMaxSize()) {
            MeetingListViewHandler(viewModel)
        }
    }
}

@Composable
fun MeetingListViewHandler(viewModel: MeetingViewModel) {
    val meetingsState by viewModel.fetchMeetingState.observeAsState(FetchMeetingStateInit())
    val lazyListState = rememberLazyListState()

    // Load more when reaching the end
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo }
            .map { info ->
                val totalItems = info.totalItemsCount
                val lastVisibleItem = info.visibleItemsInfo.lastOrNull()?.index ?: 0
                totalItems > 0 && lastVisibleItem >= totalItems - 2
            }
            .distinctUntilChanged()
            .filter { it }
            .collect { viewModel.fetchMeetings() }
    }

    when (meetingsState) {
        is FetchMeetingStateLoading -> {
            if ((meetingsState as FetchMeetingStateLoading) == FetchMeetingStateLoading() &&
                viewModel.fetchMeetingState.value is FetchMeetingStateSuccess) {
                // Show list with loading at bottom
                SuccessView(
                    meetings = (viewModel.fetchMeetingState.value as FetchMeetingStateSuccess).meetings,
                    isLoadingMore = true,
                    lazyListState = lazyListState
                )
            } else {
                LoadingView()
            }
        }
        is FetchMeetingStateSuccess -> SuccessView(
            meetings = (meetingsState as FetchMeetingStateSuccess).meetings,
            isLoadingMore = false,
            lazyListState = lazyListState
        )
        is FetchMeetingStateError -> ErrorView(
            message = (meetingsState as FetchMeetingStateError).error,
            onRetry = { viewModel.resetAndFetch() }
        )
        is FetchMeetingStateInit -> EmptyStateView()
    }
}

@Composable
private fun SuccessView(
    meetings: List<Meeting>?,
    isLoadingMore: Boolean,
    lazyListState: LazyListState
) {
    if (meetings.isNullOrEmpty()) {
        EmptyStateView()
    } else {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(meetings.size) { index ->
                MeetingCard(meetings[index])
            }

            if (isLoadingMore) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}