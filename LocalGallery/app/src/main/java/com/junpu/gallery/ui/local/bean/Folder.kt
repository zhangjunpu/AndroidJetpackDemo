package com.junpu.gallery.ui.local.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 本地文件夹
 * @author junpu
 * @date 2020/7/10
 */
@Parcelize
data class Folder(
    val name: String? = null, // 文件夹名称
    val path: String? = null, // 文件夹路径
    val type: String? = null, // 文件及类型（图片、视频、音乐）
    val cover: String? = null, // 文件夹封面
    val list: ArrayList<LocalFile>? = null // 文件夹内文件
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Folder

        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        return path?.hashCode() ?: 0
    }
}