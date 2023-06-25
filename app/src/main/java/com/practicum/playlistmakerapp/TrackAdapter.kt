package com.practicum.playlistmakerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmakerapp.net.TrackData
import java.util.*
import com.practicum.playlistmakerapp.net.*
import kotlin.collections.ArrayList
import java.text.SimpleDateFormat

class TrackAdapter(private val tracks: ArrayList<TrackData>) :
    RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    class TrackViewHolder(item: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(item.context).inflate(R.layout.track_items, item, false)
    ) {
        private val imageTitle = itemView.findViewById<ImageView>(R.id.rv_image_title)
        private val rvTrackName = itemView.findViewById<TextView>(R.id.rv_track_name)
        private val rvArtistName = itemView.findViewById<TextView>(R.id.rv_artist_name)
        private val rvTrackDuration = itemView.findViewById<TextView>(R.id.rv_track_duration)

        fun bind(model: TrackData) {
            Glide.with(itemView.context)
                .load(model.artworkUrl100)
                .centerCrop()
                .transform(RoundedCorners(10))
                .placeholder(R.drawable.ic_no_reply)
                .into(imageTitle)
            rvTrackName.text = model.trackName
            rvArtistName.text = model.artistName
            rvTrackDuration.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(293000L).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TrackViewHolder(parent)

    override fun getItemCount() = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) =
        holder.bind(tracks[position])
}