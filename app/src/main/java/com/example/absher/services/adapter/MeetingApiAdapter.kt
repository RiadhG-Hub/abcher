package com.example.absher.services.adapter// adapter/MeetingApiAdapter.kt
import android.util.Log
import com.example.absher.services.data.models.MeetingRequestBody
import com.example.absher.services.data.models.MeetingResponse
import retrofit2.HttpException
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

    suspend fun fetchMeetings(): MeetingResponse? {
        val requestBody = MeetingRequestBody()
        val tokenCore = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50IjoieVo2REdGektnT2pHMzIzU1c4anAwS3BCVzk0M3ZlRnVReHYzZTJsSnA4RT0iLCJkZXBhcnRtZW50IjoiZ0RiTzdvN3hIS1luRjdaZWFIV25RbE9ZR082VDF5Q3YwUEV0M0tGWTE5QT0iLCJuYW1lIjoiTXRUSkxvWW04NGlrb2NBV2crclNQd2xXNE1NQTZEV2MzSTgvNHVORGFlUXNuV1Qya2QvTlIrTGhrRU01TDQvdyIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMSIsImxhbmciOiJhciIsImV4cCI6MTc0MTcwMjI5NywiaXNzIjoiSW50YWxpbyIsImF1ZCI6IkludGFsaW8ifQ.ta8cgkm0-oc3jaSkXNSmP-H4h46b1VH0ozsqiiIsxiA";
        val token = "Bearer $tokenCore"

        return try {
            val result = apiService.getMeetings(token, requestBody)
            Log.e("fetchMeetings", "Success: ${result.toString()}")
            result
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Log.e("fetchMeetings", "Error 401: Unauthorized - Invalid or expired token")
            } else {
                Log.e("fetchMeetings", "HTTP error: ${e.code()} - ${e.message()}")
            }
            null
        } catch (e: Exception) {
            Log.e("fetchMeetings", "Unexpected error: ${e.message}")
            null
        }
    }}

interface MeetingApiService {
    @POST("api/meetings/search/1/10")
    suspend fun getMeetings(
        @Header("Authorization") token: String,
        @Body requestBody: MeetingRequestBody
    ): MeetingResponse
}

