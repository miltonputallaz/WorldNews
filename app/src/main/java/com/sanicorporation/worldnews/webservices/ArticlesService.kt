package com.sanicorporation.worldnews.webservices

import com.sanicorporation.worldnews.entity.Article
import com.sanicorporation.worldnews.entity.NewsWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ArticlesService {
    @GET("v2/top-headlines")
    fun getTopHeadlines(@Query("country") country: String): Call<NewsWrapper>
}