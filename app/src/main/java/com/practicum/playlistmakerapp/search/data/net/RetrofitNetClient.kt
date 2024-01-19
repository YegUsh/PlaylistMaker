package com.practicum.playlistmakerapp.search.data.net

import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.net.TrackResponse
import com.practicum.playlistmakerapp.search.data.net.model.TrackApi
import com.practicum.playlistmakerapp.search.domain.model.SearchUIType
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetClient : NetworkClient {
    companion object {
        const val baseUrl = "https://itunes.apple.com"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val songService = retrofit.create(TrackApi::class.java)

    override fun search(query: String, onSuccess: (list: List<TrackData>)-> Unit, onError: (error: SearchUIType)-> Unit) {
        songService.search(query)
            .enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    when (response.code()) {
                        200 ->
                            if (response.body()?.results?.isNotEmpty() == true) {
                                onSuccess.invoke(response.body()?.results!!.map {
                                    TrackData(
                                        trackId = it.trackId,
                                        trackName = it.trackName,
                                        artistName = it.artistName,
                                        trackTimeMillis = it.trackTimeMillis,
                                        artworkUrl100 = it.artworkUrl100,
                                        collectionName = it.collectionName,
                                        country = it.country,
                                        primaryGenreName = it.primaryGenreName,
                                        releaseDate = it.releaseDate,
                                        previewUrl = it.previewUrl,
                                    )
                                })
                            } else {
                                onError.invoke(SearchUIType.NO_DATA)

                            }

                        else -> {
                            onError.invoke(SearchUIType.NO_INTERNET)
                        }
                    }
                }


                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    onError.invoke(SearchUIType.NO_INTERNET)

                }

            })
    }
}