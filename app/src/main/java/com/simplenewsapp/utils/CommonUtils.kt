package com.simplenewsapp.utils

import java.text.SimpleDateFormat
import java.util.*


open class CommonUtils {

    private val serverFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val localFormat = SimpleDateFormat("MMM dd, yyyy KK:mm a", Locale.getDefault())

    open fun formatDate(stringDate: String?): String {
        try {
            serverFormat.timeZone = TimeZone.getTimeZone("UTC")
            if (stringDate.isNullOrEmpty()) return ""
            val date: Date? = serverFormat.parse(stringDate)
            localFormat.timeZone = TimeZone.getDefault()
            return if (date == null) ""
            else localFormat.format(date).orEmpty()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }
    }
}