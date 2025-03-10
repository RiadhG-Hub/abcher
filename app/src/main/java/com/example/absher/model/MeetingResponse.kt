package com.example.absher.model

// MeetingResponse.kt
data class MeetingResponse(
    val data: MeetingData,
    val success: Boolean,
    val message: String?
)

data class MeetingData(
    val data: List<Meeting>,
    val total: Int
)

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
    val notes: String,
    val committeeName: String,
    val statusName: String
)

// Request body model
data class MeetingSearchRequest(
    val statusId: Int?,
    val committeeId: Int?,
    val toDate: String?,
    val fromDate: String?,
    val onlyMyMeetings: Boolean,
    val title: String?,
    val location: String?
)