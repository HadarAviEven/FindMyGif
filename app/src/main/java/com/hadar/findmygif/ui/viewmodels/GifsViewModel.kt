package com.hadar.findmygif.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hadar.findmygif.data.repositories.GifRepository
import com.hadar.findmygif.ui.SingleLiveEvent
import com.hadar.findmygif.ui.models.Gif

class GifsViewModel(application: Application?) : AndroidViewModel(application!!) {
    private var gifRepository: GifRepository? = null
    var gifModelListLiveData = MutableLiveData<List<Gif>>()

    val expandGifLiveEvent = SingleLiveEvent<Pair<String?, String?>>()

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

    fun onGifItemClicked(gif: Gif?) {
        expandGifLiveEvent.value = Pair(gif?.imageUrl, gif?.gifTitle)
    }
}















