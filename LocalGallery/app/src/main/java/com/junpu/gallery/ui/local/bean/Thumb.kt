package com.junpu.gallery.ui.local.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 缩略图
 * @author junpu
 * @date 2020/7/10
 */
@Parcelize
data class Thumb(
    var id: Int,
    var path: String?
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Thumb

        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        return path?.hashCode() ?: 0
    }
}