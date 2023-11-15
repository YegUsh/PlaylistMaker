package com.practicum.playlistmakerapp.creator

import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmakerapp.data.MediaPlayerRepositoryImpl
import com.practicum.playlistmakerapp.domain.interactors.MediaPlayerInteractorImpl
import com.practicum.playlistmakerapp.presentation.MediaPlayerPresenterImpl
import com.practicum.playlistmakerapp.presentation.MediaPlayerView
import com.practicum.playlistmakerapp.presentation.Router

object Creator {
    fun providePresenter(
        mediaPlayerView: MediaPlayerView,
        activity: AppCompatActivity
    ): MediaPlayerPresenterImpl {
        val router = Router(activity)
        return MediaPlayerPresenterImpl(
            mediaPlayerView = mediaPlayerView,
            mediaPlayerInteractor = MediaPlayerInteractorImpl(
                MediaPlayerRepositoryImpl(router.getTrack().previewUrl)
            ),
            router = router
        )
    }
}