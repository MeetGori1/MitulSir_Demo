package com.example.unsplashdemo

import com.example.unsplashdemo.model.paxxelData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterFace {
    @GET("posts")
    fun getData():Call<List<paxxelData>>
}

//interface UnsplashApiInterFace {
//    @Headers("$ACCEPT_VERSION: $VERSION", "$AUTHORIZATION $CLIENT_ID")
//    @GET(END_POINT)
//    fun getData(
//        @Query("page") page:Int,
//        @Query("per_page") per_page:Int,
//        @Query("query") query:String
//    ):Call<List<ImageItem>>
//
//    @Headers("$ACCEPT_VERSION: $VERSION", "$AUTHORIZATION $CLIENT_ID")
//    @GET("/search/photos")
//    fun getSearchData(
//        @Query("query") query:String
//    ):Call<List<SearchItem>>
//}