package com.example.absher.services.domain.repository// domain/repository/MeetingRepository.kt
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.data.models.meetings.AttendeeResponse
import com.example.absher.services.data.models.meetings.Meeting
import com.example.absher.services.data.models.meetings.MeetingAgendaResponse
import com.example.absher.services.data.models.meetings.MeetingAttachmentResponse
import com.example.absher.services.data.models.meetings.MeetingInfoResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeetingRepository @Inject constructor(
    private val remoteDataSource: RemoteMeetingDataSource
) {
    suspend fun getMeetings(from: Int = 0, to: Int = 10): List<Meeting>? {
        return remoteDataSource.getMeetings(from = from, to = to)
    }

    suspend fun fetchMeetingAttendees(meetingId: Int): AttendeeResponse? {
        return remoteDataSource.fetchMeetingAttendees(meetingId = meetingId)
    }

    suspend fun fetchMeetingAgendas(meetingId: Int): MeetingAgendaResponse? {
        return remoteDataSource.fetchMeetingAgendas(meetingId = meetingId)
    }

    suspend fun fetchMeetingInfo(meetingId: Int): MeetingInfoResponse? {
        return remoteDataSource.fetchMeetingInfo(meetingId = meetingId)
    }
    suspend fun fetchMeetingAttachments(meetingId: Int): MeetingAttachmentResponse?{
        return remoteDataSource.fetchMeetingAttachments(meetingId = meetingId)
    }


}