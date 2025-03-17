package com.example.absher.services.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.absher.R
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.domain.repository.MeetingRepository
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import com.example.absher.services.viewmodel.FetchMeetingStateError
import com.example.absher.services.viewmodel.FetchMeetingStateInit
import com.example.absher.services.viewmodel.FetchMeetingStateLoading
import com.example.absher.services.viewmodel.FetchMeetingStateSuccess
import com.example.absher.services.viewmodel.MeetingViewModel
import com.example.absher.ui.theme.AbsherTheme
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
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Scaffold(

                    topBar = { AbcherTopAppBar(title = stringResource(id = R.string.meetings) , navigationIcon = {
                        DefaultBackButton( )
                    }, actions = {
                        SvgIcon(R.drawable.notifications_active, modifier = Modifier.padding(end = 16.dp))
                    } )},
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MeetingListScreen(
                        modifier = Modifier.padding(innerPadding), viewModel  = MeetingViewModel(
                            GetMeetingsUseCase(
                                MeetingRepository(
                                    RemoteMeetingDataSource(
                                        MeetingApiAdapter(

                                        )
                                    )
                                )
                            )
                            ),
                    )
                }}
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
                    SvgIcon(R.drawable.right, modifier = Modifier.rotate(180f).padding())
                    Text(
                        text = stringResource(id = R.string.team),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFCDB372),
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }

}

@Composable
private fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
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
    val context = LocalContext.current
    if (meetings.isNullOrEmpty()) {
        EmptyStateView()
    } else {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                ,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(meetings.size) { index ->
                MeetingCard(meetings[index], onClick = {
                    Log.e("TAG", "SuccessView: Clicked")
                        val intent = Intent(context, MeetingsDetails::class.java)
                        intent.putExtra("MEETING_ID", meetings[index].id)
                    intent.putExtra("MEETING_TITLE", meetings[index].title)
                        context.startActivity(intent)

                })
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


@Preview(showBackground = true)
@Composable
fun MeetingListScreenLoadingPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = { AbcherTopAppBar() }
        ) { paddingValues ->
            LoadingView(modifier = Modifier.padding(paddingValues))
        }
    }}
}