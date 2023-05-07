package com.example.unsplashdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unsplashdemo.adapter.pexelApiCallAdapter
import com.example.unsplashdemo.databinding.ActivityPexelApiCallBinding
import com.example.unsplashdemo.model.paxxelData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PexelApiCall : AppCompatActivity() {
    lateinit var binding: ActivityPexelApiCallBinding
    lateinit var adapter: pexelApiCallAdapter
    lateinit var layoutmanger: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPexelApiCallBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        layoutmanger = LinearLayoutManager(this)
//        binding.rwRecyclerView.layoutManager = layoutmanger
        getMyImage()
//        binding.btnAdd.set

    }

    private fun getMyImage() {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/").build()
            .create(ApiInterFace::class.java)

        val retrofitdata = retrofitBuilder.getData()

        retrofitdata.enqueue(object : Callback<List<paxxelData>?> {
            override fun onResponse(
                call: Call<List<paxxelData>?>,
                response: Response<List<paxxelData>?>,
            ) {
                val responseBody = response.body()!!
//                val stringBuilder = StringBuilder()
//                for (myData in responseBody) {
//                    stringBuilder.append(myData.title)
//                    stringBuilder.append("\n")
//                }
//                binding.txtTitle.text=stringBuilder
                adapter = pexelApiCallAdapter(responseBody)
                adapter.notifyDataSetChanged()
                binding.rwRecyclerView.adapter = adapter

            }

            override fun onFailure(call: Call<List<paxxelData>?>, t: Throwable) {
                Log.e("failed0", "failed")
            }
        })
    }
}