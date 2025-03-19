package com.example.absher.services.adapter

import retrofit2.HttpException
class HttpExceptionHandler(private val tokenApiAdapter: TokenApiAdapter) {

    companion object {
        @Volatile
        var tokenCore: String? = null
    }

    suspend fun <T> handleHttpException(
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