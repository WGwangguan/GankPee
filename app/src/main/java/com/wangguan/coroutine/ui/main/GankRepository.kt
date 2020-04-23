package com.wangguan.coroutine.ui.main

import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.base.BaseRepository
import com.wangguan.coroutine.net.MyRetrofitClient

/**
 * Created by WG on 2020-04-20.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class GankRepository : BaseRepository() {

    suspend fun loadHot(
        category: String,
        type: String = Constants.HOT_TYPE_VIEWS,
        count: Int = Constants.DEFAULT_COUNT
    ) = call { MyRetrofitClient.service.loadHot(type, category, count) }

    suspend fun loadDetail(postId: String) =
        call { MyRetrofitClient.service.loadDetail(postId) }

    suspend fun loadSubCategory(category: String) =
        call { MyRetrofitClient.service.loadSubCategory(category) }

    suspend fun loadData(
        category: String,
        type: String,
        page: Int,
        count: Int
    ) = pageCall { MyRetrofitClient.service.loadData(category, type, page, count) }

}