package com.practicum.playlistmakerapp.search.ui.model

import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.search.domain.model.SearchUIType

sealed interface UiState {

    object Loading : UiState
    data class SearchContent(val list: List<TrackData>) : UiState
    data class HistoryContent(val list: List<TrackData>) : UiState
    data class Error(val error: SearchUIType) : UiState
}