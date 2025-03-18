package com.example.absher.services.data.models.recommendations

data class RecommendationStatusResponse(
    val data: List<RecommendationStatus>,
    val success: Boolean,
    val message: String?
)

data class RecommendationStatus(
    val id: Int,
    val name: String
)
