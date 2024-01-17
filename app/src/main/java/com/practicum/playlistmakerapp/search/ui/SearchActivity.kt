package com.practicum.playlistmakerapp.search.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmakerapp.*
import com.practicum.playlistmakerapp.mediaplayer.domain.models.TrackData
import com.practicum.playlistmakerapp.search.domain.model.SearchUIType
import com.practicum.playlistmakerapp.search.ui.model.UiState
import com.practicum.playlistmakerapp.search.ui.view_model.SearchViewModel
import com.practicum.playlistmakerapp.util.router.SearchRouter

class SearchActivity : AppCompatActivity() {


    private val viewModel by lazy {
        ViewModelProvider(
            this,
            SearchViewModel.getViewModelFactory()
        )[SearchViewModel::class.java]
    }

    private var tempEditTextString = ""
    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true
    private lateinit var progressBar: ProgressBar
    private lateinit var searchEditText: EditText
    private lateinit var searchTracksRecyclerView: RecyclerView
    private lateinit var searchNothingToFindLayout: ConstraintLayout
    private lateinit var searchNothingFoundImage: ImageView
    private lateinit var searchNothingFoundText: TextView
    private lateinit var searchNothingFoundBtn: Button
    private lateinit var clearHistoryBtn: Button
    private lateinit var recentHistoryLayout: ConstraintLayout
    private lateinit var searchHistoryTracksRecyclerView: RecyclerView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var searchClearBtn: ImageView
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var historyTrackAdapter: TrackAdapter
    private lateinit var progressbar: ProgressBar
    private val searchRouter by lazy { SearchRouter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initializeUI()

        viewModel.observeUiStateLiveData().observe(this) {
            when (it) {
                UiState.Loading -> {
                    showLoading()
                }
                is UiState.HistoryContent -> {
                    showHistory(it.list)
                }
                is UiState.SearchContent -> {
                    showContent(it.list)
                }
                is UiState.Error -> {
                    selectSearchUI(uiType = it.error)
                }
            }
        }

        viewModel.observeTextWatcherStateLiveData().observe(this) {
            searchClearBtn.visibility = clearButtonVisibility(it)
        }

        toolbar.setNavigationOnClickListener { searchRouter.goBack() }


        val textWatcherSearchBtn = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchTextHasChanged(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        }
        searchEditText.addTextChangedListener(textWatcherSearchBtn)
        searchClearBtn.setOnClickListener {
            trackAdapter.recentTracks.clear()
            trackAdapter.notifyDataSetChanged()
            searchEditText.setText("")
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(searchEditText.windowToken, 0)
        }

        initAdapters()

    }

    private fun initAdapters() {
        trackAdapter = TrackAdapter {
            if (clickDebounce()) {
                viewModel.addToRecentHistoryList(it)
                searchRouter.openMediaPlayerActivity(it)
            }
        }

        searchTracksRecyclerView.adapter = trackAdapter
        searchTracksRecyclerView.layoutManager = LinearLayoutManager(this)
        historyTrackAdapter = TrackAdapter {
            if (clickDebounce()) {
                viewModel.addToRecentHistoryList(it)
                searchRouter.openMediaPlayerActivity(it)
            }
        }
        searchHistoryTracksRecyclerView.adapter = historyTrackAdapter
        searchHistoryTracksRecyclerView.layoutManager = LinearLayoutManager(this)
        searchHistoryTracksRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun showContent(list: List<TrackData>) {
        progressbar.visibility = View.GONE
        recentHistoryLayout.visibility = View.GONE
        searchTracksRecyclerView.visibility = View.VISIBLE
        searchNothingToFindLayout.visibility = View.GONE
        searchNothingFoundBtn.visibility = View.GONE
        trackAdapter.recentTracks.clear()
        trackAdapter.recentTracks.addAll(list)
        trackAdapter.notifyDataSetChanged()

    }

    private fun showHistory(list: List<TrackData>) {
        if (list.isNotEmpty())
            recentHistoryLayout.visibility = View.VISIBLE
        else
            recentHistoryLayout.visibility = View.GONE
        progressbar.visibility = View.GONE
        searchTracksRecyclerView.visibility = View.GONE
        searchNothingToFindLayout.visibility = View.GONE
        searchNothingFoundBtn.visibility = View.GONE
        historyTrackAdapter.recentTracks.clear()
        historyTrackAdapter.recentTracks.addAll(list)
        historyTrackAdapter.notifyDataSetChanged()
    }

    private fun showLoading() {
        progressbar.visibility = View.VISIBLE
        recentHistoryLayout.visibility = View.GONE
        searchTracksRecyclerView.visibility = View.GONE
        searchNothingToFindLayout.visibility = View.GONE
        searchNothingFoundBtn.visibility = View.GONE
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

    private fun clearButtonVisibility(s: Boolean): Int {
        return if (!s) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun selectSearchUI(uiType: SearchUIType) {
        when (uiType) {
            SearchUIType.NO_INTERNET -> {
                recentHistoryLayout.visibility = View.GONE
                searchTracksRecyclerView.visibility = View.GONE
                searchNothingToFindLayout.visibility = View.VISIBLE
                searchNothingFoundBtn.visibility = View.VISIBLE
                searchNothingFoundImage.setImageResource(R.drawable.ic_bad_connection)
                searchNothingFoundText.text = getText(R.string.search_no_internet_text)
                searchNothingFoundBtn.visibility = View.VISIBLE
                progressbar.visibility = View.GONE
                searchNothingFoundBtn.setOnClickListener { viewModel.search(searchEditText.text.toString()) }
            }
            SearchUIType.NO_DATA -> {
                progressbar.visibility = View.GONE
                recentHistoryLayout.visibility = View.GONE
                searchNothingFoundBtn.visibility = View.GONE
                searchTracksRecyclerView.visibility = View.GONE
                searchNothingToFindLayout.visibility = View.VISIBLE
                searchNothingFoundImage.setImageResource(R.drawable.ic_nothing_found)
                searchNothingFoundText.text = getText(R.string.search_nothing_find_text)
            }
        }
    }

    private fun initializeUI() {
        progressbar = findViewById(R.id.progressBar)
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
    }

    companion object {
        private const val PRODUCT_AMOUNT = "PRODUCT_AMOUNT"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

}