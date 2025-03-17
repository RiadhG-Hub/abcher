package com.example.absher.services.domain.usecases// domain/usecases/GetMeetingsUseCase.kt
import com.example.absher.services.data.models.AttendeeResponse
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.data.models.MeetingAgendaResponse
import com.example.absher.services.data.models.MeetingAttachmentResponse
import com.example.absher.services.data.models.MeetingInfoResponse
import com.example.absher.services.domain.repository.MeetingRepository
import javax.inject.Inject

class GetMeetingsUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    suspend fun execute(from: Int = 0, to: Int = 10): List<Meeting>? {
        return meetingRepository.getMeetings(from = from, to = to)
    }

    suspend fun fetchMeetingAttendees(meetingId: Int): AttendeeResponse? {
        return meetingRepository.fetchMeetingAttendees(meetingId = meetingId)

    }

    suspend fun fetchMeetingAgendas(meetingId: Int): MeetingAgendaResponse? {
        return meetingRepository.fetchMeetingAgendas(meetingId = meetingId)
    }

    suspend fun fetchMeetingInfo(meetingId: Int): MeetingInfoResponse? {
        return meetingRepository.fetchMeetingInfo(meetingId = meetingId)
    }
    suspend fun fetchMeetingAttachments(meetingId: Int): MeetingAttachmentResponse?{
        return meetingRepository.fetchMeetingAttachments(meetingId = meetingId)
    }

}