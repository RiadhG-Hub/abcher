// Path: data/models/MeetingsResponse.kt
package com.example.absher.services.data.models



data class MeetingsResponse(
    val data: MeetingsData,
    val success: Boolean,
    val message: String?
)

data class MeetingsData(
    val data: List<Meeting>,
    val total: Int
)