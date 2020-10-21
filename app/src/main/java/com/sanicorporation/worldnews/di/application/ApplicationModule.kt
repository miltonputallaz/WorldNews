package com.sanicorporation.worldnews.di.application

import com.sanicorporation.worldnews.webservices.BaseInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module()
@InstallIn(ApplicationComponent::class)
object ApplicationModule{

    @Singleton
    @Provides
    fun getOkHttp(): OkHttpClient =OkHttpClient()
        .newBuilder()
        //httpLogging interceptor for logging network requests
        .addInterceptor(BaseInterceptor())
        .build();

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient):Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



}