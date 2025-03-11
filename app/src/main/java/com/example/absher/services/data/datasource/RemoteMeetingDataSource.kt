package com.example.absher.services.data.datasource// data/datasource/RemoteMeetingDataSource.kt
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.data.models.Meeting
import javax.inject.Inject

class RemoteMeetingDataSource @Inject constructor(private val apiAdapter: MeetingApiAdapter) {
    suspend fun getMeetings(): List<Meeting> {
        return apiAdapter.fetchMeetings().data.data
}}