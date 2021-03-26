package com.junpu.gallery.ui.local

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.junpu.gallery.App
import com.junpu.gallery.ui.local.bean.Folder
import com.junpu.gallery.ui.local.bean.LocalFile
import com.junpu.gallery.ui.local.utils.TYPE_AUDIO
import com.junpu.gallery.ui.local.utils.TYPE_IMAGE
import com.junpu.gallery.ui.local.utils.TYPE_VIDEO
import java.io.File

/**
 * 本地文件夹
 * @author junpu
 * @date 2020/7/14
 */
class LocalViewModel(app: Application) : AndroidViewModel(app) {

    private val contentResolver by lazy { getApplication<App>().contentResolver }
    val folders by lazy { MutableLiveData<List<Folder>>(arrayListOf()) }
    val type by lazy { MutableLiveData<String>() }

    fun setType(type: String?) {
        this.type.value = type
    }

    fun getFolders() {
        when (type.value) {
            TYPE_IMAGE -> getImageFolders()
            TYPE_VIDEO -> getVideoFolders()
            TYPE_AUDIO -> getAudioFolders()
        }
    }

    private val baseProjection = mutableListOf(
        MediaStore.MediaColumns._ID,
        MediaStore.MediaColumns.DATA,
        MediaStore.MediaColumns.MIME_TYPE,
        MediaStore.MediaColumns.SIZE
    )

    private val videoProjection = mutableListOf(
        MediaStore.MediaColumns.DURATION
    )

    private val audioProjection = mutableListOf(
        MediaStore.MediaColumns.DURATION,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.MediaColumns.DISPLAY_NAME
    )

    /**
     * 获取文件夹列表
     */
    private fun getFolders(projection: Array<String>, uri: Uri, type: String?): ArrayList<Folder> {
        val cursor = contentResolver.query(
            uri,
            projection,
            null,
            null,
            projection[1]
        )
        val folders = arrayListOf<Folder>()
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(projection[0]))
                val path = cursor.getString(cursor.getColumnIndexOrThrow(projection[1]))
                val mimeType = cursor.getString(cursor.getColumnIndexOrThrow(projection[2]))
                val size = cursor.getLong(cursor.getColumnIndexOrThrow(projection[3]))

                val file = File(path)
                if (!file.exists()) continue

                val localFile = LocalFile(
                    id = id,
                    name = file.name,
                    path = path,
                    mimeType = mimeType,
                    size = size
                )

                if (type == TYPE_VIDEO || type == TYPE_AUDIO) {
                    localFile.duration = cursor.getLong(cursor.getColumnIndexOrThrow(projection[4]))
                    if (type == TYPE_AUDIO) {
                        localFile.run {
                            artist = cursor.getString(cursor.getColumnIndexOrThrow(projection[5]))
                            album = cursor.getString(cursor.getColumnIndexOrThrow(projection[6]))
                            title = cursor.getString(cursor.getColumnIndexOrThrow(projection[7]))
                        }
                    }
                }

                folder(file)(folders).list?.add(localFile)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return folders
    }

    /**
     * 获取本地图片列表
     */
    private fun getImageFolders() {
        val projection = baseProjection.toTypedArray()
        val url = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        folders.value = getFolders(projection, url, type.value)
    }

    /**
     * 视频文件夹
     */
    private fun getVideoFolders() {
        val projection = mutableListOf<String>().apply {
            addAll(baseProjection)
            addAll(videoProjection)
        }.toTypedArray()
        val url = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        folders.value = getFolders(projection, url, type.value)
    }

    /**
     * 音频文件夹
     */
    private fun getAudioFolders() {
        val projection = mutableListOf<String>().apply {
            addAll(baseProjection)
            addAll(audioProjection)
        }.toTypedArray()
        val url = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        folders.value = getFolders(projection, url, type.value)
    }

    /**
     * 获取本地缩略图列表
     */
    fun getThumbnail(id: Int): Bitmap = MediaStore.Images.Thumbnails.getThumbnail(
        contentResolver,
        id.toLong(),
        MediaStore.Images.Thumbnails.MINI_KIND,
        null
    )

    private val folder = { file: File ->
        { folders: ArrayList<Folder> ->
            val parent = file.parentFile
            val parentPath = parent?.absolutePath
            folders.firstOrNull { it.path == parentPath } ?: Folder(
                name = parent?.name,
                path = parentPath,
                type = type.value,
                cover = file.path,
                list = arrayListOf()
            ).apply {
                folders.add(this)
            }
        }
    }

}