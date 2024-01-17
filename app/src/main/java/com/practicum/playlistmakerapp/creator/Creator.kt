package com.practicum.playlistmakerapp.creator


import com.practicum.playlistmakerapp.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmakerapp.domain.interactors.MediaPlayerInteractor
import com.practicum.playlistmakerapp.domain.interactors.MediaPlayerInteractorImpl


object Creator {
    fun provideMediaPlayerInteractor(): MediaPlayerInteractor {
        return MediaPlayerInteractorImpl(
            MediaPlayerRepositoryImpl()
        )
    }
}