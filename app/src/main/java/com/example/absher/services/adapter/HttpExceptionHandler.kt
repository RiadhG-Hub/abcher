package com.example.absher.services.adapter

import retrofit2.HttpException
class HttpExceptionHandler(private val tokenApiAdapter: TokenApiAdapter) {

    companion object {
        @Volatile
        var tokenCore: String? = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50IjoiT3VudldLUU9tY1NpMmY0Q1NMbS9HWU5ZUWV3bGx5ZzQxbFdLYVhGbTlXQT0iLCJkZXBhcnRtZW50IjoiVkRvUDR4cWs1QlE5b1VTempRT29aTUlBdjh2ck12NU1DSXVJSTZsNzRZbz0iLCJuYW1lIjoiS0U3Nms3MzBWRFJIcE1YRnRhanN2dFdGcHh4Z3gyeVNZSjJkcTJoM3IrVW5vcStKTUpIUktqMDZlT1R1b3oydyIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMSIsImxhbmciOiJhciIsImV4cCI6MTc0MjQ5Nzg5MywiaXNzIjoiSW50YWxpbyIsImF1ZCI6IkludGFsaW8ifQ.u4u6UicsMstcfYadwv5K970EJuYQUh-3qEG9BugcCpc"
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