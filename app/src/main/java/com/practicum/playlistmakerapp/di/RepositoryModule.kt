package com.practicum.playlistmakerapp.di

import com.practicum.playlistmakerapp.mediaplayer.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmakerapp.mediaplayer.domain.repository.MediaPlayerRepository
import com.practicum.playlistmakerapp.search.domain.api.SearchRepository
import com.practicum.playlistmakerapp.search.data.repository.SearchRepositoryImpl
import com.practicum.playlistmakerapp.settings.data.SettingsRepositoryImpl
import com.practicum.playlistmakerapp.settings.domain.api.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {
    single<MediaPlayerRepository> {
        MediaPlayerRepositoryImpl()
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
}