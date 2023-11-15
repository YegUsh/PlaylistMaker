package com.practicum.playlistmakerapp.presentation

import com.practicum.playlistmakerapp.domain.models.TrackData

interface MediaPlayerView {
    fun getData(track: TrackData)
    fun setPausedImage()
    fun setStartImage()
    fun goBack()
    fun setDuration(duration : String)
}