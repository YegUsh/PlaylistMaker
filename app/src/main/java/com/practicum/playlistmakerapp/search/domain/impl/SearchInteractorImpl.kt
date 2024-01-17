package com.practicum.playlistmakerapp.search.domain.impl

import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.search.domain.api.SearchInteractor
import com.practicum.playlistmakerapp.search.domain.api.SearchRepository
import com.practicum.playlistmakerapp.search.domain.model.SearchUIType

class SearchInteractorImpl (private val searchRepository: SearchRepository): SearchInteractor {
    override fun clearHistory() {
        searchRepository.clear()
    }

    override fun saveData(historyList: ArrayList<TrackData>) {
        searchRepository.saveSearchHistory(historyList)
    }

    override fun getData(): List<TrackData> {
        return  searchRepository.getSearchHistory()
    }

    override fun loadTracks(
        query: String,
        onSuccess: (list: List<TrackData>) -> Unit,
        onError: (error: SearchUIType) -> Unit
    ) {
        searchRepository.loadTracks(query, onSuccess, onError)
    }
}