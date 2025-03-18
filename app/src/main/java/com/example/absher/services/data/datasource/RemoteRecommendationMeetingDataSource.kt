package com.example.absher.services.data.datasource// data/datasource/RemoteMeetingDataSource.kt
import com.example.absher.services.adapter.RecommendationApiAdapter
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoResponse
import com.example.absher.services.data.models.recommendations.RecommendationResponse
import com.example.absher.services.data.models.recommendations.RecommendationStatusResponse
import javax.inject.Inject

class RemoteRecommendationMeetingDataSource @Inject constructor(private val apiAdapter: RecommendationApiAdapter) {
    suspend fun fetchRecommendations(
        from: Int = 1,
        to: Int = 10,

        ): RecommendationResponse? {
        return apiAdapter.fetchRecommendations(from, to)


    }

    suspend fun fetchRecommendationInfo(
        recommendationId: Int,

        ): FetchRecommendationInfoResponse? {
        return apiAdapter.fetchRecommendationInfo(recommendationId)
    }

    suspend fun fetchRecommendationStatuses(

    ): RecommendationStatusResponse? {
        return apiAdapter.fetchRecommendationStatuses()
    }


}