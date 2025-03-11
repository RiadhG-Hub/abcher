// Path: adapter/MeetingApiService.kt
package com.example.absher.services.adapter

import com.example.absher.services.data.models.MeetingRequestBody
import com.example.absher.services.data.models.MeetingsResponse

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface MeetingApiService {
    @POST("meetings/search/{page}/{pageSize}")
    suspend fun getMeetings(
        @Path("page") page: Int,
        @Path("pageSize") pageSize: Int,
        @Body requestBody: MeetingRequestBody
    ): MeetingsResponse
}