package com.example.absher.services.adapter// adapter/MeetingApiAdapter.kt
import com.example.absher.services.data.models.meetings.AttendeeResponse
import com.example.absher.services.data.models.meetings.Meeting
import com.example.absher.services.data.models.meetings.MeetingAgendaResponse
import com.example.absher.services.data.models.meetings.MeetingAttachment
import com.example.absher.services.data.models.meetings.MeetingAttachmentData
import com.example.absher.services.data.models.meetings.MeetingAttachmentResponse
import com.example.absher.services.data.models.meetings.MeetingInfoResponse
import com.example.absher.services.data.models.meetings.MeetingRequestBody
import com.example.absher.services.data.models.meetings.MeetingResponse
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

class MeetingApiAdapter {
    private var tokenCore =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50Ijoib1NFY04rb0JsZTRsZ3BQWm1ZZ1F0eW9iaHV6YXdyS2VPaWZMZW1qYkFxVT0iLCJkZXBhcnRtZW50IjoiM0hZUkRmTVhmTUFKZWluYkZCUVZrSWc5K1ZzTFluY0E3MmFGQ012NUlNaz0iLCJuYW1lIjoiclZIRkRjQnpwSFN0ckwreUhreTQzd01memxtRDdESGx3T2ZabllWeHdGRT0iLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjMwMjMiLCJsYW5nIjoiYXIiLCJleHAiOjE3NDIxMjQ2MzYsImlzcyI6IkludGFsaW8iLCJhdWQiOiJJbnRhbGlvIn0.33C1wad3j8GCMLZDSTzoM6ZC9Xz88m2cpvYvXIOgVBE"
    private val tokenApiAdapter = TokenApiAdapter()

    // TODO: Add token refresh logic using authInterceptor
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MeetingApiService::class.java)

    suspend fun fetchMeetings(
        from: Int = 1,
        to: Int = 10,
        token: String = "Bearer $tokenCore"
    ): MeetingResponse? {

        // List of mock meetings
        val getMockMeetings: List<Meeting> = listOf(
            Meeting(
                id = 1,
                statusId = 5,
                referenceNumber = "MTG-2025-001",
                title = "Weekly Team Sync",
                date = "2025-03-20T00:00:00",
                startTime = "09:00",
                endTime = "10:00",
                location = "Conference Room A",
                isCommittee = false,
                notes = "Discuss project updates and sprint planning",
                committeeName = "Development Team",
                statusName = "Scheduled"
            ),
            Meeting(
                id = 2,
                statusId = 2,
                referenceNumber = "MTG-2025-002",
                title = "Client Review Meeting",
                date = "2025-03-21T00:00:00",
                startTime = "14:00",
                endTime = "15:30",
                location = "Zoom Call",
                isCommittee = false,
                notes = "Review project deliverables with client",
                committeeName = "Project Stakeholders",
                statusName = "Pending Approval"
            ),
            Meeting(
                id = 3,
                statusId = 7,
                referenceNumber = "MTG-2025-003",
                title = "Board Committee Meeting",
                date = "2025-03-22T00:00:00",
                startTime = "10:00",
                endTime = "12:00",
                location = "Board Room",
                isCommittee = true,
                notes = "Quarterly performance review",
                committeeName = "Executive Committee",
                statusName = "Approved"
            )
        )
        return MeetingResponse(
            data = MeetingResponse.MeetingData(
                getMockMeetings,
                getMockMeetings.size
            ), success = true, message = "Success"
        )
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


    suspend fun fetchMeetingAttendees(
        meetingId: Int,
        token: String = "Bearer $tokenCore"
    ): AttendeeResponse? {


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

    suspend fun fetchMeetingAgendas(
        meetingId: Int,
        token: String = "Bearer $tokenCore"
    ): MeetingAgendaResponse? {
        return try {
            val result = apiService.fetchMeetingAgendas(meetingId, token)
            println("[INFO] Fetch meeting agendas successful: $result")
            result
        } catch (e: HttpException) {
            if (e.code() == 401) {
                println("[WARNING] 401 Unauthorized: Token expired, attempting refresh...")
                val tokenResult = tokenApiAdapter.testToken()
                tokenResult?.data?.token?.let {
                    tokenCore = it
                    println("[INFO] Token refreshed successfully.")
                    return fetchMeetingAgendas(meetingId, "Bearer $tokenCore")
                } ?: run {
                    println("[ERROR] Token refresh failed.")
                    null
                }
            } else {
                System.err.println("[ERROR] HTTP ${e.code()}: ${e.message}")
                null
            }
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }


    suspend fun fetchMeetingInfo(
        meetingId: Int,
        token: String = "Bearer $tokenCore"
    ): MeetingInfoResponse? {
        return try {
            val result = apiService.getMeetingInfo(meetingId, token)
            println("[INFO] Fetch meeting info successful: $result")
            result
        } catch (e: HttpException) {
            if (e.code() == 401) {
                println("[WARNING] 401 Unauthorized: Token expired, attempting refresh...")
                val tokenResult = tokenApiAdapter.testToken()
                tokenResult?.data?.token?.let {
                    tokenCore = it
                    println("[INFO] Token refreshed successfully.")
                    return fetchMeetingInfo(meetingId, "Bearer $tokenCore")
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

    suspend fun fetchMeetingAttachments(meetingId: Int): MeetingAttachmentResponse?{
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

    @GET("api/meetings/{meetingId}/agendas")
    suspend fun fetchMeetingAgendas(
        @Path("meetingId") meetingId: Int,
        @Header("Authorization") token: String
    ): MeetingAgendaResponse?


    @GET("api/meetings/{meetingId}/meeting-info")
    suspend fun getMeetingInfo(
        @Path("meetingId") meetingId: Int,
        @Header("Authorization") token: String
    ): MeetingInfoResponse?
}


