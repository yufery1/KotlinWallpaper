package com.example.kotlinwallpaper.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinwallpaper.R
import com.example.kotlinwallpaper.databinding.FragmentWallpaperBinding
import com.example.kotlinwallpaper.domain.model.Wallpaper
import com.example.kotlinwallpaper.presentation.adapter.WallpaperAdapter
import com.example.kotlinwallpaper.presentation.viewmodel.WallpaperViewModel

class WallpaperFragment : Fragment() {

    private val binding:FragmentWallpaperBinding by lazy {
        FragmentWallpaperBinding.inflate(layoutInflater)
    }

    private lateinit var viewmodel:WallpaperViewModel
    private lateinit var adapter: WallpaperAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this).get(WallpaperViewModel::class.java)
        viewmodel.fetchWallpapers(20)//Загружаем 20 обоев \ можно поменять на свой вкус
        viewmodel.getWallpapers().observe(viewLifecycleOwner,
            {
                wallpapers ->
                adapter.submit(wallpapers)
            })
    }

    private fun setupRecyclerView(view: View){
        val recyclerView = view.findViewById<RecyclerView>(R.id.wallpaperRecyclerView)
        adapter = WallpaperAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
    }
}