package com.example.absher.services.adapter

import com.example.absher.services.data.models.MeetingRequestBody
import com.example.absher.services.data.models.MeetingResponse
import com.example.absher.services.data.models.RefreshTokenResponse
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class TokenApiAdapter {
    // TODO add token refresh logic using authInterceptor
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(TokenApiService::class.java)


    suspend fun testToken(): RefreshTokenResponse? {
        return try {
            val result = apiService.testToken();
            println("Token test successful: $result");
            println("Message: ${result.message}");
            result;
        } catch (e: HttpException) {
            if (e.code() == 401) {
                System.err.println("[ERROR] 401 Unauthorized: Invalid or expired token");
            } else {
                System.err.println("[ERROR] HTTP ${e.code()}: ${e.message()}");
            }
            null;
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected: ${e.message}");
            null;
        }
    }
}

interface TokenApiService {
    @GET("api/absher-services/test-token")
    suspend fun testToken(



    ): RefreshTokenResponse
}