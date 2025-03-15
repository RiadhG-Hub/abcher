package com.example.absher.services.adapter// adapter/MeetingApiAdapter.kt
import com.example.absher.services.data.models.AttendeeResponse
import com.example.absher.services.data.models.MeetingRequestBody
import com.example.absher.services.data.models.MeetingResponse
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

class MeetingApiAdapter {
    private var tokenCore = "azs"
    private val tokenApiAdapter = TokenApiAdapter()

    // TODO: Add token refresh logic using authInterceptor
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MeetingApiService::class.java)

    suspend fun fetchMeetings(from: Int = 1, to: Int = 10, token: String = "Bearer $tokenCore"): MeetingResponse? {
        val requestBody = MeetingRequestBody()

        return try {
            val result = apiService.getMeetings(token, from.toString(), to.toString(), requestBody)
            println("[INFO] Fetch meetings successful: $result")
            println("[INFO] Message: ${result.message}")
            result
        } catch (e: HttpException) {
            if (e.code() == 401) {
                println("[WARNING] 401 Unauthorized: Token expired, attempting refresh...")
                val tokenResult = tokenApiAdapter.testToken()
                tokenResult?.data?.token?.let {
                    tokenCore = it
                    println("[INFO] Token refreshed successfully.")
                    return fetchMeetings(from, to, "Bearer $tokenCore")
                } ?: run {
                    println("[ERROR] Token refresh failed.")
                    null
                }
            } else {
                System.err.println("[ERROR] HTTP ${e.code()}: ${e.message()}")
                null
            }
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }


suspend fun fetchMeetingAttendees(meetingId: Int, token: String = "Bearer $tokenCore"):AttendeeResponse ? {


    return try {
        val result = apiService.fetchMeetingAttendees(
            meetingId = meetingId,
            token = token
        )
        println("[INFO] Fetch meetings successful: $result")

        result
    } catch (e: HttpException) {
        if (e.code() == 401) {
            println("[WARNING] 401 Unauthorized: Token expired, attempting refresh...")
            val tokenResult = tokenApiAdapter.testToken()
            tokenResult?.data?.token?.let {
                tokenCore = it
                println("[INFO] Token refreshed successfully.")
                return fetchMeetingAttendees(meetingId, "Bearer $tokenCore")
            } ?: run {
                println("[ERROR] Token refresh failed.")
                null
            }
        } else {
            System.err.println("[ERROR] HTTP ${e.code()}: ${e.message()}")
            null
        }
    } catch (e: Exception) {
        System.err.println("[ERROR] Unexpected error: ${e.message}")
        null
    }
}
}


interface MeetingApiService {
    @POST("api/meetings/search/{from}/{to}")
    suspend fun getMeetings(
        @Header("Authorization") token: String,
        @Path("from") from: String,
        @Path("to") to: String,
        @Body requestBody: MeetingRequestBody
    ): MeetingResponse



    @GET("api/meetings/{meetingId}/attendees")
    suspend fun fetchMeetingAttendees(
        @Path("meetingId") meetingId: Int,
        @Header("Authorization") token: String
    ): AttendeeResponse?
}
