package com.example.absher.services.adapter

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecommendationApiAdapterTest {
    private lateinit var recommendationApiAdapter: RecommendationApiAdapter

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(RecommendationApiService::class.java)
        recommendationApiAdapter = RecommendationApiAdapter(okHttpClient = OkHttpClient()) // Use real interceptor if required
    }

    @Test
    fun `fetchRecommendations should return real MeetingResponse`() = runBlocking {
        val result = recommendationApiAdapter.fetchRecommendations(1, 10)



        println("fetch recommendations: $result")
    }


    @Test
    fun `fetchRecommendationInfo should return real MeetingResponse`() = runBlocking {
        val result = recommendationApiAdapter.fetchRecommendationInfo(recommendationId = 5059)



        println("fetch fetch recommendation info: $result")
    }



}
