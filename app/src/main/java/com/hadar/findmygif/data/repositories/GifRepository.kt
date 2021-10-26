package com.hadar.findmygif.data.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hadar.findmygif.R
import com.hadar.findmygif.data.APIClient
import com.hadar.findmygif.data.APIInterface
import com.hadar.findmygif.data.GiphyTrendingResponse
import com.hadar.findmygif.ui.models.Gif
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GifRepository(private val application: Application?) {
    private val apiInterface = APIClient().createService(APIInterface::class.java)

    fun getTrendingGifs(gifModelListLiveData: MutableLiveData<List<Gif>>) {
        if (application != null) {
            apiInterface.getTrendingGifs(
                apiKey = application.applicationContext.getString(R.string.api_key),
                limit = application.applicationContext.getString(R.string.trending_limit).toInt(),
                offset = application.applicationContext.getString(R.string.trending_offset).toInt()
            ).enqueue(getCallBack(gifModelListLiveData))
        }
    }

    fun getSearchGifs(searchWord: String, gifModelListLiveData: MutableLiveData<List<Gif>>) {
        if (application != null) {
            apiInterface.getSearchGifs(
                q = searchWord,
                apiKey = application.applicationContext.getString(R.string.api_key),
                limit = application.applicationContext.getString(R.string.search_limit).toInt(),
                offset = application.applicationContext.getString(R.string.search_offset).toInt(),
                lang = application.applicationContext.getString(R.string.lang),

                ).enqueue(getCallBack(gifModelListLiveData))
        }
    }

    private fun getCallBack(liveData: MutableLiveData<List<Gif>>) =
        object : Callback<GiphyTrendingResponse?> {

            override fun onFailure(call: Call<GiphyTrendingResponse?>, t: Throwable) {
                liveData.value = null
            }

            override fun onResponse(
                call: Call<GiphyTrendingResponse?>,
                response: Response<GiphyTrendingResponse?>
            ) {

                if (response.isSuccessful) {
                    val giphyResponse = response.body()
                    val dataGiphyList = giphyResponse?.data

                    if (dataGiphyList != null) {
                        val gifs = ArrayList<Gif>()
                        for (item in dataGiphyList) {
                            gifs.add(Gif(item.title, item.images.original.url))
                        }
                        liveData.value = gifs
                    }
                } else {
                    liveData.value = null
                }
            }
        }
}