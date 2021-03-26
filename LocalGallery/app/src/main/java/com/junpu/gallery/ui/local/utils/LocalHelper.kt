@file:JvmName("LocalHelper")

package com.junpu.gallery.ui.local.utils

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import com.junpu.gallery.R
import com.junpu.gallery.ui.local.bean.LocalFile
import com.junpu.log.logStackTrace
import com.junpu.utils.getStringResource
import java.text.DecimalFormat

/**
 * 本地工具类
 * @author zhangjunpu
 * @date 16/8/24
 */

const val TYPE_IMAGE = "image/"
const val TYPE_VIDEO = "video/"
const val TYPE_AUDIO = "audio/"

const val SIZE_1_K = 1024
const val SIZE_1_M = SIZE_1_K * 1024
const val SIZE_1_G = SIZE_1_M * 1024

const val TIME_1_S = 1000
const val TIME_1_M = TIME_1_S * 60
const val TIME_1_H = TIME_1_M * 60

/**
 * 如果为图片
 */
fun isImage(type: String?): Boolean {
    return type?.startsWith(TYPE_IMAGE) ?: false
}

/**
 * 如果为视频
 */
fun isVideo(type: String?): Boolean {
    return type?.startsWith(TYPE_VIDEO) ?: false
}

/**
 * 如果为音频
 */
fun isAudio(type: String?): Boolean {
    return type?.startsWith(TYPE_AUDIO) ?: false
}

/**
 * 获取视频缩略图
 */
fun getVideoThumbnail(filePath: String?): Bitmap? {
    var bitmap: Bitmap? = null
    val retriever = MediaMetadataRetriever()
    try {
        retriever.setDataSource(filePath)
        bitmap = retriever.frameAtTime
    } catch (e: RuntimeException) {
        e.logStackTrace()
    } finally {
        try {
            retriever.release()
        } catch (e: RuntimeException) {
            e.logStackTrace()
        }
    }
    return bitmap
}

/**
 * 获取文件大小（byte -> K、M、G）
 */
fun LocalFile.lengthToString(): String? {
    val unit: String
    val length: String
    when {
        size > SIZE_1_G -> {
            unit = "G"
            length = DecimalFormat("0.0").format(size / SIZE_1_G.toDouble())
        }
        size > SIZE_1_M -> {
            length = DecimalFormat("0.0").format(size / SIZE_1_M.toDouble())
            unit = "M"
        }
        else -> {
            length = (size / SIZE_1_K).toString()
            unit = "K"
        }
    }
    return getStringResource(R.string.format_file_size).format(length, unit)
}

fun formatDurationByMills(mills: Long): String {
    val hour: Long
    val minute: Long
    val second: Long
    return when {
        mills >= TIME_1_H -> {
            hour = mills / TIME_1_H
            minute = (mills % TIME_1_H) / TIME_1_M
            second = ((mills % TIME_1_H) % TIME_1_M) / TIME_1_S
            "%02d:%02d:%02d".format(hour, minute, second)
        }
        mills >= TIME_1_M -> {
            minute = mills / TIME_1_M
            second = (mills % TIME_1_M) / TIME_1_S
            "%02d:%02d".format(minute, second)
        }
        else -> {
            minute = 0
            second = mills / TIME_1_S
            "%02d:%02d".format(minute, second)
        }
    }
}

val localTitleResources = { type: String? ->
    when (type) {
        TYPE_IMAGE -> R.string.local_image
        TYPE_VIDEO -> R.string.local_video
        TYPE_AUDIO -> R.string.local_audio
        else -> R.string.app_name
    }
}

val localFileInfo = { localFile: LocalFile ->
    var result = "资源id: ${localFile.id}\n" +
            "文件名: ${localFile.name}\n" +
            "路径: ${localFile.path}\n" +
            "文件类型: ${localFile.mimeType}\n" +
            "大小: ${localFile.lengthToString()}\n"
    result += if (localFile.duration > 0) "时长: ${formatDurationByMills(localFile.duration)}\n" else ""
    result += if (localFile.artist.isNullOrBlank()) "" else "歌手: ${localFile.artist}\n"
    result += if (localFile.album.isNullOrBlank()) "" else "专辑: ${localFile.album}\n"
    result += if (localFile.title.isNullOrBlank()) "" else "标题: ${localFile.title}\n"
    result
}