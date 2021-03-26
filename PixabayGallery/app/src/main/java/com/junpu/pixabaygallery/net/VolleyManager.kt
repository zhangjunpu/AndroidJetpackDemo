package com.junpu.pixabaygallery.net

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.junpu.log.L
import com.junpu.log.logStackTrace
import com.junpu.pixabaygallery.App
import com.junpu.pixabaygallery.bean.ImageBean
import com.junpu.pixabaygallery.bean.StatusBean
import kotlin.math.ceil

/**
 * Volley
 * @author junpu
 * @date 2020/7/29
 */
class VolleyManager private constructor(context: Context) {

    companion object {
        private const val BASE_URL = "https://pixabay.com/api/"
        private const val PIXABAY_KEY = "17677122-3230817d1fc1747f3a718c9fa"
        const val PAGE_SIZE = 50
        val instance by lazy { VolleyManager(App.app) }
    }

    var queue: RequestQueue? = null
    val gson by lazy { Gson() }

    init {
        queue = Volley.newRequestQueue(context)
    }

    fun get(
        map: Map<String, String?>? = null,
        callback: (isSuccess: Boolean, result: StatusBean?, e: VolleyError?) -> Unit
    ) {
        val url = newRequestUrl(map)
        L.vv(url)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                val result = gson.fromJson(it, StatusBean::class.java)
                callback(true, result, null)
            },
            {
                it.logStackTrace()
                callback(false, null, it)
            }
        )
        queue?.add(request)
    }

    /**
     * 最大页码
     */
    val maxPages = { total: Int -> ceil(total.toFloat() / PAGE_SIZE).toInt() }

    private fun newRequestUrl(map: Map<String, String?>?): String {
        val key = if (map?.containsKey(API_PAGE) == true && map[API_PAGE] == "1") {
            Category.values().random().english.also { queryKey = it }
        } else {
            queryKey
        }
        L.vv(key)
        val sb = StringBuilder(BASE_URL)
        sb.append("?key=").append(PIXABAY_KEY)
        sb.append("&lang=zh")
        sb.append("&q=${key}")
        sb.append("&per_page=${PAGE_SIZE}")
//        sb.append("&category=nature")
//        sb.append("&min_width=2560")
//        sb.append("&min_height=1440")
        map?.forEach { sb.append("&${it.key}=${it.value}") }
        return sb.toString()
    }

    private var queryKey: String? = null
}