package com.practicum.playlistmakerapp.di

import android.content.Context
import com.google.gson.Gson
import com.practicum.playlistmakerapp.search.data.net.NetworkClient
import com.practicum.playlistmakerapp.search.data.net.RetrofitNetClient
import com.practicum.playlistmakerapp.search.data.net.model.TrackApi

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val APP_PREFERENCES = "app_preferences"
private val baseUrl = "https://itunes.apple.com"
val dataModule = module {
    single<TrackApi> {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrackApi::class.java)
    }
    single {
        androidContext()
            .getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    }
    single<NetworkClient>{
        RetrofitNetClient(get())
    }
    factory { Gson() }



}