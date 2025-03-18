package com.example.absher.services.data.datasource// data/datasource/RemoteMeetingDataSource.kt
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.data.models.meetings.AttendeeResponse
import com.example.absher.services.data.models.meetings.Meeting
import com.example.absher.services.data.models.meetings.MeetingAgendaResponse
import com.example.absher.services.data.models.meetings.MeetingAttachmentResponse
import com.example.absher.services.data.models.meetings.MeetingInfoResponse
import javax.inject.Inject

class RemoteMeetingDataSource @Inject constructor(private val apiAdapter: MeetingApiAdapter) {
    suspend fun getMeetings(from: Int = 0, to: Int = 10): List<Meeting>? {
        return apiAdapter.fetchMeetings(from = from, to = to)?.data?.data
    }

    suspend fun fetchMeetingAttendees(meetingId: Int): AttendeeResponse? {
        return apiAdapter.fetchMeetingAttendees(meetingId = meetingId)
    }

    suspend fun fetchMeetingAgendas(meetingId: Int): MeetingAgendaResponse? {
        return apiAdapter.fetchMeetingAgendas(meetingId = meetingId)
    }

    suspend fun fetchMeetingInfo(meetingId: Int): MeetingInfoResponse? {
        return apiAdapter.fetchMeetingInfo(meetingId = meetingId)
    }
    suspend fun fetchMeetingAttachments(meetingId: Int): MeetingAttachmentResponse?{
        return apiAdapter.fetchMeetingAttachments(meetingId = meetingId)
    }




}