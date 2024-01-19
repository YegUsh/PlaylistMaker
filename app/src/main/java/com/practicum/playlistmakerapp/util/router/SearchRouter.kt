package com.practicum.playlistmakerapp.util.router

import android.app.Activity
import android.content.Intent
import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.mediaplayer.ui.MediaPlayerActivity
import com.practicum.playlistmakerapp.search.ui.SEARCH_KEY

class SearchRouter(val activity: Activity) {

    fun goBack() {
        activity.finish()
    }

    fun openMediaPlayerActivity(track: TrackData) {
        transitionToMediaPlayerActivity(track)
    }

    private fun transitionToMediaPlayerActivity(track: TrackData) {
        val sendIntent = Intent(activity, MediaPlayerActivity::class.java)
        sendIntent.putExtra(
            SEARCH_KEY, track
        )
        activity.startActivity(sendIntent)
    }
}