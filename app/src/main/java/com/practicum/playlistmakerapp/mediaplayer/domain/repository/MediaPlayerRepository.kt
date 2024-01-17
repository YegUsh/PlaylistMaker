package com.practicum.playlistmakerapp.mediaplayer.domain.repository

import com.practicum.playlistmakerapp.mediaplayer.domain.models.PlayerState

interface MediaPlayerRepository{
    fun preparePlayer(trackUrl: String)
    fun startPlayer()
    fun pausedPlayer()
    fun stopPlayer()
    fun getCurrentPosition(): Int
    var playerState: PlayerState

}