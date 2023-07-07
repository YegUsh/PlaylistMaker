package com.practicum.playlistmakerapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.practicum.playlistmakerapp.net.TrackApi
import com.practicum.playlistmakerapp.net.TrackData
import com.practicum.playlistmakerapp.net.TrackResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private var tempEditTextString = ""
    private lateinit var searchEditText: EditText
    private lateinit var searchTracksRecyclerView: RecyclerView
    private lateinit var searchNothingToFindLayout: ConstraintLayout
    private lateinit var searchNothingFoundImage: ImageView
    private lateinit var searchNothingFoundText: TextView
    private lateinit var searchNothingFoundBtn: Button
    private lateinit var clearHistoryBtn: Button
    private lateinit var searchHistory: SearchHistory
    private lateinit var recentHistoryLayout: ConstraintLayout
    private lateinit var searchHistoryTracksRecyclerView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var searchClearBtn: ImageView
    private var tracks = ArrayList<TrackData>()
    private var recentHistoryTracks = ArrayList<TrackData>()
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val songService = retrofit.create(TrackApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initializeUI()

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (searchEditText.text.isNotEmpty())
                    search()
                else {
                    selectSearchUI(SearchUIType.NO_DATA)
                }
                true
            }
            false
        }

        val textWatcherSearchBtn = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchClearBtn.visibility = clearButtonVisibility(p0)
                tempEditTextString = p0.toString()
                searchTracksRecyclerView.visibility = View.GONE
                if (recentHistoryTracks.isNotEmpty() && searchEditText.text.isEmpty())
                    recentHistoryLayout.visibility = View.VISIBLE
                else
                    recentHistoryLayout.visibility = View.GONE
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
        searchEditText.addTextChangedListener(textWatcherSearchBtn)
        searchClearBtn.setOnClickListener {
            searchEditText.text.clear()
            tracks.clear()
            searchTracksRecyclerView.adapter?.notifyDataSetChanged()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        val trackApapter = TrackAdapter {
            addToRecentHistoryList(it)
            transitionToMediaPlayerActivity(it)
        }
        trackApapter.recentTracks = tracks
        searchTracksRecyclerView.adapter = trackApapter
        searchTracksRecyclerView.layoutManager = LinearLayoutManager(this)
        val historyTrackAdapter = TrackAdapter {
            transitionToMediaPlayerActivity(it)
        }
        historyTrackAdapter.recentTracks = recentHistoryTracks
        searchHistoryTracksRecyclerView.adapter = historyTrackAdapter
        searchHistoryTracksRecyclerView.layoutManager = LinearLayoutManager(this)

        clearHistoryBtn.setOnClickListener {
            searchHistory.clear()
            recentHistoryLayout.visibility = View.GONE
            recentHistoryTracks.clear()
            searchHistoryTracksRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun transitionToMediaPlayerActivity(track: TrackData) {
        val sendIntent: Intent = Intent(applicationContext, MediaPlayerActivity::class.java)
        sendIntent.putExtra(
            SEARCH_KEY, track
        )
        startActivity(sendIntent)
    }

    private fun addToRecentHistoryList(trackData: TrackData) {
        for (index in recentHistoryTracks.indices) {
            if (recentHistoryTracks[index].trackId == trackData.trackId) {
                recentHistoryTracks.removeAt(index)
                recentHistoryTracks.add(0, trackData)
                searchHistoryTracksRecyclerView.adapter?.notifyItemMoved(index, 0)
                return
            }
        }

        if (recentHistoryTracks.size < 10) {
            recentHistoryTracks.add(0, trackData)
            searchHistoryTracksRecyclerView.adapter?.notifyItemInserted(0)
            searchHistoryTracksRecyclerView.adapter?.notifyItemRangeChanged(
                0,
                recentHistoryTracks.size
            )
        } else {
            recentHistoryTracks.removeAt(9)
            searchHistoryTracksRecyclerView.adapter?.notifyItemRemoved(0)
            searchHistoryTracksRecyclerView.adapter?.notifyItemRangeChanged(
                9,
                recentHistoryTracks.size
            )

        }

    }

    override fun onStop() {
        super.onStop()
        searchHistory.save(recentHistoryTracks)
    }

    private fun search() {
        songService.search(searchEditText.text.toString())
            .enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    when (response.code()) {
                        200 ->
                            if (response.body()?.results?.isNotEmpty() == true) {
                                selectSearchUI(SearchUIType.SUCCESS)
                                tracks.clear()
                                tracks.addAll(response.body()?.results!!)
                                searchTracksRecyclerView.adapter?.notifyDataSetChanged()

                            } else {
                                selectSearchUI(SearchUIType.NO_DATA)

                            }

                        else -> {
                            selectSearchUI(SearchUIType.NO_INTERNET)
                        }
                    }
                }


                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    selectSearchUI(SearchUIType.NO_INTERNET)
                }

            })
    }

    private fun selectSearchUI(uiType: SearchUIType) {
        when (uiType) {
            SearchUIType.SUCCESS -> {
                recentHistoryLayout.visibility = View.GONE
                searchTracksRecyclerView.visibility = View.VISIBLE
                searchNothingToFindLayout.visibility = View.GONE
                searchNothingFoundBtn.visibility = View.GONE

            }
            SearchUIType.NO_INTERNET -> {
                recentHistoryLayout.visibility = View.GONE
                searchTracksRecyclerView.visibility = View.GONE
                searchNothingToFindLayout.visibility = View.VISIBLE
                searchNothingFoundBtn.visibility = View.VISIBLE
                searchNothingFoundImage.setImageResource(R.drawable.ic_bad_connection)
                searchNothingFoundText.text = getText(R.string.search_no_internet_text)
                searchNothingFoundBtn.visibility = View.VISIBLE
                searchNothingFoundBtn.setOnClickListener { search() }
            }
            SearchUIType.NO_DATA -> {
                recentHistoryLayout.visibility = View.GONE
                searchNothingFoundBtn.visibility = View.GONE
                searchTracksRecyclerView.visibility = View.GONE
                searchNothingToFindLayout.visibility = View.VISIBLE
                searchNothingFoundImage.setImageResource(R.drawable.ic_nothing_found)
                searchNothingFoundText.text =
                    getText(R.string.search_nothing_find_text)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PRODUCT_AMOUNT, tempEditTextString)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val searchEditText = findViewById<EditText>(R.id.search_search)
        tempEditTextString = savedInstanceState.getString(PRODUCT_AMOUNT).toString()
        searchEditText.setText(tempEditTextString)
    }


    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun initializeUI() {
        toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_toolbar)
        searchEditText = findViewById(R.id.search_search)
        searchNothingToFindLayout = findViewById(R.id.search_nothing_found_layout)
        searchNothingFoundImage = findViewById(R.id.search_nothing_found_image)
        searchNothingFoundText = findViewById(R.id.search_nothing_found_text)
        searchNothingFoundBtn = findViewById(R.id.search_nothing_found_btn)
        searchHistoryTracksRecyclerView = findViewById(R.id.search_history_tracks_recyclerview)
        recentHistoryLayout = findViewById(R.id.recent_history_layout)
        clearHistoryBtn = findViewById(R.id.clear_history)
        searchClearBtn = findViewById(R.id.search_cancel_btn)
        searchTracksRecyclerView = findViewById(R.id.search_tracks_recyclerview)
        searchClearBtn.visibility = View.GONE
        searchHistory =
            SearchHistory(getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE))
        recentHistoryTracks.addAll(searchHistory.load())
        if (recentHistoryTracks.isNotEmpty())
            recentHistoryLayout.visibility = View.VISIBLE
    }


    enum class SearchUIType {
        SUCCESS,
        NO_INTERNET,
        NO_DATA
    }

    companion object {
        private const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
        private const val baseUrl = "https://itunes.apple.com"
    }
}