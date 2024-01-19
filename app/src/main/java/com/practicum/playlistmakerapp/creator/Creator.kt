package com.practicum.playlistmakerapp.creator


import android.content.SharedPreferences
import com.practicum.playlistmakerapp.app.App
import com.practicum.playlistmakerapp.mediaplayer.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmakerapp.mediaplayer.domain.interactors.MediaPlayerInteractor
import com.practicum.playlistmakerapp.mediaplayer.domain.interactors.MediaPlayerInteractorImpl
import com.practicum.playlistmakerapp.search.data.net.RetrofitNetClient
import com.practicum.playlistmakerapp.search.data.repository.SearchRepositoryImpl
import com.practicum.playlistmakerapp.search.domain.api.SearchInteractor
import com.practicum.playlistmakerapp.search.domain.api.SearchRepository
import com.practicum.playlistmakerapp.search.domain.impl.SearchInteractorImpl
import com.practicum.playlistmakerapp.settings.data.SettingsRepositoryImpl
import com.practicum.playlistmakerapp.settings.domain.api.SettingsInteractor
import com.practicum.playlistmakerapp.settings.domain.api.SettingsRepository
import com.practicum.playlistmakerapp.settings.domain.impl.SettingsInteractorImpl


object Creator {
    fun provideMediaPlayerInteractor(): MediaPlayerInteractor {
        return MediaPlayerInteractorImpl(
            MediaPlayerRepositoryImpl()
        )
    }

    fun getSharedPreferences(): SharedPreferences {
        return App.instance.sharedPreferences
    }

    fun getSearchRepository(): SearchRepository {
        return SearchRepositoryImpl(
            sharedPreferences = getSharedPreferences(),
            networkClient = RetrofitNetClient()
        )
    }

    fun provideSearchInteractor(): SearchInteractor {
        return SearchInteractorImpl(searchRepository = getSearchRepository())
    }

    fun getSettingRepository(): SettingsRepository {
        return SettingsRepositoryImpl(sharedPreferences = getSharedPreferences())
    }

    fun provideSettingInteractor(): SettingsInteractor {
        return SettingsInteractorImpl(settingRepository = getSettingRepository())

    }
}