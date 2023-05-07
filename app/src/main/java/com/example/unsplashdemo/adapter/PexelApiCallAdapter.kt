package com.example.unsplashdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashdemo.databinding.ItemImageBinding
import com.example.unsplashdemo.model.paxxelData

class pexelApiCallAdapter(var list: List<paxxelData>) :
    RecyclerView.Adapter<pexelApiCallAdapter.MyViewHolder>() {

    fun setItems(dataList: ArrayList<paxxelData>) {
        this.list = dataList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(val binding: ItemImageBinding, val adapter: pexelApiCallAdapter) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(bean: paxxelData) {

//            Glide.with(itemView.context).load(bean.bank?.image)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.drawable.ic_launcher_background)
//                .into(binding.imgImage)

            binding.txtText.setSelected(true)
            binding.txtText.text = bean.title

//            binding.btnDelete.setOnClickListener {
//                adapter.mClickListener.onViewClick(it.id, layoutPosition, bean)
//                binding.sml.smoothCloseMenu()
//            }
        }
    }
}