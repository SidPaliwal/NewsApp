package com.example.newsretro

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org"
const val API_KEY = "f7c908f26a5e45b1951a0ce0caf33dd2"

interface NewsInterface{
    @GET("/v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country")country:String,@Query("category")category:String,@Query("page")page:Int): Call<News2>
}

object NewsService {
    val newsinstance:NewsInterface
    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsinstance = retrofit.create(NewsInterface::class.java)
    }
}