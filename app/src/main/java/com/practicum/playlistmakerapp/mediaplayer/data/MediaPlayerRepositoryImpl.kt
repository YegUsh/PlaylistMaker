package com.practicum.playlistmakerapp.mediaplayer.data

import android.media.MediaPlayer
import com.practicum.playlistmakerapp.mediaplayer.domain.models.PlayerState
import com.practicum.playlistmakerapp.mediaplayer.domain.repository.MediaPlayerRepository

class MediaPlayerRepositoryImpl() :
    MediaPlayerRepository {
    override  var playerState = PlayerState.STATE_DEFAULT
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    override fun preparePlayer(trackUrl: String) {
        mediaPlayer.apply {
            setDataSource(trackUrl)
            prepare()
            playerState = PlayerState.STATE_PREPARED
            setOnCompletionListener {
                playerState = PlayerState.STATE_PREPARED
            }
        }
    }

    override fun startPlayer() {
        mediaPlayer.start()
        playerState = PlayerState.STATE_PLAYING
    }

    override fun stopPlayer() {
        mediaPlayer.release()

    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun pausedPlayer() {
        mediaPlayer.pause()
        playerState = PlayerState.STATE_PAUSED
    }
}