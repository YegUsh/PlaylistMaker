package com.practicum.playlistmakerapp.mediaplayer.presentation

import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.search.ui.SEARCH_KEY

class Router (private val activity: AppCompatActivity){
    fun getTrack(): TrackData {
        return activity.intent.getSerializableExtra(SEARCH_KEY)!! as TrackData
    }
}