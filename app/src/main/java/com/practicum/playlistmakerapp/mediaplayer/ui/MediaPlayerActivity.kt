package com.practicum.playlistmakerapp.mediaplayer.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmakerapp.R
import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.mediaplayer.ui.view_model.MediaPlayerViewModel
import com.practicum.playlistmakerapp.main.PlayStatus
import com.practicum.playlistmakerapp.search.ui.SEARCH_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class MediaPlayerActivity : AppCompatActivity() {
    private val viewModel by viewModel<MediaPlayerViewModel>()
    private lateinit var mpBackBtn: ImageButton
    private lateinit var mpCover: ImageView
    private lateinit var mpTrackName: TextView
    private lateinit var mpArtistName: TextView
    private lateinit var mpTrackDuration: TextView
    private lateinit var mpTrackCountry: TextView
    private lateinit var mpTrackGenre: TextView
    private lateinit var mpTrackAlbum: TextView
    private lateinit var mpTrackAlbumText: TextView
    private lateinit var mpReleaseDate: TextView
    private lateinit var mpButton: ImageButton
    private lateinit var mpLiked: ImageButton
    private lateinit var mpCurrentTrackDuration: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)
        initializeUI()
        val track = intent.getSerializableExtra(SEARCH_KEY)!! as TrackData
        viewModel.preparePlayer(track.previewUrl)
        getData(track)
        viewModel.getPlayStatusLiveData().observe(this) {
            when (it) {
                PlayStatus.OnPause -> mpButton.setImageResource(R.drawable.ic_mp_play)
                PlayStatus.OnStart -> mpButton.setImageResource(R.drawable.ic_pause)
                else -> throw AssertionError()
            }
        }
        viewModel.getDurationLiveData().observe(this) {
            setDuration(it)
        }
        initializeListeners(track.previewUrl)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onViewPaused()
    }

    private fun initializeUI() {
        mpBackBtn = findViewById(R.id.music_back_btn)
        mpCover = findViewById(R.id.mp_cover)
        mpTrackName = findViewById(R.id.mp_trackName)
        mpArtistName = findViewById(R.id.mp_artistName)
        mpTrackDuration = findViewById(R.id.track_duration_value)
        mpTrackCountry = findViewById(R.id.track_country_value)
        mpTrackGenre = findViewById(R.id.track_primary_genre_name_value)
        mpTrackAlbum = findViewById(R.id.collection_duration_value)
        mpTrackAlbumText = findViewById(R.id.collection_duration_text)
        mpReleaseDate = findViewById(R.id.track_release_date_value)
        mpTrackAlbum.visibility = View.VISIBLE
        mpTrackAlbumText.visibility = View.VISIBLE
        mpButton = findViewById(R.id.mp_play_btn)
        mpLiked = findViewById(R.id.mp_fav_btn)
        mpCurrentTrackDuration = findViewById(R.id.mp_current_track_duration)
    }

    private fun initializeListeners(trackUrl: String) {
        mpBackBtn.setOnClickListener {
            finish()
        }
        mpButton.setOnClickListener {
            viewModel.onPlayBtnClicked(trackUrl)
        }
    }

    fun getData(trackData: TrackData) {

        val cornerRadius =
            applicationContext.resources.getDimensionPixelSize(R.dimen.main_btn_radius)
        Glide.with(this)
            .load(trackData.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.ic_no_reply)
            .centerInside()
            .transform(RoundedCorners(cornerRadius))
            .into(mpCover)
        mpTrackName.text = trackData.trackName
        mpArtistName.text = trackData.artistName
        mpTrackDuration.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackData.trackTimeMillis).toString()
        mpTrackCountry.text = trackData.country
        mpTrackAlbum.text = trackData.collectionName.ifEmpty {
            mpTrackAlbum.visibility = View.GONE
            mpTrackAlbumText.visibility = View.GONE
            ""
        }


        mpTrackGenre.text = trackData.primaryGenreName
        mpCurrentTrackDuration.text = "00:00"
        mpReleaseDate.text = trackData.releaseDate.substring(0, 4)
    }

    private fun setDuration(duration: String) {
        mpCurrentTrackDuration.text = duration
    }


}

