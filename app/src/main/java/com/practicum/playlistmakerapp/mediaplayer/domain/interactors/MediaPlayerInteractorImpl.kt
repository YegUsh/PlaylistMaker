package com.practicum.playlistmakerapp.mediaplayer.domain.interactors

import com.practicum.playlistmakerapp.mediaplayer.domain.models.PlayerState
import com.practicum.playlistmakerapp.mediaplayer.domain.repository.MediaPlayerRepository
import java.text.SimpleDateFormat
import java.util.*

class MediaPlayerInteractorImpl(val mediaPlayerRepository: MediaPlayerRepository) : MediaPlayerInteractor {


    override fun pausePlayer() {
        mediaPlayerRepository.pausedPlayer()

    }

    override fun start(trackUrl: String) {
        mediaPlayerRepository.startPlayer(trackUrl)

    }

    override fun release() {
        mediaPlayerRepository.stopPlayer()
    }

    override fun prepare(trackUrl: String) {
        mediaPlayerRepository.preparePlayer(trackUrl)
    }

    override fun getPlayerState(): PlayerState {
        return mediaPlayerRepository.getPlayState()
    }

    override fun getCurrentPosition(): String {
        return convertMillisecondsToString(mediaPlayerRepository.getCurrentPosition())
    }

    private fun convertMillisecondsToString(duration: Int): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(duration)
    }

}