package com.example.absher.services.view.recommendations.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.absher.R
import com.example.absher.services.data.models.meetings.MeetingRequestBody
import com.example.absher.services.data.models.recommendations.Recommendation
import com.example.absher.services.view.meetings.AbcherTopAppBar
import com.example.absher.services.view.meetings.DefaultBackButton
import com.example.absher.services.view.meetings.SvgIcon
import com.example.absher.services.view.recommendations.RecommendationCard
import com.example.absher.services.view.recommendations.RecommendationsSearchCard
import com.example.absher.services.viewmodel.recommendations.FetchRecommendationStateError
import com.example.absher.services.viewmodel.recommendations.FetchRecommendationStateInit
import com.example.absher.services.viewmodel.recommendations.FetchRecommendationStateLoading
import com.example.absher.services.viewmodel.recommendations.FetchRecommendationStateSuccess
import com.example.absher.services.viewmodel.recommendations.RecommendationViewModel
import com.example.absher.ui.theme.AbsherTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class RecommendationsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            AbsherTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Scaffold(
                        containerColor = MaterialTheme.colorScheme.surface, topBar = {
                            AbcherTopAppBar(
                                title = stringResource(id = R.string.files),
                                navigationIcon = {
                                    DefaultBackButton()
                                },
                                actions = {
                                    SvgIcon(
                                        R.drawable.notifications_active,
                                        modifier = Modifier.padding(end = 16.dp)
                                    )
                                })
                        }, modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        RecommendationListScreen(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = hiltViewModel(),
                        )
                    }
                }
            }
        }
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
        Text(text = "No Recommendations found")
    }
}


// RecommendationListPage.kt
@Composable
fun RecommendationListScreen(
    viewModel: RecommendationViewModel, modifier: Modifier = Modifier
) {


    Box(modifier = modifier.fillMaxSize()) {
Column {  RecommendationsSearchCard(onSearch = {
        searchValue ->
    viewModel.fetchRecommendations(requestBody = searchValue)
    // Load more when reaching the end

})

    RecommendationListViewHandler(
        viewModel,

    )}





    }
}



@Composable
fun RecommendationListViewHandler(
    viewModel: RecommendationViewModel,

) {
    val recommendationsState by viewModel.fetchRecommendationState.observeAsState(FetchRecommendationStateInit())
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
            .collect { viewModel.fetchRecommendations(requestBody = MeetingRequestBody()) }
    }


    when (recommendationsState) {
        is FetchRecommendationStateLoading -> {
            if ((recommendationsState as FetchRecommendationStateLoading) == FetchRecommendationStateLoading() && viewModel.fetchRecommendationState.value is FetchRecommendationStateSuccess) {
                // Show list with loading at bottom
                SuccessView(
                    recommendations = (viewModel.fetchRecommendationState.value as FetchRecommendationStateSuccess).recommendations,
                    isLoadingMore = true,
                    lazyListState = lazyListState
                )
            } else {
                LoadingView()
            }
        }

        is FetchRecommendationStateSuccess -> SuccessView(
            recommendations = (recommendationsState as FetchRecommendationStateSuccess).recommendations,
            isLoadingMore = false,
            lazyListState = lazyListState
        )

        is FetchRecommendationStateError -> ErrorView(
            message = (recommendationsState as FetchRecommendationStateError).error,
            onRetry = { viewModel.resetAndFetch() })

        is FetchRecommendationStateInit -> EmptyStateView()
    }
}

@Composable
private fun SuccessView(

    recommendations: List<Recommendation>?, isLoadingMore: Boolean, lazyListState: LazyListState
) {
    val context = LocalContext.current
    if (recommendations.isNullOrEmpty()) {
        EmptyStateView()
    } else {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recommendations.size) { index ->
                RecommendationCard(recommendations[index], onClick = {
                    Log.e("TAG", "SuccessView: Clicked")
                    val intent = Intent(context, RecommendationsDetails::class.java)
                    intent.putExtra("Recommendation_ID", recommendations[index].id)
                    intent.putExtra("Recommendation_TITLE", recommendations[index].meetingId)
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
fun RecommendationListScreenLoadingPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Scaffold(
                topBar = { AbcherTopAppBar() }) { paddingValues ->
                LoadingView(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}