package com.example.absher.services.data.models.meetings

data class MeetingInfoResponse(
    val data: MeetingInfoData?,
    val success: Boolean,
    val message: String?
)

data class MeetingInfoData(
    val id: Int,
    val referenceNumber: String,
    val committeeName: String?,
    val createdby: Int,
    val statusId: Int,
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val isCommittee: Boolean,
    val committeeId: Int,
    val councilSessionId: Int?,
    val notes: String?,
    val readOnly: Boolean,
    val meetingAttendees: List<MeetingAttendee>
)

data class MeetingAttendee(
    val userId: Int,
    val needsApproval: Boolean,
    val jobTitle: String,
    val name: String?,
    val attended: Boolean,
    val hasProfilePicture: Boolean
)
