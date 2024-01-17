package com.practicum.playlistmakerapp.settings.domain.api

interface SettingsInteractor {
    fun saveTheme(key: String, status: Boolean)
    fun getTheme(key: String): Boolean
}