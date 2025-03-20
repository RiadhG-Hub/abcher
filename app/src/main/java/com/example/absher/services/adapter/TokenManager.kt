// Path: adapter/TokenManager.kt
package com.example.absher.services.adapter

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.absher.services.data.models.meetings.RefreshTokenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    fun saveToken(accessToken: String, refreshToken: String? = null) {
        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .apply()
        
        refreshToken?.let {
            sharedPreferences.edit()
                .putString(KEY_REFRESH_TOKEN, it)
                .apply()
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit()
            .remove(KEY_ACCESS_TOKEN)
            .remove(KEY_REFRESH_TOKEN)
            .apply()
    }

    fun hasToken(): Boolean {
        return !getToken().isNullOrEmpty()
    }

    fun refreshTokenSync(): RefreshTokenResponse? {
        return null
    }
}