// Path: data/models/RefreshTokenResponse.kt
package com.example.absher.services.data.models

data class RefreshTokenResponse(
    val accessToken: String,
    val expiresIn: Long
)