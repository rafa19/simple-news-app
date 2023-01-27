package com.simplenewsapp.data.network

import javax.inject.Inject

class CommonDao @Inject constructor(private val commonService: CommonService) : BaseDataSource() {

    suspend fun getTopHeadlines(page: Int, pageSize: Int) = getResult {
        commonService.getTopHeadlines(page, pageSize)
    }

    suspend fun getNewsList(page: Int, pageSize: Int) = getResult {
        commonService.getNewsList(page, pageSize)
    }

}