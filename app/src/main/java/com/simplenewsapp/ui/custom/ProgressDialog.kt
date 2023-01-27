package com.simplenewsapp.ui.custom

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.simplenewsapp.R


class ProgressDialog(context: Context) : Dialog(context) {
    init {
        @SuppressLint("InflateParams")
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        setContentView(inflate)
        setCancelable(false)
        window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
    }
}