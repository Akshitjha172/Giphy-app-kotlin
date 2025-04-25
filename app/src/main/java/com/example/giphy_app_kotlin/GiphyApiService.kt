package com.example.giphy_app_kotlin


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object GiphyApi {
    const val BASE_URL = "https://api.giphy.com/v1/gifs/"
    const val API_KEY = "MJD9HEO3jTpPHHaMcsHjDZMCeC0EUUnS"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: GiphyService by lazy {
        retrofit.create(GiphyService::class.java)
    }
}

interface GiphyService {
    @GET("trending")
    fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<GiphyResponse>

    @GET("search")
    fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<GiphyResponse>
}
