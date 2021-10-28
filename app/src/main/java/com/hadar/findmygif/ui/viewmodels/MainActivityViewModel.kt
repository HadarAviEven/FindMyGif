package com.hadar.findmygif.ui.viewmodels

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import com.hadar.findmygif.R

class MainActivityViewModel(application: Application?) : AndroidViewModel(application!!) {

    private var mediaPlayer = MediaPlayer.create(getApplication(), R.raw.myfavoritesong)

    init {
        mediaPlayer = MediaPlayer.create(getApplication(), R.raw.myfavoritesong)
    }

    fun onStart() {
        mediaPlayer.start()
    }

    fun onPause() {
        mediaPlayer.pause()
    }
}

