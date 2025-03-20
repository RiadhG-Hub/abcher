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
class RecommendationApiAdapterTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var recommendationApiAdapter: RecommendationApiAdapter

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
    fun `fetchRecommendations should return real RecommendationResponse`() = runBlocking {
        // Prepare mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "data": {
                        "data": [
                            {
                                "id": 1,
                                "meetingAgendaId": 1,
                                "text": "Test Recommendation",
                                "statusId": 1,
                                "createdByName": "John Doe",
                                "owner": 1,
                                "ownerName": "Jane Smith",
                                "recommendationTypeId": 1,
                                "recommendationTypeName": "Type 1",
                                "percentage": 50,
                                "status": "Active"
                            }
                        ],
                        "total": 1
                    },
                    "success": true,
                    "message": "Success"
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)

        // Make the actual request
        val result = recommendationApiAdapter.fetchRecommendations(1, 10)

        // Verify the response
        assert(result != null)
        assert(result?.success == true)
        assert(result?.data?.data?.size == 1)
        assert(result?.data?.data?.first()?.text == "Test Recommendation")
    }

    @Test
    fun `fetchRecommendationInfo should return real RecommendationInfo`() = runBlocking {
        // Prepare mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "data": {
                        "id": 5059,
                        "createdBy": 1,
                        "createdAt": "2024-03-20T10:00:00",
                        "dueDate": "2024-03-27T10:00:00",
                        "meetingAgendaId": 1,
                        "text": "Test Recommendation Info",
                        "statusId": 1,
                        "createdByName": "John Doe",
                        "owner": 1,
                        "ownerName": "Jane Smith",
                        "recommendationTypeId": 1,
                        "recommendationTypeName": "Type 1",
                        "percentage": 50,
                        "status": "Active"
                    },
                    "success": true,
                    "message": "Success"
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)

        // Make the actual request
        val result = recommendationApiAdapter.fetchRecommendationInfo(recommendationId = 5059)

        // Verify the response
        assert(result != null)
        assert(result?.success == true)
        assert(result?.data?.id == 5059)
        assert(result?.data?.text == "Test Recommendation Info")
    }

    @Test
    fun `fetchRecommendationStatuses should return real StatusResponse`() = runBlocking {
        // Prepare mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "data": [
                        {
                            "id": 1,
                            "nameAr": "نشط",
                            "nameEn": "Active",
                            "active": true,
                            "displayOrder": 1
                        },
                        {
                            "id": 2,
                            "nameAr": "مكتمل",
                            "nameEn": "Completed",
                            "active": true,
                            "displayOrder": 2
                        }
                    ],
                    "success": true,
                    "message": "Success"
                }
            """.trimIndent())
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)

        // Make the actual request
        val result = recommendationApiAdapter.fetchRecommendationStatuses()

        // Verify the response
        assert(result != null)
        assert(result?.success == true)
        assert(result?.data?.size == 2)
        assert(result?.data?.first()?.name == "Active")
    }
}
