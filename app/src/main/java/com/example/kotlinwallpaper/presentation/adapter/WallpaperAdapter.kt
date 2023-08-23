package com.example.kotlinwallpaper.presentation.adapter

import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinwallpaper.R
import com.example.kotlinwallpaper.databinding.ItemWallpaperBinding
import com.example.kotlinwallpaper.domain.model.Wallpaper

class WallpaperAdapter : ListAdapter<Wallpaper, WallpaperAdapter.WallpaperViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Wallpaper>() {
        override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
            return oldItem == newItem
        }
    }

    class WallpaperViewHolder(private val binding: ItemWallpaperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wallpaper: Wallpaper) {
            Glide.with(binding.root)
                .load(wallpaper.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(binding.wallpaperImageView)

            binding.root.setOnClickListener {
                // Обработка клика на элементе списка (например, открытие изображения в большем размере)
            }

            binding.saveButton.setOnClickListener {
                saveImageToGallery(binding.root.context, wallpaper.imageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val binding = ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun saveImageToGallery(context: Context, imageUrl: String) {
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .target(
                onSuccess = { result ->
                    val savedUri = Uri.parse(
                        MediaStore.Images.Media.insertImage(
                            context.contentResolver, result, null, null
                        )
                    )
                    if (savedUri != null) {
                        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        val request = DownloadManager.Request(savedUri)
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        downloadManager.enqueue(request)
                    }
                },
                onError = { error ->
                    Toast.makeText(
                        context,
                        "Error saving image: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            .build()
        val imageLoader = ImageLoader.Builder(context).build()
        imageLoader.enqueue(request)
    }
}
