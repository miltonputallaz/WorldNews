package com.sanicorporation.worldnews.activity.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sanicorporation.worldnews.network.NetworkStatus
import com.sanicorporation.worldnews.entity.Article
import com.sanicorporation.worldnews.repository.NewsRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


internal class MainViewModelTest{
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private var repository: NewsRepository = mockk(relaxUnitFun = true)

    private val newsListener: Observer<NetworkStatus<Article>> = mockk(relaxUnitFun = true)

    private val networkSuccess = mockk<NetworkStatus.Success<Article>>(relaxUnitFun = true)

    private val networkError = mockk<NetworkStatus.Error<Article>>(relaxUnitFun = true)

    private lateinit var mainViewModel: MainViewModel

    private val successLiveData = MutableLiveData(networkSuccess)

    private val errorLiveData = MutableLiveData(networkError)

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mainViewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `show loading on get news correctly`() = runBlocking{
        coEvery { repository.getLastNews("us") } returns successLiveData as LiveData<NetworkStatus<Article>>
        mainViewModel.getNews().observeForever(newsListener)
        verifyOrder {
            newsListener.onChanged(NetworkStatus.Loading)
            newsListener.onChanged(networkSuccess)
        }
    }

    @Test
    fun `show loading on get news wrong`() = runBlocking{
        coEvery { repository.getLastNews("us") } returns errorLiveData as LiveData<NetworkStatus<Article>>
        mainViewModel.getNews().observeForever(newsListener)
        verifyOrder {
            newsListener.onChanged(NetworkStatus.Loading)
            newsListener.onChanged(networkError)
        }
    }
}