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
import retrofit2.http.Path

class MeetingApiAdapter(authInterceptor: AuthInterceptor) {
    // TODO add token refresh logic using authInterceptor
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MeetingApiService::class.java)

    suspend fun fetchMeetings(from : Int = 0,    to : Int = 10): MeetingResponse? {
        val requestBody = MeetingRequestBody()
        val tokenCore = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50IjoiekZjMUFVMUZ6QXBuSjZpVUxkaGxibUFoeXlNMUtkdmV3YW81RU14TmZSND0iLCJkZXBhcnRtZW50IjoiUHZ5VEtPOGJDWmdpWUU5NFQxM0JFaHQ0UjhQcThsMWp5bW5GY3ExMHVLMD0iLCJuYW1lIjoidHBBZXRNMHNjZHJ4eG9obE1BNTRidFFjY1Q0bGZWZzJ1TnNnNGZiMWxrQlFzNVkyK2pIV2Q3UTdsMC93YjFxSyIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMSIsImxhbmciOiJhciIsImV4cCI6MTc0MTc0MTYyMiwiaXNzIjoiSW50YWxpbyIsImF1ZCI6IkludGFsaW8ifQ.ZJspSG8nkS1nEJFeAt0ywH0CwT-925FZJP7Nxdg7EB0"
        val token = "Bearer $tokenCore"

        return try {
            val result = apiService.getMeetings(token, from = from.toString()  , to = to.toString(), requestBody)
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
    @POST("api/meetings/search/{from}/{to}")
    suspend fun getMeetings(
        @Header("Authorization") token: String,
        @Path("from") from: String,
        @Path("to") to: String,
        @Body requestBody: MeetingRequestBody
    ): MeetingResponse
}

