package com.practicum.playlistmakerapp.di

import com.practicum.playlistmakerapp.main.view_model.MainViewModel
import com.practicum.playlistmakerapp.mediaplayer.ui.view_model.MediaPlayerViewModel
import com.practicum.playlistmakerapp.search.ui.view_model.SearchViewModel
import com.practicum.playlistmakerapp.settings.view_model.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MediaPlayerViewModel(get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        SettingsViewModel(get(), androidApplication())
    }

    viewModel {
        MainViewModel()
    }
}