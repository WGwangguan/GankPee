package com.wangguan.coroutine.ui.hot.banner

import com.wangguan.coroutine.base.BaseRepository
import com.wangguan.coroutine.net.Banner
import com.wangguan.coroutine.net.GankResponse
import com.wangguan.coroutine.net.GankResult
import com.wangguan.coroutine.net.MyRetrofitClient

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class BannerRepository : BaseRepository() {
    suspend fun loadBanners(): GankResponse<List<Banner>> {
        return call { MyRetrofitClient.service.banners() }
    }
}