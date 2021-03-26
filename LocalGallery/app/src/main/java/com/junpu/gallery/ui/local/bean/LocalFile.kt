package com.junpu.gallery.ui.local.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 本地文件
 * @author junpu
 * @date 2020/7/10
 */
@Parcelize
data class LocalFile(
    var id: Long = 0, // 资源id
    var name: String? = null, // 文件名
    var path: String? = null, // 路径
    var mimeType: String? = null, // 文件类型
    var size: Long = 0, // 大小
    var duration: Long = 0, // 时长（视频、音乐）
    var artist: String? = null, // 歌手
    var album: String? = null, // 专辑
    var title: String? = null, // 标题
    var thumb: String? = null, // 缩略图路径
    var uri: String? = null // 网络地址
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocalFile

        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        return path?.hashCode() ?: 0
    }
}