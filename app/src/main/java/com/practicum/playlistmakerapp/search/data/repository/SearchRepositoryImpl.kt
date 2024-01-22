package com.practicum.playlistmakerapp.search.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.search.data.net.NetworkClient
import com.practicum.playlistmakerapp.search.domain.api.SearchRepository
import com.practicum.playlistmakerapp.search.domain.model.SearchUIType

class SearchRepositoryImpl(private val sharedPreferences: SharedPreferences,
                           private val networkClient: NetworkClient,
                           val gson: Gson) : SearchRepository {


    override fun clear() {
        sharedPreferences.edit().remove(HISTORY_KEY).apply()
    }

    override fun saveSearchHistory(historyTrack: ArrayList<TrackData>) {
        val json = gson.toJson(historyTrack)
        sharedPreferences.edit().putString(HISTORY_KEY, json).apply()
    }

    override fun loadTracks(
        query: String,
        onSuccess: (list: List<TrackData>) -> Unit,
        onError: (error: SearchUIType) -> Unit
    ) {
        networkClient.search(query,onSuccess,onError)
    }
    override fun getSearchHistory(): List<TrackData> {
        val json = sharedPreferences.getString(HISTORY_KEY, null) ?: return emptyList()
        return Gson().fromJson(json, Array<TrackData>::class.java).toList()
    }
    companion object {
        const val HISTORY_KEY = "history_key"
    }

}