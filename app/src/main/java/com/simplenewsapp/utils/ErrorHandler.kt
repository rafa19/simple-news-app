package com.simplenewsapp.utils

import android.content.Context

class ErrorHandler(private val context: Context) {

    fun handleError(throwable: Throwable?) {
        try {
            var errorMessage: String? = throwable?.message

            if (errorMessage.isNullOrEmpty()) {
                errorMessage = "Error occurred, please try again later"
            }
            DialogUtils(context).showDialog("Attention", errorMessage)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}