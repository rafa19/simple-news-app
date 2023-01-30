package com.simplenewsapp.data.network

data class ApiResponse<out T>(val status: Status, val data: T?, val throwable: Throwable?) {


    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(throwable: Throwable): ApiResponse<T> {
            return ApiResponse(Status.ERROR, null, throwable)
        }

        fun <T> loading(data: T? = null): ApiResponse<T> {
            return ApiResponse(Status.LOADING, data, null)
        }
    }
}

