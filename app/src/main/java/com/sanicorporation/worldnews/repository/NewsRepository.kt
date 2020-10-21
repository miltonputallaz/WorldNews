package com.sanicorporation.worldnews.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sanicorporation.worldnews.network.NetworkStatus
import com.sanicorporation.worldnews.entity.Article
import com.sanicorporation.worldnews.entity.NewsWrapper
import com.sanicorporation.worldnews.webservices.ArticlesService
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext
import javax.inject.Inject


class NewsRepository @Inject constructor(
    val articlesService: ArticlesService
) {
    suspend fun getLastNews(country: String): LiveData<NetworkStatus<Article>> {
        val data = MutableLiveData<NetworkStatus<Article>>()
        val response = articlesService.getTopHeadlines(country).execute()

        if (response.isSuccessful){
            val articleWrapper = response.body() as NewsWrapper
            withContext(Main){
                data.postValue(NetworkStatus.Success<Article>(articleWrapper.status, articleWrapper.articles.size,articleWrapper.articles))
            }

        } else {
            withContext(Main){
                data.postValue(NetworkStatus.Error<Article>("Error", response.code().toString(), response.message()))
            }
        }

        return data
    }


}