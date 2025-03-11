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

class MeetingApiAdapter(authInterceptor: AuthInterceptor) {
    // TODO add token refresh logic using authInterceptor
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MeetingApiService::class.java)

    suspend fun fetchMeetings(): MeetingResponse? {
        val requestBody = MeetingRequestBody()
        val tokenCore = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50IjoiK1BtNktraTB4aHF5Nkt0by8wUFkvQUV2cFRLcEtJS2xZeDNDOHkvTzBjbz0iLCJkZXBhcnRtZW50IjoiRnp4N1psSjltRXhPQUROWmxoK283VHJaRmZFOEtsNlE1LzEvUzFqVGJOdz0iLCJuYW1lIjoiVTNPakRuZC9BYXh2NDgrazBRM0FDdlhPVTZaTGlxUkNIaVhHNkhJd3VjMW9uK3JxNFFydWwyZVEzQVlneVJ6biIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMSIsImxhbmciOiJhciIsImV4cCI6MTc0MTc0MDcyMSwiaXNzIjoiSW50YWxpbyIsImF1ZCI6IkludGFsaW8ifQ.Hh9HJqji8uFQK7s_byLr3NBEiE_qH93owWeKH7DFV70"
        val token = "Bearer $tokenCore"

        return try {
            val result = apiService.getMeetings(token, requestBody)
            Log.e("fetchMeetings", "Success: $result")
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

