package com.example.absher.services.domain.repository// domain/repository/MeetingRepository.kt
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.data.models.AttendeeResponse
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.data.models.MeetingAgendaResponse
import com.example.absher.services.data.models.MeetingInfoResponse
import javax.inject.Inject

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


}