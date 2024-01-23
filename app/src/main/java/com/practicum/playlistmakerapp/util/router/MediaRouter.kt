package com.practicum.playlistmakerapp.util.router

import android.app.Activity

class MediaRouter(val activity: Activity)  {
    fun goBack() {
        activity.finish()
    }

}