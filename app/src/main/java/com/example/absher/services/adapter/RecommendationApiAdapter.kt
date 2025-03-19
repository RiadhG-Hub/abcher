package com.example.absher.services.adapter

import com.example.absher.services.data.models.meetings.MeetingRequestBody
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoResponse
import com.example.absher.services.data.models.recommendations.RecommendationResponse
import com.example.absher.services.data.models.recommendations.RecommendationStatusResponse
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

class RecommendationApiAdapter(private val httpExceptionHandler: HttpExceptionHandler = HttpExceptionHandler(
    tokenApiAdapter = TokenApiAdapter()
)){

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(RecommendationApiService::class.java)

    suspend fun fetchRecommendations(
        from: Int = 1,
        to: Int = 10,
        requestBody: MeetingRequestBody = MeetingRequestBody(
            meetingReferenceNo = null,
            fromDate = null,
            toDate = null,
            title = null
        ),
        token: String = "Bearer ${HttpExceptionHandler.tokenCore}"
    ): RecommendationResponse? {
        println("request body from adapter")
        println(requestBody)

        return try {
            val result = apiService.getRecommendations(token, from.toString(), to.toString(), requestBody)
            println("[INFO] Fetch recommendations successful: $result")
            result
        } catch (e: HttpException) {
            if(e.code()==401){
                httpExceptionHandler.handleHttpException(e, "fetchRecommendations") { newToken ->
                    fetchRecommendations(from, to, requestBody, newToken)
                }

            } else {
                println("[ERROR] HTTP ${e.code()} in fetchRecommendationInfo: ${e.message()}")
                null
            }
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun fetchRecommendationInfo(
        recommendationId: Int = 2042,
        token: String = "Bearer ${HttpExceptionHandler.tokenCore}"
    ): FetchRecommendationInfoResponse? {
        return try {
            val result = apiService.getRecommendationInfo(recommendationId, token)
            println("[INFO] Fetch recommendation info successful: $result")
            result
        } catch (e: HttpException) {
            if(e.code()==401){
                httpExceptionHandler.handleHttpException(e, "fetchRecommendationInfo") { newToken ->
                    fetchRecommendationInfo(recommendationId, newToken)
            }

            } else {
                println("[ERROR] HTTP ${e.code()} in fetchRecommendationInfo: ${e.message()}")
                null
            }
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun fetchRecommendationStatuses(
        token: String = "Bearer ${HttpExceptionHandler.tokenCore}"
    ): RecommendationStatusResponse? {
        return try {
            val result = apiService.getRecommendationStatuses(token)
            println("[INFO] Fetch recommendation statuses successful: $result")
            result
        } catch (e: HttpException) {
            if(e.code()==401){
                httpExceptionHandler.handleHttpException(e, "fetchRecommendationInfo") { newToken ->
                    fetchRecommendationStatuses( newToken)
                }

            } else {
                println("[ERROR] HTTP ${e.code()} in fetchRecommendationInfo: ${e.message()}")
                null
            }
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }
}



interface RecommendationApiService {
    @POST("meetingAgendaRecommendations/search/{from}/{to}")
    suspend fun getRecommendations(
        @Header("Authorization") token: String,
        @Path("from") from: String,
        @Path("to") to: String,
        @Body requestBody: MeetingRequestBody
    ): RecommendationResponse

    @GET("meetingAgendaRecommendations/{recommendationId}")
    suspend fun getRecommendationInfo(
        @Path("recommendationId") recommendationId: Int,
        @Header("Authorization") token: String
    ): FetchRecommendationInfoResponse

    @GET("lookups/meeting-recommendations-statuses")
    suspend fun getRecommendationStatuses(
        @Header("Authorization") token: String
    ): RecommendationStatusResponse
}
