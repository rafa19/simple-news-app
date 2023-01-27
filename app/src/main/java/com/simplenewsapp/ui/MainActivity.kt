package com.simplenewsapp.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simplenewsapp.R
import com.simplenewsapp.data.model.Article
import com.simplenewsapp.data.network.Resource
import com.simplenewsapp.data.network.Status
import com.simplenewsapp.databinding.ActivityMainBinding
import com.simplenewsapp.ui.custom.ProgressDialog
import com.simplenewsapp.utils.EndlessRecyclerViewScrollListener
import com.simplenewsapp.utils.ErrorHandler
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var newsPage = 1
    private var newsAdapter = NewsAdapter(arrayListOf())
    lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mProgressDialog = ProgressDialog(this)
        setUpRecyclerView()


        mainViewModel.newsList.observe(this, ::consumeNewsListResult)
        mainViewModel.headline.observe(this, ::consumeHeadlinesResult)
        mainViewModel.getNewsList(newsPage)
        mainViewModel.getHeadlines()

    }

    private fun setUpRecyclerView() {
        val lManager = LinearLayoutManager(this)
        scrollListener = object : EndlessRecyclerViewScrollListener(1, lManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                newsPage++
                mainViewModel.getNewsList(newsPage)
            }
        }
        binding.recyclerNews.apply {
            layoutManager = lManager
            addOnScrollListener(scrollListener)
            adapter = newsAdapter
        }

        newsAdapter.onItemClick = {
            startActivity(
                Intent(this, WebViewActivity::class.java)
                    .putExtra("articleUrl", it.url)
            )
        }
    }

    private fun hideLoading() {
        mProgressDialog.cancel()
    }

    private fun showLoading() {
        mProgressDialog.show()
    }

    private fun bindHeadline(headline: Article?) {
        Glide.with(this)
            .load(headline?.urlToImage)
            .placeholder(R.drawable.ic_placeholder)
            .into(binding.imgHeadline)
        binding.txtTitle.text = headline?.title
        binding.txtDate.text = headline?.publishedAt
        binding.rltHeadlineContainer.setOnClickListener {
            startActivity(
                Intent(this, WebViewActivity::class.java)
                    .putExtra("articleUrl", headline?.url)
            )
        }
    }


    private fun consumeNewsListResult(resource: Resource<List<Article>>) {
        when (resource.status) {
            Status.LOADING -> showLoading()
            Status.ERROR -> {
                hideLoading()
                ErrorHandler(this).handleError(resource.throwable)
            }
            Status.SUCCESS -> {
                hideLoading()
                newsAdapter.addList(resource.data.orEmpty())
            }
        }
    }


    private fun consumeHeadlinesResult(resource: Resource<Article>) {
        when (resource.status) {
            Status.LOADING -> showLoading()
            Status.ERROR -> {
                hideLoading()
                ErrorHandler(this).handleError(resource.throwable)
            }
            Status.SUCCESS -> {
                hideLoading()
                bindHeadline(resource.data)
            }
        }
    }


}