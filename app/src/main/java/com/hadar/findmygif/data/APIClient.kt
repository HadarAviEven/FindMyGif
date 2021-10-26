package com.hadar.findmygif.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
        const val BASE_URL = "http://api.giphy.com/"
    }

    private lateinit var retrofit: Retrofit
    private var client: OkHttpClient? = null

    private fun getRetrofitClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client!!)
            .build()
        return retrofit
    }

    fun <T> createService(interfaceClazz: Class<T>): T {
        return getRetrofitClient().create(interfaceClazz)
    }
}
