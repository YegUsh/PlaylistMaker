package com.practicum.playlistmakerapp.presentation

import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmakerapp.domain.models.TrackData
import com.practicum.playlistmakerapp.ui.SEARCH_KEY

class Router (private val activity: AppCompatActivity){
    fun getTrack(): TrackData {
        return activity.intent.getSerializableExtra(SEARCH_KEY)!! as TrackData
    }
}