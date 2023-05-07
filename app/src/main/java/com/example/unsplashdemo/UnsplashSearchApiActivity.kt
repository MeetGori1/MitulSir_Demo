package com.example.unsplashdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.example.unsplashdemo.adapter.SearchDiffUtilListAdapter
import com.example.unsplashdemo.databinding.ActivityUnsplashSearchApiBinding
import com.example.unsplashdemo.model.ImageItem
import com.example.unsplashdemo.model.SearchItem
import com.example.unsplashdemo.retrofit.retrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UnsplashSearchApiActivity : AppCompatActivity() {
    lateinit var binding: ActivityUnsplashSearchApiBinding
    lateinit var adapter: SearchDiffUtilListAdapter
    lateinit var layoutManager: LinearLayoutManager
    var list = arrayListOf<ImageItem>()

    var page = 1
    var perPage = 10
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    var isScrolling: Boolean = false
    var query: String = "cat"


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUnsplashSearchApiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        binding.progressbar.visibility = View.VISIBLE
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            binding.progressbar.visibility = View.VISIBLE
            page += 1
            list.clear()
            query = binding.searchView.text.toString()
            getMyImage(query)
            adapter.notifyDataSetChanged()
        }

        layoutManager = LinearLayoutManager(this)
        binding.rwRecyclerView.layoutManager = layoutManager

        adapter = SearchDiffUtilListAdapter()
        binding.rwRecyclerView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rwRecyclerView)
        getMyImage(query)


        binding.rwRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var visibleItemCount = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        binding.progressbar.visibility = View.VISIBLE
                        page += 1
                        getMyImage(query)
                    }
                }
            }
        })
    }

//    private fun ApiCall(): Call<List<ImageItem>> {
//        isLoading = true
//
//
//        val logging = HttpLoggingInterceptor()
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(logging)
//
//        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://api.unsplash.com").client(httpClient.build()).build()
//            .create(UnsplashApiInterFace::class.java)
//
//        val retrofitdata = retrofitBuilder.getData(page, perPage, "water")
//        return retrofitdata
//    }


    //    fun getLocation() {
//        var gc = Geocoder(this, Locale.getDefault())
//
//        var address = gc.getFromLocationName(city, 2)
//       latitude = address?.get(0)?.longitude!!
//        longiude = address?.get(0)?.longitude!!
//
////        binding.pollutantCard.param3.text = address?.get(1)?.latitude.toString()
////        binding.pollutantCard.param4.text = address?.get(1)?.longitude.toString()
//
//    }

    private fun getMyImage(query: String) {

        isLoading = true
        retrofitBuilder.api.getSearchData(page, perPage, query)
            .enqueue(object : Callback<SearchItem?> {
                override fun onResponse(
                    call: Call<SearchItem?>, response: Response<SearchItem?>,
                ) {
                    isLoading = false
                    if (response.body() == null) {
                        Toast.makeText(this@UnsplashSearchApiActivity,
                            "Api Limit is Crossed ",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        val responseBody = response.body()!!
                        list.addAll(responseBody.results)
                        adapter.submitList(list)

                        binding.progressbar.visibility = View.INVISIBLE
                        isLoading = false
                        if (responseBody.results.size > 0) {
                            isLastPage = false
                        }

                    }

                }

                override fun onFailure(call: Call<SearchItem?>, t: Throwable) {
                    binding.progressbar.visibility = View.INVISIBLE
                    Log.e("failed", "Api Call Failed")
                }
            })
    }

}