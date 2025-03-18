package com.example.absher.services.data.models.meetings

data class MeetingRequestBody(
    val statusId: Any? = null,
    val committeeId: Any? = null,
    val toDate: Any? = null,
    val fromDate: Any? = null,
    val onlyMyMeetings: Boolean = true,
    val title: Any? = null,
    val location: Any? = null,
    val meetingReferenceNo: Any? = null
)