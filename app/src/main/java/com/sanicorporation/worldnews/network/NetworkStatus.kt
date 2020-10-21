package com.sanicorporation.worldnews.network

import com.sanicorporation.worldnews.entity.Article

sealed class NetworkStatus<out d: Any>  {
    data class Success<out d: Any>(val status: String, val totalResults: Int, val articles: List<Article>): NetworkStatus<d>()
    data class Error<out d: Any>(val status: String, val code: String, val message: String): NetworkStatus<Nothing>()
    object Loading : NetworkStatus<Nothing>()
}