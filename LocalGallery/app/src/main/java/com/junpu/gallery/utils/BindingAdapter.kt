package com.junpu.gallery.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 *
 * @author junpu
 * @date 2020/7/15
 */
object BindingAdapter {

    @BindingAdapter(value = ["imageUrl", "placeholder", "error"], requireAll = false)
    @JvmStatic
    fun setImageUrl(view: ImageView, url: String?, placeHolder: Drawable?, error: Drawable?) {
        Glide.with(view)
            .load(url)
            .placeholder(placeHolder)
            .error(error)
            .into(view)
    }
}