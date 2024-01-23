package com.practicum.playlistmakerapp.music.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.practicum.playlistmakerapp.R
import com.practicum.playlistmakerapp.music.view_model.FavoritesViewModel


class FavoritesFragment : Fragment() {

    private val viewModel by viewModels<FavoritesViewModel>()

    companion object {
        fun newInstance() = FavoritesFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }
}

