package com.simplenewsapp.utils

import android.app.AlertDialog
import android.content.Context

class DialogUtils(val context: Context) {

    fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

}