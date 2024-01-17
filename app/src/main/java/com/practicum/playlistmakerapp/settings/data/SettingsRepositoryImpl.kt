package com.practicum.playlistmakerapp.settings.data

import android.content.SharedPreferences
import com.practicum.playlistmakerapp.settings.domain.api.SettingsRepository

class SettingsRepositoryImpl (val sharedPreferences: SharedPreferences): SettingsRepository {


    override fun putSharedTheme(key: String, status: Boolean) {
        sharedPreferences.edit()
            .putBoolean(key, status).apply()
    }

    override fun getTheme(key: String): Boolean {
        return    sharedPreferences.getBoolean(
            key,
            false)
    }
}