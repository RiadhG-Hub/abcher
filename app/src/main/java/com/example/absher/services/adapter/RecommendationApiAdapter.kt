package com.example.absher.services.adapter// adapter/MeetingApiAdapter.kt
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


class RecommendationApiAdapter {
    private var tokenCore =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50Ijoib1NFY04rb0JsZTRsZ3BQWm1ZZ1F0eW9iaHV6YXdyS2VPaWZMZW1qYkFxVT0iLCJkZXBhcnRtZW50IjoiM0hZUkRmTVhmTUFKZWluYkZCUVZrSWc5K1ZzTFluY0E3MmFGQ012NUlNaz0iLCJuYW1lIjoiclZIRkRjQnpwSFN0ckwreUhreTQzd01memxtRDdESGx3T2ZabllWeHdGRT0iLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjMwMjMiLCJsYW5nIjoiYXIiLCJleHAiOjE3NDIxMjQ2MzYsImlzcyI6IkludGFsaW8iLCJhdWQiOiJJbnRhbGlvIn0.33C1wad3j8GCMLZDSTzoM6ZC9Xz88m2cpvYvXIOgVBE"
    private val tokenApiAdapter = TokenApiAdapter()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(RecommendationApiService::class.java)

    suspend fun fetchRecommendations(
        from: Int = 1,
        to: Int = 10,
         requestBody : MeetingRequestBody = MeetingRequestBody(
             meetingReferenceNo = 22,
             fromDate = null,
             toDate = null,
             title = null
         ),
        token: String = "Bearer $tokenCore"
    ): RecommendationResponse? {
        println("request body from adapter")
        println(requestBody)


        return try {
            val result = apiService.getRecommendations(token, from.toString(), to.toString(), requestBody)
            println("[INFO] Fetch recommendations successful: $result")
            result
        } catch (e: HttpException) {
            handleHttpException(e, "fetchRecommendations") { newToken ->
                fetchRecommendations(from, to, requestBody, newToken)
            }
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun fetchRecommendationInfo(
        recommendationId: Int,
        token: String = "Bearer $tokenCore"
    ): FetchRecommendationInfoResponse? {
        return try {
            val result = apiService.getRecommendationInfo(recommendationId, token)
            println("[INFO] Fetch recommendation info successful: $result")
            result
        } catch (e: HttpException) {
            handleHttpException(e, "fetchRecommendationInfo") { newToken ->
                fetchRecommendationInfo(recommendationId, newToken)
            }
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    suspend fun fetchRecommendationStatuses(
        token: String = "Bearer $tokenCore"
    ): RecommendationStatusResponse? {
        return try {
            val result = apiService.getRecommendationStatuses(token)
            println("[INFO] Fetch recommendation statuses successful: $result")
            result
        } catch (e: HttpException) {
            handleHttpException(e, "fetchRecommendationStatuses") { newToken ->
                fetchRecommendationStatuses(newToken)
            }
        } catch (e: Exception) {
            System.err.println("[ERROR] Unexpected error: ${e.message}")
            null
        }
    }

    private suspend fun <T> handleHttpException(
        e: HttpException,
        methodName: String,
        retryFunction: suspend (String) -> T
    ): T? {
        return if (e.code() == 401) {
            println("[WARNING] 401 Unauthorized in $methodName: Token expired, attempting refresh...")
            val tokenResult = tokenApiAdapter.testToken()
            tokenResult?.data?.token?.let {
                tokenCore = it
                println("[INFO] Token refreshed successfully.")
                retryFunction("Bearer $tokenCore")
            } ?: run {
                println("[ERROR] Token refresh failed.")
                null
            }
        } else {
            System.err.println("[ERROR] HTTP ${e.code()} in $methodName: ${e.message()}")
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

