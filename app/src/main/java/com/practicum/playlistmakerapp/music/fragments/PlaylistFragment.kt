package com.practicum.playlistmakerapp.music.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.practicum.playlistmakerapp.R
import com.practicum.playlistmakerapp.music.view_model.PlaylistViewModel

class PlaylistFragment : Fragment() {
    private val viewModel by viewModels<PlaylistViewModel>()

    companion object {
        fun newInstance() = PlaylistFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }


}