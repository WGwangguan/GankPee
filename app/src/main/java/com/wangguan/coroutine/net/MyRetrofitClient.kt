package com.wangguan.coroutine.net

import com.wangguan.coroutine.base.BaseRetrofitClient
import okhttp3.OkHttpClient

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
object MyRetrofitClient : BaseRetrofitClient() {
    val service by lazy {
        getService(GankApiService::class.java, GankApiService.BASE_URL)
    }

    override fun handleBuilder(builder: OkHttpClient.Builder) {

    }
}