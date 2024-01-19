package com.practicum.playlistmakerapp.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
const val THEME_KEY = "theme_key"

class App : Application() {
    var darkTheme = false
    lateinit var sharedPreferences: SharedPreferences
    private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
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

    companion object {
        private const val APP_PREFERENCES = "app_preferences"
        lateinit var instance: App
            private set
    }

}