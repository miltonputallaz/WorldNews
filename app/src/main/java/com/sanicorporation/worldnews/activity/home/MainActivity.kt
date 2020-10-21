package com.sanicorporation.worldnews.activity.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanicorporation.worldnews.R
import com.sanicorporation.worldnews.network.NetworkStatus
import com.sanicorporation.worldnews.adapter.NewsAdapter
import com.sanicorporation.worldnews.databinding.ActivityMainBinding
import com.sanicorporation.worldnews.entity.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        configureBinding()
        setObservers()
        setRecycler()

    }

    private fun setRecycler() {
        val viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var adapter = NewsAdapter(listOf())
        news_recycler.layoutManager = viewManager
        news_recycler.adapter = adapter
    }

    private fun configureBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setObservers() {
        viewModel.getNews().observe(this){
            reactToResponse(it)
        }
    }

    private fun reactToResponse(it: NetworkStatus<Article>) {
        Log.d("CAMBIOS",it.toString());
        when(it){
            is NetworkStatus.Loading -> showLoading(true)
            is NetworkStatus.Success -> {
                showLoading(false)
                var adpter = news_recycler.adapter as NewsAdapter
                adpter.setData(it.articles)
                news_recycler.visibility = View.VISIBLE
            }
            is NetworkStatus.Error<*> -> {
                error_text.visibility = View.VISIBLE
                error_text.text = it.message
            }
        }
    }


    private fun showLoading(show: Boolean){
        progress.visibility = if (show) View.VISIBLE else View.GONE
    }
}