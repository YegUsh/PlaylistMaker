package com.practicum.playlistmakerapp.search.domain.api

import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.search.domain.model.SearchUIType
import com.practicum.playlistmakerapp.search.ui.SearchActivity

interface SearchInteractor {
    fun clearHistory()
    fun saveData(historyList: ArrayList<TrackData>)
    fun getData():List<TrackData>
    fun loadTracks(query: String, onSuccess: (list: List<TrackData>)-> Unit, onError: (error: SearchUIType)-> Unit)
}