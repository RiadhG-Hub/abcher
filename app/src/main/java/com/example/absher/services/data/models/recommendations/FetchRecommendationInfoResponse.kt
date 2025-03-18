package com.example.absher.services.data.models.recommendations

data class FetchRecommendationInfoResponse(
    val data: FetchRecommendationInfoData,
    val success: Boolean,
    val message: String?
)

data class FetchRecommendationInfoData(
    val id: Int,
    val createdBy: Int,
    val createdAt: String,
    val dueDate: String,
    val meetingAgendaId: Int,
    val text: String,
    val statusId: Int,
    val createdByName: String,
    val owner: Int,
    val ownerName: String,
    val recommendationTypeId: Int,
    val recommendationTypeName: String,
    val percentage: Int,
    val status: String
)