package com.practicum.playlistmakerapp.domain.interactors

import com.practicum.playlistmakerapp.domain.models.PlayerState

interface MediaPlayerInteractor {
    fun pausePlayer()
    fun start()
    fun release()
    fun prepare()
    fun getPlayerState(): PlayerState
    fun getCurrentPosition(): Int
}