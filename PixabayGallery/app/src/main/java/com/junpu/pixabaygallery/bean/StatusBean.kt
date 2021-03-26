package com.junpu.pixabaygallery.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author junpu
 * @date 2020/7/29
 */
data class StatusBean(
    var total: Int,
    var totalHits: Int,
    var hits: List<ImageBean>?
)

@Parcelize
data class ImageBean(
    var id: Int,
    var pageURL: String?,
    var type: String?,
    var tags: String?,
    var previewURL: String?,
    var previewWidth: Int,
    var previewHeight: Int,
    var webformatURL: String?,
    var webformatWidth: Int,
    var webformatHeight: Int,
    var largeImageURL: String?,
    var imageWidth: Int,
    var imageHeight: Int,
    var imageSize: Int,
    var views: Int,
    var downloads: Int,
    var favorites: Int,
    var likes: Int,
    var comments: Int,
    var user_id: Int,
    var user: String?,
    var userImageURL: String?
) : Parcelable