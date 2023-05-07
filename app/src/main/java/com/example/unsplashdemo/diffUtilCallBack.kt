package com.example.unsplashdemo

import androidx.recyclerview.widget.DiffUtil
import com.example.unsplashdemo.model.ImageItem
import com.example.unsplashdemo.model.SearchItem


object UserDiffUtil : DiffUtil.ItemCallback<ImageItem>() {
    override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {

        return oldItem.id == newItem.id
    }

}

object UserDiffUtilSearch : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {

        return oldItem.results.size == newItem.results.size
    }

}
