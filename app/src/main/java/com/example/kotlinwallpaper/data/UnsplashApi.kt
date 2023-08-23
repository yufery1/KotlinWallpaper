package com.example.kotlinwallpaper.data

import com.example.kotlinwallpaper.data.model.UnsplashPhoto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos/random")
    suspend fun getRandomWallpapers(
        @Query("client_id") apiKey: String,
        @Query("count") count: Int
    ): List<UnsplashPhoto>
}
object UnsplashApiService {
    private const val BASE_URL = "https://api.unsplash.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: UnsplashApi = retrofit.create(UnsplashApi::class.java)
}