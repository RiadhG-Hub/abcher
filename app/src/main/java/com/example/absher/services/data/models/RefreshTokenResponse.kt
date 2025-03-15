// Path: data/models/RefreshTokenResponse.kt
package com.example.absher.services.data.models

// Data Classes for Token Response


data class RefreshTokenResponse(
    val data: TokenData?,
    val success: Boolean,
    val message: String?
)

data class TokenData(
    val token: String,
    val refreshToken: String,
    val user: UserData
)

data class UserData(
    val id: Int,
    val fullnameAr: String,
    val fullnameEn: String,
    val language: String,
    val email: String,
    val mobile: String?,
    val nationalId: String,
    val hasProfilePicture: Boolean
)
