package com.sanicorporation.worldnews.activity.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sanicorporation.worldnews.network.NetworkStatus
import com.sanicorporation.worldnews.entity.Article
import com.sanicorporation.worldnews.repository.NewsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainViewModel @ViewModelInject constructor(
    val newsRepository: NewsRepository
): ViewModel() {

    private val lastNews:  LiveData<out NetworkStatus<Article>> = liveData(Main) {
        emit(NetworkStatus.Loading)
        emitSource(getNewsFromRepository())
    }

    private suspend fun getNewsFromRepository() = liveData(IO){
        val data = newsRepository.getLastNews("us")
        emitSource(data)
    }



    fun getNews(): LiveData<out NetworkStatus<Article>> = lastNews

}