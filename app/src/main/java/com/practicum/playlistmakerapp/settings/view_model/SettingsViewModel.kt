package com.practicum.playlistmakerapp.settings.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmakerapp.app.App
import com.practicum.playlistmakerapp.app.THEME_KEY
import com.practicum.playlistmakerapp.creator.Creator
import com.practicum.playlistmakerapp.settings.domain.api.SettingsInteractor

class SettingsViewModel(
    val settingsInteractor: SettingsInteractor,
    private val app: Application
) : AndroidViewModel(app) {
    val switcherStatusLiveData = MutableLiveData<Boolean>()
    fun getSwitcherStatusLiveData(): LiveData<Boolean> = switcherStatusLiveData

    init {
        switcherStatusLiveData.value = settingsInteractor.getTheme(THEME_KEY)
    }

    fun onSwitcherClicked(status: Boolean) {
        settingsInteractor.saveTheme(THEME_KEY, status)
        switcherStatusLiveData.value = status
        (app as App).switchTheme(status)
    }

    companion object {

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App
                SettingsViewModel(settingsInteractor = Creator.provideSettingInteractor(), app = application)
            }
        }
    }
}