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
        val originalRequest = chain.request()
        
        val token = tokenManager.getAccessToken()
        val authenticatedRequest = originalRequest.newBuilder()
            .addAuthHeader(token)
            .build()

        val response = chain.proceed(authenticatedRequest)

        if (response.code == UNAUTHORIZED) {
            synchronized(this) {
                val currentToken = tokenManager.getAccessToken()
                if (currentToken != token) {
                    return chain.proceed(originalRequest.newBuilder()
                        .addAuthHeader(currentToken)
                        .build())
                }

                val newToken = refreshToken()
                if (newToken != null) {
                    tokenManager.saveAccessToken(newToken)
                    return chain.proceed(originalRequest.newBuilder()
                        .addAuthHeader(newToken)
                        .build())
                }
            }
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

    private fun refreshToken(): String? {
        try {
            val refreshResponse = tokenManager.refreshTokenSync()
            return refreshResponse?.accessToken
        } catch (e: Exception) {
            return null
        }
    }
}