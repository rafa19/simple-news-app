package com.simplenewsapp.data.model

import com.google.gson.annotations.SerializedName
import com.simplenewsapp.utils.CommonUtils
import java.text.SimpleDateFormat
import java.util.*

class ArticleResponse {
    @SerializedName("articles")
    var articles: MutableList<Article>? = null
}

data class Article(
    @SerializedName("source") var source: Source? = Source(),
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("urlToImage") var urlToImage: String? = null,
    @SerializedName("publishedAt") var _publishedAt: String? = null,
    @SerializedName("content") var content: String? = null
) {
    val publishedAt
        get():String? {
            return CommonUtils().formatDate(_publishedAt.orEmpty())
        }
}