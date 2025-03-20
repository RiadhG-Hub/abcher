package com.example.absher.services.adapter// adapter/MeetingApiAdapter.kt
import com.example.absher.services.data.models.meetings.AttendeeResponse
import com.example.absher.services.data.models.meetings.MeetingAgendaResponse
import com.example.absher.services.data.models.meetings.MeetingAttachment
import com.example.absher.services.data.models.meetings.MeetingAttachmentData
import com.example.absher.services.data.models.meetings.MeetingAttachmentResponse
import com.example.absher.services.data.models.meetings.MeetingInfoResponse
import com.example.absher.services.data.models.meetings.MeetingRequestBody
import com.example.absher.services.data.models.meetings.MeetingResponse
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeetingApiAdapter @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MeetingApiService::class.java)

    suspend fun fetchMeetings(
        from: Int = 1,
        to: Int = 10
    ): MeetingResponse? {
        val requestBody = MeetingRequestBody()

        return try {
            val result = apiService.getMeetings(from.toString(), to.toString(), requestBody)
            println("[INFO] Fetch meetings successful: $result")
            println("[INFO] Message: ${result.message}")
            result
        } catch (e: HttpException) {
            println("[ERROR] HTTP ${e.code()} in fetchMeetings: ${e.message()}")
            null
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun fetchMeetingAttendees(meetingId: Int): AttendeeResponse? {
        return try {
            val result = apiService.fetchMeetingAttendees(meetingId = meetingId)
            println("[INFO] Fetch meetings successful: $result")
            result
        } catch (e: HttpException) {
            println("[ERROR] HTTP ${e.code()} in fetchMeetingAttendees: ${e.message()}")
            null
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun fetchMeetingAgendas(meetingId: Int): MeetingAgendaResponse? {
        return try {
            val result = apiService.fetchMeetingAgendas(meetingId)
            println("[INFO] Fetch meeting agendas successful: $result")
            result
        } catch (e: HttpException) {
            println("[ERROR] HTTP ${e.code()} in fetchMeetingAgendas: ${e.message()}")
            null
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun fetchMeetingInfo(meetingId: Int): MeetingInfoResponse? {
        return try {
            val result = apiService.getMeetingInfo(meetingId)
            println("[INFO] Fetch meeting info successful: $result")
            result
        } catch (e: HttpException) {
            println("[ERROR] HTTP ${e.code()} in fetchMeetingInfo: ${e.message()}")
            null
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun fetchMeetingAttachments(meetingId: Int): MeetingAttachmentResponse? {
        val mockMeetingAttachmentResponse = MeetingAttachmentResponse(
            data = MeetingAttachmentData(
                meetingAttachments = listOf(
                    MeetingAttachment(
                        userId = 101,
                        needsApproval = false,
                        jobTitle = "Software Engineer",
                        name = "John Doe",
                        attended = true,
                        hasProfilePicture = true
                    ),
                    MeetingAttachment(
                        userId = 102,
                        needsApproval = true,
                        jobTitle = "Project Manager",
                        name = "Jane Smith",
                        attended = false,
                        hasProfilePicture = false
                    ),
                    MeetingAttachment(
                        userId = 103,
                        needsApproval = false,
                        jobTitle = "QA Analyst",
                        name = "Michael Johnson",
                        attended = true,
                        hasProfilePicture = true
                    )
                )
            ),
            success = true,
            message = "Data retrieved successfully"
        )

        return mockMeetingAttachmentResponse
    }
}

interface MeetingApiService {
    @POST("api/meetings/search/{from}/{to}")
    suspend fun getMeetings(
        @Path("from") from: String,
        @Path("to") to: String,
        @Body requestBody: MeetingRequestBody
    ): MeetingResponse

    @GET("api/meetings/{meetingId}/attendees")
    suspend fun fetchMeetingAttendees(
        @Path("meetingId") meetingId: Int
    ): AttendeeResponse?

    @GET("api/meetings/{meetingId}/agendas")
    suspend fun fetchMeetingAgendas(
        @Path("meetingId") meetingId: Int
    ): MeetingAgendaResponse?

    @GET("api/meetings/{meetingId}/meeting-info")
    suspend fun getMeetingInfo(
        @Path("meetingId") meetingId: Int
    ): MeetingInfoResponse?
}


