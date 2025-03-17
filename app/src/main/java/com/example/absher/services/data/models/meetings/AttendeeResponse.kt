package com.example.absher.services.data.models.meetings

data class AttendeeResponse(
    val data: List<Attendee>,
    val success: Boolean,
    val message: String?
)

data class Attendee(
    val fullName: String,
    val jobTitle: String,
    val needsApproval: Boolean,
    val approved: Boolean,
    val attended: Boolean
)
