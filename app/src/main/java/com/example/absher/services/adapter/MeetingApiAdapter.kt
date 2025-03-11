package com.example.absher.services.adapter// adapter/MeetingApiAdapter.kt
import android.util.Log
import com.example.absher.services.data.models.MeetingRequestBody
import com.example.absher.services.data.models.MeetingResponse
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeetingApiAdapter @Inject constructor(
    authInterceptor: AuthInterceptor
) {
    private val retrofit: Retrofit
    val meetingApiService: MeetingApiService

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://mmsksa.d-intalio.com/MMS_Api/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        meetingApiService = retrofit.create(MeetingApiService::class.java)
    }
}