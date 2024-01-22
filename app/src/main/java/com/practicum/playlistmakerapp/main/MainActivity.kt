package com.practicum.playlistmakerapp.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmakerapp.R
import com.practicum.playlistmakerapp.main.model.MainNavigation
import com.practicum.playlistmakerapp.main.view_model.MainViewModel
import com.practicum.playlistmakerapp.music.MediaActivity
import com.practicum.playlistmakerapp.search.ui.SearchActivity
import com.practicum.playlistmakerapp.settings.ui.SettingsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.button_search)
        val buttonMediaLibrary = findViewById<Button>(R.id.button_media)
        val buttonMainSettings = findViewById<Button>(R.id.button_settings)


        viewModel.getMainStatusLiveData().observe(this) {
            when (it) {
                MainNavigation.SEARCH -> startActivity(
                    Intent(
                        this@MainActivity,
                        SearchActivity::class.java
                    )
                )
                MainNavigation.MUSIC_LIBLARY -> startActivity(
                    Intent(
                        this@MainActivity,
                        MediaActivity::class.java
                    )
                )
                MainNavigation.SETTINGS -> startActivity(
                    Intent(
                        this@MainActivity,
                        SettingsActivity::class.java
                    )
                )
            }
        }

        buttonSearch.setOnClickListener {
            viewModel.startSearchActivity()
        }

        buttonMediaLibrary.setOnClickListener {
            viewModel.startMusicLiblaryActivity()

        }

        buttonMainSettings.setOnClickListener {
            viewModel.startSettingsActivity()

        }
    }
}