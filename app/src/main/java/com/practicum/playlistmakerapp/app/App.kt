package com.practicum.playlistmakerapp.app

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmakerapp.di.dataModule
import com.practicum.playlistmakerapp.di.interactorModule
import com.practicum.playlistmakerapp.di.repositoryModule
import com.practicum.playlistmakerapp.di.viewModelModule
import com.practicum.playlistmakerapp.settings.domain.api.SettingsRepository
import com.practicum.playlistmakerapp.util.THEME_KEY
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


class App : Application() {
    var darkTheme = false
    lateinit var sharedPreferences: SharedPreferences
    private set

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
        switchTheme(getKoin().get<SettingsRepository>().getTheme(THEME_KEY))
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