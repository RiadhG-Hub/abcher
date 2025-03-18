package com.example.absher.services.domain.repository// domain/repository/MeetingRepository.kt
import com.example.absher.services.data.datasource.RemoteRecommendationMeetingDataSource
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoResponse
import com.example.absher.services.data.models.recommendations.RecommendationResponse
import com.example.absher.services.data.models.recommendations.RecommendationStatusResponse
import javax.inject.Inject

class RecommendationRepository @Inject constructor(
    private val remoteDataSource: RemoteRecommendationMeetingDataSource
) {
    suspend fun fetchRecommendations(
        from: Int = 1,
        to: Int = 10,

        ): RecommendationResponse? {
        return remoteDataSource.fetchRecommendations(from, to)


    }

    suspend fun fetchRecommendationInfo(
        recommendationId: Int,

        ): FetchRecommendationInfoResponse? {
        return remoteDataSource.fetchRecommendationInfo(recommendationId)
    }

    suspend fun fetchRecommendationStatuses(

    ): RecommendationStatusResponse? {
        return remoteDataSource.fetchRecommendationStatuses()
    }



}