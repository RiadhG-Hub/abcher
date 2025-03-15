package com.example.absher.services.adapter

import android.content.Context.MODE_PRIVATE
import androidx.compose.ui.platform.LocalContext
import com.example.absher.services.data.models.MeetingRequestBody
import com.example.absher.services.data.models.MeetingResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MeetingApiAdapterTest {
    private lateinit var meetingApiAdapter: MeetingApiAdapter

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MeetingApiService::class.java)
        meetingApiAdapter = MeetingApiAdapter() // Use real interceptor if required
    }

    @Test
    fun `fetchMeetings should return real MeetingResponse`() = runBlocking {
        val result = meetingApiAdapter.fetchMeetings(1, 10)



        println("Fetched Meetings: $result")
    }


    @Test
    fun `fetchMeetingAttendees should return real MeetingResponse`() = runBlocking {
        val result = meetingApiAdapter.fetchMeetingAttendees(meetingId = 2103 )



        println("Fetch meeting attends $result")
    }
}
