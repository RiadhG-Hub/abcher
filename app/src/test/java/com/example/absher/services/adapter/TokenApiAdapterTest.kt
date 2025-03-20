package com.example.absher.services.adapter

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TokenApiAdapterTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var tokenApiAdapter: TokenApiAdapter

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `testToken should return real TokenResponse`() = runBlocking {
        // Prepare mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "data": {
                        "token": "test_access_token",
                        "refreshToken": "test_refresh_token",
                        "user": {
                            "id": 1,
                            "fullnameAr": "اسم المستخدم",
                            "fullnameEn": "User Name",
                            "language": "ar",
                            "email": "user@example.com",
                            "mobile": "1234567890",
                            "nationalId": "123456789",
                            "hasProfilePicture": true
                        }
                    },
                    "success": true,
                    "message": "Success"
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)

        // Make the actual request
        val result = tokenApiAdapter.testToken()

        // Verify the response
        assert(result != null)
        assert(result?.success == true)
        assert(result?.data?.token == "test_access_token")
        assert(result?.data?.refreshToken == "test_refresh_token")
        assert(result?.data?.user?.fullnameEn == "User Name")
    }

    @Test
    fun `testToken should handle 401 error`() = runBlocking {
        // Prepare mock response for 401 error
        val mockResponse = MockResponse()
            .setResponseCode(401)
            .setBody("""
                {
                    "success": false,
                    "message": "Unauthorized"
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)

        // Make the actual request
        val result = tokenApiAdapter.testToken()

        // Verify the response
        assert(result == null)
    }
}
