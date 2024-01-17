package com.practicum.playlistmakerapp.main.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmakerapp.main.SingleLiveEvent
import com.practicum.playlistmakerapp.main.model.MainNavigation

class MainViewModel : ViewModel() {

    val mainStatusLiveData = SingleLiveEvent<MainNavigation>()
    fun getMainStatusLiveData(): LiveData<MainNavigation> = mainStatusLiveData
    fun startSearchActivity() {
        mainStatusLiveData.value = MainNavigation.SEARCH
    }

    fun startMusicLiblaryActivity() {
        mainStatusLiveData.value = MainNavigation.MUSIC_LIBLARY
    }

    fun startSettingsActivity() {
        mainStatusLiveData.value = MainNavigation.SETTINGS
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel()
            }
        }
    }
}