package com

import android.app.Application
import com.sanicorporation.worldnews.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import javax.inject.Inject


@HiltAndroidApp
class WorldNewsApp(): Application() {
    lateinit var consumerKey: String

    @Inject lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
    }
}