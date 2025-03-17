package com.example.absher.services.data.models.meetings

data class MeetingAttachmentResponse(
val data: MeetingAttachmentData?,
val success: Boolean,
val message: String?
)

data class MeetingAttachmentData(

    val meetingAttachments: List<MeetingAttachment>
)

data class MeetingAttachment(
    val userId: Int,
    val needsApproval: Boolean,
    val jobTitle: String,
    val name: String?,
    val attended: Boolean,
    val hasProfilePicture: Boolean
)

