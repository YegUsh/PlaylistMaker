package com.practicum.playlistmakerapp.main

sealed class PlayStatus {
    object OnStart : PlayStatus()
    object OnPause : PlayStatus()
}