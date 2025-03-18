package com.example.absher.services.data.models.recommendations

data class RecommendationResponse(
    val data: RecommendationData?,
    val success: Boolean,
    val message: String?
)

data class RecommendationData(
    val data: List<Recommendation>,
    val total: Int
)

data class Recommendation(
    val id: Int,
    val createdBy: Int,
    val dueDate: String,
    val createdAt: String,
    val meetingAgendaId: Int,
    val text: String,
    val statusId: Int,
    val createdByName: String,
    val owner: Int,
    val ownerName: String,
    val percentage: Int,
    val status: String,
    val meetingId: Int,
    val meetingReferenceNo: String,
    val committeeName: String,
    val recommendationName: String,
    val canEdit: Boolean
)