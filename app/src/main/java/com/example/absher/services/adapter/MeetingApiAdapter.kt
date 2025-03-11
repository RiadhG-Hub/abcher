package com.example.absher.services.adapter// adapter/MeetingApiAdapter.kt
import com.example.absher.services.data.models.MeetingResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

class MeetingApiAdapter {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MeetingApiService::class.java)

    suspend fun fetchMeetings(): MeetingResponse {
        val requestBody = mapOf(
            "statusId" to null,
            "committeeId" to null,
            "toDate" to null,
            "fromDate" to null,
            "onlyMyMeetings" to true,
            "title" to null,
            "location" to null
        )
        val tokenCore = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50IjoiZXhLcEpGT2FCOExGcFo4Mkpsc2U5RmZnS0JMTTBPdzgreXdmTVJoV0gzVT0iLCJkZXBhcnRtZW50IjoidDIybllLRzlVRFBDWWhaSnJIeFRVcGIzVVJ5NVVkcDRlT3FKM012UDczVT0iLCJuYW1lIjoiRHFmZXVOS1VxVWdFNndDdHZrd1FvR0ViejdaYUh2V094bDBpd1JwMTFXcVEvSG1hV1ZTM3hQUkZlaVFka2FNZyIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMSIsImxhbmciOiJhciIsImV4cCI6MTc0MTY5NjAzNywiaXNzIjoiSW50YWxpbyIsImF1ZCI6IkludGFsaW8ifQ.zczFTa5d8oHRUYjB3UQApfSz3uso_XnWOP2ywKaaPN8";
        val token = "Bearer $tokenCore"
        return apiService.getMeetings(token, requestBody)
    }
}

interface MeetingApiService {
    @POST("api/meetings/search/1/10")
    suspend fun getMeetings(
        @Header("Authorization") token: String,
        @Body requestBody: Map<String, Any?>
    ): MeetingResponse
}

