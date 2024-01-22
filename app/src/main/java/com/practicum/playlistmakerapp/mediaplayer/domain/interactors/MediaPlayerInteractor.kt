package com.practicum.playlistmakerapp.mediaplayer.domain.interactors

import com.practicum.playlistmakerapp.mediaplayer.domain.models.PlayerState

interface MediaPlayerInteractor {
    fun pausePlayer()
    fun start(trackUrl: String)
    fun release()
    fun prepare(trackUrl: String)
    fun getPlayerState(): PlayerState
    fun getCurrentPosition(): String
}