package com.example.absher.services.data.datasource// data/datasource/RemoteMeetingDataSource.kt
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.data.models.Attendee
import com.example.absher.services.data.models.AttendeeResponse
import com.example.absher.services.data.models.Meeting
import javax.inject.Inject

class RemoteMeetingDataSource @Inject constructor(private val apiAdapter: MeetingApiAdapter) {
    suspend fun getMeetings(from : Int = 0,    to : Int = 10): List<Meeting>? {
        return apiAdapter.fetchMeetings(from  = from,    to = to)?.data?.data
}
suspend fun fetchMeetingAttendees(meetingId: Int): AttendeeResponse?{
    return  apiAdapter.fetchMeetingAttendees(meetingId = meetingId)
}

}