// Path: adapter/AuthInterceptor.kt
package com.example.absher.services.adapter

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    companion object {
        private const val AUTH_HEADER = "Authorization"
        private const val BEARER = "Bearer "
        private const val UNAUTHORIZED = 401
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${tokenManager.getToken()}")
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()

        val response = chain.proceed(request)

        // Handle token refresh if needed
        if (response.code == UNAUTHORIZED) {
            // Token expired or invalid
            // You can implement token refresh logic here
            tokenManager.clearToken()
            // You might want to trigger a re-authentication flow here
        }

        return response
    }

    private fun Request.Builder.addAuthHeader(token: String?): Request.Builder {
        return if (token != null) {
            header(AUTH_HEADER, BEARER + token)
        } else {
            removeHeader(AUTH_HEADER)
        }
    }

    fun refreshToken(): String? {
        try {
            val refreshResponse = tokenManager.refreshTokenSync()
            return refreshResponse?.data!!.token
        } catch (e: Exception) {
            return null
        }
    }

    fun getToken(): String? {
        return tokenManager.getToken()
    }
}