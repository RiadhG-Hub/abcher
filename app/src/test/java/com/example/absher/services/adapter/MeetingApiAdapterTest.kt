package com.example.absher.services.adapter

import com.example.absher.services.adapter.HttpExceptionHandler
import com.example.absher.services.adapter.TokenApiAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MeetingApiAdapterTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var meetingApiAdapter: MeetingApiAdapter

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `fetchMeetings should return real MeetingResponse`() = runBlocking {
        val result = meetingApiAdapter.fetchMeetings(1, 10)
        println("Fetched Meetings: $result")
    }

    @Test
    fun `fetchMeetingAttendees should return real MeetingResponse`() = runBlocking {
        val result = meetingApiAdapter.fetchMeetingAttendees(meetingId = 2103)
        println("Fetch meeting attends $result")
    }

    @Test
    fun `fetchMeetingAgenda should return real MeetingResponse`() = runBlocking {
        val result = meetingApiAdapter.fetchMeetingAgendas(meetingId = 2103)
        println("Fetch meeting attends $result")
    }

    @Test
    fun `fetchMeetingInfo should return real MeetingInfo`() = runBlocking {
        val result = meetingApiAdapter.fetchMeetingInfo(meetingId = 2103)
        println("Fetch meeting attends $result")
    }
}
