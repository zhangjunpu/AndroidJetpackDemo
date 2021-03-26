package com.junpu.pixabaygallery.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.junpu.pixabaygallery.net.LoadStatus
import com.junpu.pixabaygallery.net.PixabayDataSourceFactory

/**
 * MainViewModel
 * @author junpu
 * @date 2020/7/31
 */
class MainViewModel : ViewModel() {

    val loadStatus = MutableLiveData<LoadStatus>()
    private val factory = PixabayDataSourceFactory(loadStatus)
    val imageIndex by lazy { MutableLiveData(0) }
    val imageList by lazy { factory.toLiveData(1) }

    fun invalidateDataSource() = imageList.value?.dataSource?.invalidate()

    fun retry() {
        factory.sourceLiveData.value?.retry?.invoke()
    }
}