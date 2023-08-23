package com.example.kotlinwallpaper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinwallpaper.domain.model.Wallpaper
import com.example.kotlinwallpaper.domain.repository.WallpaperRepository
import kotlinx.coroutines.launch

class WallpaperViewModel(private val repository: WallpaperRepository):ViewModel() {
    private val wallpapersLiveData = MutableLiveData<List<Wallpaper>>()

    fun getWallpapers():LiveData<List<Wallpaper>> = wallpapersLiveData

    fun fetchWallpapers(count:Int){
        viewModelScope.launch {
            val wallpapers = repository.getRandomWallpapers(count)
            wallpapersLiveData.postValue(wallpapers)
        }
    }
}