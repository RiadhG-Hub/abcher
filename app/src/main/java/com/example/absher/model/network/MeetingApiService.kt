package com.example.absher.model.network// MeetingApiService.kt
import com.example.absher.model.MeetingResponse
import com.example.absher.model.MeetingSearchRequest
import retrofit2.http.Body

import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetingApiService {
    @POST("api/meetings/search/{page}/{pageSize}")
    suspend fun searchMeetings(
        @Path("page") page: Int,
        @Path("pageSize") pageSize: Int,
        @Header("Authorization") authToken: String,
        @Body request: MeetingSearchRequest
    ): MeetingResponse
}