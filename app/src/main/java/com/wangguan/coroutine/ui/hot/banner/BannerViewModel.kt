package com.wangguan.coroutine.ui.hot.banner

import androidx.lifecycle.MutableLiveData
import com.wangguan.coroutine.base.BaseViewModel
import com.wangguan.coroutine.checkSuccess
import com.wangguan.coroutine.net.Banner

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class BannerViewModel : BaseViewModel() {
    val banners by lazy { MutableLiveData<List<Banner>>() }
    private val repository by lazy { BannerRepository() }

    fun loadBanners() {
        launchOnUI {
            reqHelper.requestSuspend { repository.loadBanners() }
                .checkSuccess { banners.value = it }
        }
    }
}