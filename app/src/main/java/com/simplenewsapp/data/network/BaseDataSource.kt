package com.simplenewsapp.data.network

import retrofit2.HttpException
import retrofit2.Response


abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResponse<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return ApiResponse.success(body)
            }
            return ApiResponse.error(HttpException(response))
        } catch (e: Exception) {
            return ApiResponse.error(e)
        }
    }


}