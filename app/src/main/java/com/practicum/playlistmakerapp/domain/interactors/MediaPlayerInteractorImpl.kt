package com.practicum.playlistmakerapp.domain.interactors

import com.practicum.playlistmakerapp.domain.models.PlayerState
import com.practicum.playlistmakerapp.domain.repository.MediaPlayerRepository

    class MediaPlayerInteractorImpl(val mediaPlayerRepository: MediaPlayerRepository) : MediaPlayerInteractor {


    override fun pausePlayer() {
        mediaPlayerRepository.pausedPlayer()

    }

    override fun start() {
        mediaPlayerRepository.startPlayer()

    }

    override fun release() {
        mediaPlayerRepository.stopPlayer()
    }

    override fun prepare() {
        mediaPlayerRepository.preparePlayer()
    }

    override fun getPlayerState(): PlayerState {
        return mediaPlayerRepository.playerState
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayerRepository.getCurrentPosition()
    }
}