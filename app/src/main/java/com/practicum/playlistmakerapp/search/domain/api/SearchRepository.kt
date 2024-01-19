package com.practicum.playlistmakerapp.search.domain.api

import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.search.domain.model.SearchUIType

interface SearchRepository {

    fun clear()
    fun saveSearchHistory(historyTrack: ArrayList<TrackData>)
    fun loadTracks(query: String, onSuccess: (list: List<TrackData>) -> Unit, onError: (error: SearchUIType) -> Unit)
    fun getSearchHistory(): List<TrackData>
}