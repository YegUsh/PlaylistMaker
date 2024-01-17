package com.practicum.playlistmakerapp.settings.domain.api

interface SettingsRepository {
    fun putSharedTheme(key: String, status: Boolean)
    fun getTheme(key: String): Boolean

}