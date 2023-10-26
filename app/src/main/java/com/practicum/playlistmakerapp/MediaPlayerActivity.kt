package com.practicum.playlistmakerapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmakerapp.net.TrackData
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.*

class MediaPlayerActivity : AppCompatActivity() {
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
    private lateinit var url: String
    private lateinit var mpButton: ImageButton
    private lateinit var mpCurrentTrackDuration: TextView
    private var mediaPlayer = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())
    private var playerState = STATE_DEFAULT

    private lateinit var track: TrackData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)
        initializeUI()
        getData()
        initializeListeners()
        preparePlayer()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        mediaPlayer.release()
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
        mpButton.isEnabled = false
        mpCurrentTrackDuration = findViewById(R.id.mp_current_track_duration)
    }

    private fun initializeListeners() {
        mpBackBtn.setOnClickListener {
            onBackPressed()
        }
        mpButton.setOnClickListener {
            playbackControl()
        }
    }

    private fun getData() {

        val cornerRadius =
            applicationContext.resources.getDimensionPixelSize(R.dimen.main_btn_radius)
        track = intent.getParcelableExtra<TrackData>(SEARCH_KEY) ?: return
        Glide.with(this)
            .load(track?.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.ic_no_reply)
            .centerInside()
            .transform(RoundedCorners(cornerRadius))
            .into(mpCover)
        mpTrackName.text = track.trackName
        mpArtistName.text = track.artistName
        mpTrackDuration.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis).toString()
        mpTrackCountry.text = track.country
        mpTrackAlbum.text = if (track.collectionName.isNullOrEmpty()) {
            mpTrackAlbum.visibility = View.GONE
            mpTrackAlbumText.visibility = View.GONE
            ""
        }
        else track.collectionName

        url = track.previewUrl

        mpTrackGenre.text = track.primaryGenreName
        mpReleaseDate.text = track.releaseDate?.substring(0, 4)
        mpCurrentTrackDuration.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
        mpReleaseDate.text = track.releaseDate?.substring(0, 4)
    }

    private fun setDuration(milliseconds: Int) {
        mpCurrentTrackDuration.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(milliseconds)
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            mpButton.isEnabled = true
            playerState = STATE_PREPARED
            mpButton.setImageResource(R.drawable.ic_mp_play)
        }
        mediaPlayer.setOnCompletionListener {
            mpButton.setImageResource(R.drawable.ic_mp_play)
            playerState = STATE_PREPARED
            setDuration(0)
            handler.removeCallbacksAndMessages(null)
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        mpButton.setImageResource(R.drawable.ic_pause)
        playerState = STATE_PLAYING
        handler.postDelayed(object : Runnable {
            override fun run() {
                setDuration(mediaPlayer.currentPosition)
                handler.postDelayed(this, MP_DELAY)
            }
        }, MP_DELAY)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        mpButton.setImageResource(R.drawable.ic_mp_play)
        playerState = STATE_PAUSED
        handler.removeCallbacksAndMessages(null)
    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val MP_DELAY = 1000L
    }

}