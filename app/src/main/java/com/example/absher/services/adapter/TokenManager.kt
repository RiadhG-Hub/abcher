// Path: adapter/TokenManager.kt
package com.example.absher.services.adapter

import android.content.SharedPreferences
import com.example.absher.services.data.models.RefreshTokenResponse
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit() {
            putString(KEY_ACCESS_TOKEN, token)
        }
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit() {
            remove(KEY_ACCESS_TOKEN)
        }
    }

    fun refreshTokenSync(): RefreshTokenResponse? {
        return  null
    }
}