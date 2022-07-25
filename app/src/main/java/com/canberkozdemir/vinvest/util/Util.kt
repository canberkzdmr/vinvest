package com.canberkozdemir.vinvest.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.canberkozdemir.vinvest.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeHolderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url, placeHolderProgressBar(view.context))
}

/**
 * This method converts given String to number with 5 decimals.
 */
fun formatTo5Decimals (value: String): String {
    return DecimalFormat("#,###.#####").format(value.toFloat()).toString().replace(",", "")
}

@SuppressLint("SimpleDateFormat")
fun unixTimeStampToDateString(unixDate: String): String {
    return SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Date(unixDate.toLong()))
}