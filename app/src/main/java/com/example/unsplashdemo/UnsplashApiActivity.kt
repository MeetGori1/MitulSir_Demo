package com.example.unsplashdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashdemo.adapter.DiffUtilListAdapter
import com.example.unsplashdemo.databinding.ActivityUnsplashApiBinding
import com.example.unsplashdemo.model.ImageItem
import com.example.unsplashdemo.retrofit.retrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UnsplashApiActivity : AppCompatActivity() {
    lateinit var binding: ActivityUnsplashApiBinding
    lateinit var adapter: DiffUtilListAdapter
    lateinit var layoutManager: GridLayoutManager
    var list = arrayListOf<ImageItem>()

    var page = 1
    var perPage = 28
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    var isScrolling: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUnsplashApiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        binding.progressbar.visibility = View.VISIBLE
        setContentView(binding.root)

        layoutManager = GridLayoutManager(this, 4)
        binding.rwRecyclerView.layoutManager = layoutManager

        adapter = DiffUtilListAdapter()
        binding.rwRecyclerView.adapter = adapter

        getMyImage(1)



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
                        getMyImage(2)
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

    private fun getMyImage(num: Int) {

        isLoading = true
        retrofitBuilder.api.getData(page, perPage)
            .enqueue(object : Callback<List<ImageItem>?> {
                override fun onResponse(
                    call: Call<List<ImageItem>?>,
                    response: Response<List<ImageItem>?>,
                ) {
                    isLoading = false
                    if (response.body().isNullOrEmpty()) {
                        Toast.makeText(this@UnsplashApiActivity,
                            "Api Limit is Crossed ",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        val responseBody = response.body()!!

//                        if (num == 1) {
                            list.addAll(responseBody)
//                        } else {
//                            list.addAll(responseBody)
//
//                        }

                        adapter.submitList(list)
                        binding.progressbar.visibility = View.INVISIBLE
                        if (responseBody.size > 0) {
                            isLastPage = responseBody.size < perPage
                        } else {
                            isLastPage = true
                        }
                        isLoading = false
                    }
//                list.clear()
//                adapter.submitList(adapter.currentList.reverse())


//                adapter.setItems(list)
//                if (num == 1) {
//                    adapter.setItems(list)
//                } else {
//                    adapter.upDateItem(list)
//
////                    binding.rwRecyclerView.scrollToPosition(list.size/2)
//                }


                }

                override fun onFailure(call: Call<List<ImageItem>?>, t: Throwable) {
                    binding.progressbar.visibility = View.INVISIBLE
                    Log.e("failed", "Api Call Failed")
                }
            })
    }


//    private fun getMySearchedImage() {
//        isLoading = true
//
//        val logging = HttpLoggingInterceptor()
//
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        val httpClient = OkHttpClient.Builder()
//
//        httpClient.addInterceptor(logging)
//
//        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://api.unsplash.com").client(httpClient.build()).build()
//            .create(UnsplashApiInterFace::class.java)
//
//        val retrofitdata = retrofitBuilder.getSearchData("kitty")
//        Log.e("url",retrofitdata.toString())
//
//        retrofitdata.enqueue(object : Callback<List<SearchItem>?> {
//            override fun onResponse(
//                call: Call<List<SearchItem>?>,
//                response: Response<List<SearchItem>?>
//            ) {
//                val responseBody = response.body()!!
//                adapter = UnsplashApiCallAdapter(responseBody)
//                adapter.notifyDataSetChanged()
//                binding.rwRecyclerView.adapter = adapter
//                binding.progressbar.visibility = View.GONE
//            }
//
//            override fun onFailure(call: Call<List<SearchItem>?>, t: Throwable) {
//
//            }
//
//        })
//    }
}