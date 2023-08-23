package com.example.kotlinwallpaper.domain.repository

import com.example.kotlinwallpaper.data.UnsplashApi
import com.example.kotlinwallpaper.domain.model.Wallpaper

class WallpaperRepository(private val unsplashApi:UnsplashApi) {
    suspend fun getRandomWallpapers(count:Int):List<Wallpaper>{
        val response = unsplashApi.getRandomWallpapers("",count)
        return response.map {
            it ->
            Wallpaper(it.id,it.urls.regular)
        }
    }
}