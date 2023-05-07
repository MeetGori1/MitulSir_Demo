package com.example.unsplashdemo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unsplashdemo.R
import com.example.unsplashdemo.UserDiffUtil
import com.example.unsplashdemo.databinding.ItemUnsplashSearchImageBinding
import com.example.unsplashdemo.model.ImageItem

class SearchDiffUtilListAdapter() :
    ListAdapter<ImageItem, SearchDiffUtilListAdapter.ViewHolder>(UserDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding =
            ItemUnsplashSearchImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.setData(item, position)
    }

    class ViewHolder(private val binding: ItemUnsplashSearchImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(item: ImageItem, position: Int) {
            Glide.with(itemView.context).load(item.urls.regular)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imgImageView)

        }
    }
}