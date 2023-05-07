package com.example.unsplashdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unsplashdemo.R
import com.example.unsplashdemo.UserDiffUtil
import com.example.unsplashdemo.databinding.ItemUnsplashImageBinding
import com.example.unsplashdemo.model.ImageItem

class DiffUtilListAdapter() : ListAdapter<ImageItem, DiffUtilListAdapter.ViewHolder>(UserDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemUnsplashImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.setData(user)
    }

    class ViewHolder(private val binding: ItemUnsplashImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: ImageItem) {
            binding.apply {
                Glide.with(itemView.context).load(item.urls.regular)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.imgImageView)
            }
        }
        }
}