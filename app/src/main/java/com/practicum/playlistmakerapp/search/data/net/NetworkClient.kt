package com.practicum.playlistmakerapp.search.data.net

import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.search.domain.model.SearchUIType

interface NetworkClient {
    fun search(query: String, onSuccess: (list: List<TrackData>)-> Unit, onError: (error: SearchUIType)-> Unit)
}