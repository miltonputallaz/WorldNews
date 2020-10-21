package com.sanicorporation.worldnews.di.home


import com.sanicorporation.worldnews.webservices.ArticlesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object HomeModule{

    @Provides
    fun getArticleServices(retrofit: Retrofit): ArticlesService = retrofit.create(ArticlesService::class.java)


}