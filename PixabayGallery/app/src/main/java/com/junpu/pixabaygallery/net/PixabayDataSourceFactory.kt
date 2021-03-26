package com.junpu.pixabaygallery.net

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.junpu.pixabaygallery.bean.ImageBean

/**
 *
 * @author junpu
 * @date 2020/7/31
 */
class PixabayDataSourceFactory(private val networkStats: MutableLiveData<LoadStatus>) :
    DataSource.Factory<Int, ImageBean>() {

    val sourceLiveData = MutableLiveData<PixabayDataSource>()

    override fun create(): DataSource<Int, ImageBean> {
        val dataSource = PixabayDataSource(networkStats)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}