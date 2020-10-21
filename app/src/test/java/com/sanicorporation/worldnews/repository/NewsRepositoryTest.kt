package com.sanicorporation.worldnews.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sanicorporation.worldnews.network.NetworkStatus
import com.sanicorporation.worldnews.entity.Article
import com.sanicorporation.worldnews.entity.NewsWrapper
import com.sanicorporation.worldnews.webservices.ArticlesService
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Call
import retrofit2.Response

internal class  NewsRepositoryTest{
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private var webService: ArticlesService = mockk(relaxUnitFun = true)

    private val newsObserver: Observer<NetworkStatus<Article>> = mockk(relaxUnitFun = true)

    private val wrapper: NewsWrapper = mockk(relaxUnitFun = true, relaxed = true)

    private val responseBody: ResponseBody = mockk(relaxUnitFun = true, relaxed = true)

    private val networkSuccess = mockk<Call<NewsWrapper>>(relaxUnitFun = true){
        every {execute()} returns Response.success(wrapper)
    }


    private val networkError = mockk<Call<NewsWrapper>>(relaxUnitFun = true){
        every {execute()} returns Response.error(400, responseBody )
    }

    private lateinit var newsRepository: NewsRepository


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        newsRepository = NewsRepository(webService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @Test
    fun `return correct value on get data successfully`() = runBlocking{
        coEvery { webService.getTopHeadlines("us") } returns networkSuccess
        newsRepository.getLastNews("us").observeForever(newsObserver)
        verify(exactly = 1) { newsObserver.onChanged(NetworkStatus.Success(wrapper.status, wrapper.articles.size,wrapper.articles))}
    }

    @Test
    fun `return correct value on get data not successfully`() = runBlocking{
        coEvery { webService.getTopHeadlines("us") } returns networkError
        newsRepository.getLastNews("us").observeForever(newsObserver)
        verify(exactly = 1) { newsObserver.onChanged(NetworkStatus.Error<Article>("Error", any(), any()))}
    }
}