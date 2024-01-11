package com.practicum.playlistmakerapp.ui

sealed class PlayStatus {
    object OnStart : PlayStatus()
    object OnPause : PlayStatus()
}