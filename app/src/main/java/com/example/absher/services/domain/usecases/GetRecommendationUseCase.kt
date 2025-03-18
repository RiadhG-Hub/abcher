package com.example.absher.services.domain.usecases// domain/usecases/GetMeetingsUseCase.kt
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoResponse
import com.example.absher.services.data.models.recommendations.RecommendationResponse
import com.example.absher.services.data.models.recommendations.RecommendationStatusResponse
import com.example.absher.services.domain.repository.RecommendationRepository
import javax.inject.Inject

class GetRecommendationUseCase @Inject constructor(
    private val recommendationRepository: RecommendationRepository
) {
    suspend fun fetchRecommendations(
        from: Int = 1,
        to: Int = 10,

        ): RecommendationResponse? {
        return recommendationRepository.fetchRecommendations(from, to)



    }

    suspend fun fetchRecommendationInfo(
        recommendationId: Int,

        ): FetchRecommendationInfoResponse? {
        return recommendationRepository.fetchRecommendationInfo(recommendationId)
    }

    suspend fun fetchRecommendationStatuses(

    ): RecommendationStatusResponse? {
        return recommendationRepository.fetchRecommendationStatuses()
    }

}