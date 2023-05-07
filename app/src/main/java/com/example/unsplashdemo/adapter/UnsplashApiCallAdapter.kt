package com.example.unsplashdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unsplashdemo.R
import com.example.unsplashdemo.databinding.ItemUnsplashImageBinding
import com.example.unsplashdemo.model.ImageItem
import com.example.unsplashdemo.model.SearchItem

class UnsplashApiCallAdapter() :
    RecyclerView.Adapter<UnsplashApiCallAdapter.MyViewHolder>() {
    lateinit var list: ArrayList<ImageItem>
    fun setItems(dataList: List<SearchItem>) {
//        list = dataList
        notifyDataSetChanged()
    }

    fun upDateItem(dataList: ArrayList<ImageItem>) {
        list.addAll(dataList)
        notifyDataSetChanged()
//        for (i in 0..dataList.size){
//            this.list.add(dataList[i])
//        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): MyViewHolder {
        val binding =
            ItemUnsplashImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(val binding: ItemUnsplashImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(bean: ImageItem) {

            Glide.with(itemView.context).load(bean.urls.regular)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_launcher_background)
                .into(binding.imgImageView)

        }
    }
}