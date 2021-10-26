package com.hadar.findmygif.ui.viewmodels

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hadar.findmygif.R
import com.hadar.findmygif.data.repositories.GifRepository
import com.hadar.findmygif.ui.models.Gif

class GifsViewModel(application: Application?) : AndroidViewModel(application!!) {
    private var gifRepository: GifRepository? = null
    var gifModelListLiveData = MutableLiveData<List<Gif>>()
    private var mediaPlayer = MediaPlayer.create(application, R.raw.myfavoritesong)

    init {
        gifRepository = GifRepository(application)
        getTrendingGifs()
    }

    fun getTrendingGifs() {
        gifRepository?.getTrendingGifs(gifModelListLiveData)
    }

    fun onTextChanged(text: String) {
        gifRepository?.getSearchGifs(text, gifModelListLiveData)
    }

    fun onStart() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
            mediaPlayer = MediaPlayer.create(getApplication(), R.raw.myfavoritesong)
        }
        mediaPlayer.start()
    }

    fun onPause() {
        mediaPlayer.pause()
    }
}















