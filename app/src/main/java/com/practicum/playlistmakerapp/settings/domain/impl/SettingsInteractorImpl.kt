package com.practicum.playlistmakerapp.settings.domain.impl

import com.practicum.playlistmakerapp.settings.domain.api.SettingsInteractor
import com.practicum.playlistmakerapp.settings.domain.api.SettingsRepository

class SettingsInteractorImpl(val settingRepository: SettingsRepository) : SettingsInteractor {


    override fun saveTheme(key: String, status: Boolean) {
        settingRepository.putSharedTheme(key, status)
    }

    override fun getTheme(key: String): Boolean {
        return settingRepository.getTheme(key)
    }
}