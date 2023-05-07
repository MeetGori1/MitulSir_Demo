package com.example.unsplashdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unsplashdemo.R
import com.example.unsplashdemo.databinding.ItemUnsplashImageBinding
import com.example.unsplashdemo.model.ImageItem

class DiffUtilAdapter()  : RecyclerView.Adapter<DiffUtilAdapter.ViewHolder>(){
    private lateinit var binding: ItemUnsplashImageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiffUtilAdapter.ViewHolder {
        binding= ItemUnsplashImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: DiffUtilAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount()=differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun setData(item : ImageItem){
            binding.apply {
                Glide.with(itemView.context).load(item.urls.regular)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.imgImageView)
            }
        }

    }
    private val differCallback = object : DiffUtil.ItemCallback<ImageItem>(){
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return  oldItem.urls == newItem.urls
        }


        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem== newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

}