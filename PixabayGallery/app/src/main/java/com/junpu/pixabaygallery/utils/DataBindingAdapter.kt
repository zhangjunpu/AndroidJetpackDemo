package com.junpu.pixabaygallery.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 *
 * @author junpu
 * @date 2020/7/29
 */
object DataBindingAdapter {

    @BindingAdapter(value = ["imageUrl", "placeholder", "error"], requireAll = false)
    @JvmStatic
    fun loadUrl(imageView: ImageView, url: String?, placeholder: Drawable?, error: Drawable?) {
        Glide.with(imageView)
            .load(url)
            .apply {
                placeholder?.let { placeholder(it) }
                error?.let { error(it) }
            }
            .into(imageView)
    }
}