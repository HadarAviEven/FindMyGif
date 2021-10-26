package com.hadar.findmygif.data

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("/v1/gifs/trending")
    fun getTrendingGifs(
        @Query("api_key") apiKey: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Call<GiphyTrendingResponse?>

    @GET("/v1/gifs/search")
    fun getSearchGifs(
        @Query("api_key") apiKey: String?,
        @Query("q") q: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
        @Query("lang") lang: String?
    ): Call<GiphyTrendingResponse?>
}
