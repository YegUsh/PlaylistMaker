package com.practicum.playlistmakerapp.di

import com.practicum.playlistmakerapp.mediaplayer.domain.interactors.MediaPlayerInteractor
import com.practicum.playlistmakerapp.mediaplayer.domain.interactors.MediaPlayerInteractorImpl
import com.practicum.playlistmakerapp.search.domain.api.SearchInteractor
import com.practicum.playlistmakerapp.search.domain.impl.SearchInteractorImpl
import com.practicum.playlistmakerapp.settings.domain.api.SettingsInteractor
import com.practicum.playlistmakerapp.settings.domain.impl.SettingsInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<MediaPlayerInteractor> {
        MediaPlayerInteractorImpl(get())
    }
    single<SearchInteractor>{
        SearchInteractorImpl(get())
    }

    single<SettingsInteractor>{
        SettingsInteractorImpl(get())
    }
}