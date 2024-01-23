package com.practicum.playlistmakerapp.music

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.practicum.playlistmakerapp.music.fragments.FavoritesFragment
import com.practicum.playlistmakerapp.music.fragments.PlaylistFragment

class LibraryPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return if (position == 0) FavoritesFragment.newInstance() else PlaylistFragment.newInstance()
    }
}