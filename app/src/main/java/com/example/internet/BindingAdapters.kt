package com.example.internet

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.Locale

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(it)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("priceText")
fun bindPrice(textView: TextView, price: Double) {
    // Menampilkan format harga $99.99 secara profesional
    textView.text = String.format(Locale.US, "$%.2f", price)
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: ProductApiStatus?) {
    when (status) {
        ProductApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_loading)
        }
        ProductApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}
