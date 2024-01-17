package com.practicum.playlistmakerapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
const val THEME_KEY = "theme_key"

class App : Application() {
    var darkTheme = false

    override fun onCreate() {
        super.onCreate()
        val settingsTheme = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
        switchTheme(settingsTheme.getBoolean(THEME_KEY, false))
    }

    fun switchTheme(darkThemeEnabled: Boolean) {

        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}