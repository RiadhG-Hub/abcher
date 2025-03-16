package com.example.absher.services.data.models

import com.google.gson.annotations.SerializedName

data class MeetingAgendaResponse(
    @SerializedName("data") val data: List<MeetingAgenda>,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String?
)

data class MeetingAgenda(
    @SerializedName("id") val id: Int,
    @SerializedName("meetingId") val meetingId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("voting") val voting: String,
    @SerializedName("committeeDutyId") val committeeDutyId: Int?,
    @SerializedName("votingTypeId") val votingTypeId: Int,
    @SerializedName("agendaTopics") val agendaTopics: List<AgendaTopic>,
    @SerializedName("votingType") val votingType: VotingType?,
    @SerializedName("meetingAgendaRecommendations") val meetingAgendaRecommendations: Any?
)

data class AgendaTopic(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class VotingType(
    @SerializedName("id") val id: Int,
    @SerializedName("nameAr") val nameAr: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("active") val active: Boolean,
    @SerializedName("displayOrder") val displayOrder: Int,
    @SerializedName("votingOptions") val votingOptions: List<VotingOption>
)

data class VotingOption(
    @SerializedName("id") val id: Int,
    @SerializedName("nameAr") val nameAr: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("weight") val weight: Int,
    @SerializedName("active") val active: Boolean,
    @SerializedName("displayOrder") val displayOrder: Int,
    @SerializedName("votingTypeId") val votingTypeId: Int
)
