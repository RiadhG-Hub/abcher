package com.example.absher.services.adapter

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenApiAdapterTest {
    private lateinit var tokenApiAdapter: TokenApiAdapter

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MeetingApiService::class.java)
        tokenApiAdapter = TokenApiAdapter() // Use real interceptor if required
    }

    @Test
    fun `fetchMeetings should return real MeetingResponse`() = runBlocking {
        val result = tokenApiAdapter.testToken()



        println("Fetched Meetings: $result")
    }
}
