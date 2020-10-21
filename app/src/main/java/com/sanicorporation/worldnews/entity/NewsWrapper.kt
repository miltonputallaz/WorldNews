package com.sanicorporation.worldnews.entity

data class NewsWrapper(val status: String, val totalResults: Int, val articles: List<Article>)