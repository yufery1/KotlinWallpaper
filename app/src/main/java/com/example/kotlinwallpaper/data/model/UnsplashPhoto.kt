package com.example.kotlinwallpaper.data.model

data class UnsplashPhoto(
    val id: String,
    val urls: UnsplashUrls
)

data class UnsplashUrls(
    val regular: String
)