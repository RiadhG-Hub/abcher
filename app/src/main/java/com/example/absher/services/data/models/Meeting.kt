package com.example.absher.services.data.models

// data/models/Meeting.kt
data class Meeting(
    val id: Int,
    val statusId: Int,
    val referenceNumber: String,
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val location: String?,
    val isCommittee: Boolean,
    val notes: String?,
    val committeeName: String?,
    val statusName: String
)

// data/models/MeetingResponse.kt
data class MeetingResponse(
    val data: MeetingData,
    val success: Boolean,
    val message: String?
) {
    data class MeetingData(
        val data: List<Meeting>,
        val total: Int
    )
}