package com.practicum.playlistmakerapp.domain.repository

import com.practicum.playlistmakerapp.domain.models.PlayerState

interface MediaPlayerRepository{
    fun preparePlayer(trackUrl: String)
    fun startPlayer()
    fun pausedPlayer()
    fun stopPlayer()
    fun getCurrentPosition(): Int
    var playerState: PlayerState

}