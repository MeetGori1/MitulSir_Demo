package com.example.unsplashdemo.retrofit

import com.example.unsplashdemo.Utils.Constants
import com.example.unsplashdemo.model.ImageItem
import com.example.unsplashdemo.model.SearchItem
import com.example.unsplashdemo.Utils.Constants.END_POINT
import com.example.unsplashdemo.Utils.Constants.END_POINT_SEARCH
import com.example.unsplashdemo.Utils.Constants.END_POINT_WEather
import com.example.unsplashdemo.model.WeatherDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService1 {
    @Headers("${Constants.ACCEPT_VERSION}: ${Constants.VERSION}",
        "${Constants.AUTHORIZATION} ${Constants.CLIENT_ID}")
    @GET(END_POINT)
    fun getData(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
    ): Call<List<ImageItem>>

    @Headers("${Constants.ACCEPT_VERSION}: ${Constants.VERSION}",
        "${Constants.AUTHORIZATION} ${Constants.CLIENT_ID}")
    @GET(END_POINT_SEARCH)
    fun getSearchData(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("query") query: String,
    ): Call<SearchItem>


    @GET(END_POINT_WEather)
    fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
    ): Call<WeatherDataClass>
}