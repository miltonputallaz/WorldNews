package com.sanicorporation.worldnews.webservices

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BaseInterceptor: Interceptor {
    private val KEY_QUERY: String = "apiKey"
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        var url: HttpUrl = request.url().newBuilder().addQueryParameter(KEY_QUERY,com.sanicorporation.worldnews.BuildConfig.NEWS_API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}