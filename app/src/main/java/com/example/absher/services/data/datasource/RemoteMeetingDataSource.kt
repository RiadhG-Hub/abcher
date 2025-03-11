package com.example.absher.services.data.datasource// data/datasource/RemoteMeetingDataSource.kt

import android.util.Log
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.data.models.MeetingRequestBody
import com.example.absher.services.adapter.MeetingApiAdapter
import javax.inject.Inject
// Path: data/datasource/RemoteMeetingDataSource.kt


class RemoteMeetingDataSource @Inject constructor(
    private val apiAdapter: MeetingApiAdapter
) {
    suspend fun getMeetings(
        page: Int = 1,
        pageSize: Int = 10,
        requestBody: MeetingRequestBody = MeetingRequestBody(
            statusId = null,
            committeeId = null,
            toDate = null,
            fromDate = null,
            onlyMyMeetings = true,
            title = null,
            location = null
        )
    ): List<Meeting>? {
        return try {
            val response = apiAdapter.meetingApiService.getMeetings(page, pageSize, requestBody)
            if (response.success) {
                response.data.data
            } else {
                Log.e("RemoteMeetingDataSource", "API call failed: ${response.message}")
                null // Or handle the error case differently based on your needs
            }
        } catch (e: Exception) {
            Log.e("RemoteMeetingDataSource", "Exception during API call: ${e.message}")
            null
        }
    }
}