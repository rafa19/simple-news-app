package com.simplenewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simplenewsapp.data.model.Article
import com.simplenewsapp.data.model.ArticleResponse
import com.simplenewsapp.data.network.*
import com.simplenewsapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val commonDao: NewsDao) : ViewModel() {

    private var newsResponse: ArticleResponse? = null
    private val _newsList: MutableLiveData<ApiResponse<List<Article>>> = MutableLiveData()
    val newsList: LiveData<ApiResponse<List<Article>>> = _newsList

    private val _headline: MutableLiveData<ApiResponse<Article>> = MutableLiveData()
    val headline: LiveData<ApiResponse<Article>> = _headline

    fun getNewsList(newsPage: Int) {
        viewModelScope.launch {
            _newsList.value = ApiResponse.loading()
            _newsList.value = handleNewsListResponse(commonDao.getNewsList(newsPage, Constants.PAGE_SIZE))
        }
    }

    private fun handleNewsListResponse(response: ApiResponse<ArticleResponse>): ApiResponse<List<Article>> {
        if (response.status == Status.SUCCESS) {
            response.data?.let {
                if (newsResponse == null) {
                    newsResponse = it
                } else {
                    val oldNews = newsResponse?.articles
                    val newNews = it.articles
                    oldNews?.addAll(newNews.orEmpty())
                }
                return ApiResponse.success(it.articles.orEmpty())
            }
        }
        return ApiResponse.error(response.throwable!!)
    }

    fun getHeadlines() {
        viewModelScope.launch {
            _headline.value = ApiResponse.loading()
            //we could get only one news and show it,
            //but for demonstration purpose just getting 10 news and later will sort
            _headline.value =
                handleHeadlineResponse(commonDao.getTopHeadlines(1, Constants.PAGE_SIZE))
        }
    }

    private fun handleHeadlineResponse(response: ApiResponse<ArticleResponse>): ApiResponse<Article> {
        if (response.status == Status.SUCCESS) {
            response.data?.let { it ->
                if (!it.articles.isNullOrEmpty()) {
                    //sorting list to get the latest headline
                    val sortedList =
                        it.articles?.sortedByDescending { article -> article._publishedAt }
                    return ApiResponse.success(sortedList?.get(0)!!)
                }
            }
        }
        return ApiResponse.error(response.throwable!!)
    }

}