package com.inigofrabasa.hodinkeetest.adapters

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.sql.Date
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@BindingAdapter("timeFormat")
fun bindTimeFormat(textView: TextView, date: String?) {
    try {
        date?.apply {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
            val netDate = sdf.parse(date)
            netDate?.apply {
                val timestamp = netDate.time
                val resultDate = Date(timestamp)
                textView.text = resultDate.toString()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl : String?) {
    imageUrl?.apply {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

