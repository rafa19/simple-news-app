package com.simplenewsapp.di.util

import com.simplenewsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Singleton


@Singleton
class AuthenticationInterceptor :
    Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            var request: Request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            builder.addHeader("X-Api-Key", BuildConfig.NEWS_API_KEY)
            request = builder.build()
            chain.proceed(request)
        } catch (ex: Exception) {
            ex.printStackTrace()
            chain.proceed(chain.request())
        }
    }

}