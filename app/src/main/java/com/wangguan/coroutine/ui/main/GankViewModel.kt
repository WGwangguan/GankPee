package com.wangguan.coroutine.ui.main

import androidx.lifecycle.MutableLiveData
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.base.BaseViewModel
import com.wangguan.coroutine.checkSuccess
import com.wangguan.coroutine.net.GankBean
import com.wangguan.coroutine.net.GankPage
import com.wangguan.coroutine.net.SubCategory

/**
 * Created by WG on 2020-04-20.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class GankViewModel : BaseViewModel() {
    private val repository by lazy { GankRepository() }

    val hotData by lazy { MutableLiveData<List<GankBean>>() }
    val subCategory by lazy { MutableLiveData<List<SubCategory>>() }
    val gankPage by lazy { MutableLiveData<GankPage<List<GankBean>>>() }

    fun loadHot(category: String, showLoading: Boolean = true) {
        launchOnUI {
            reqHelper.requestSuspend(showLoading = showLoading) { repository.loadHot(category) }
                .checkSuccess {
                    hotData.value = it
                }
        }
    }

    fun loadDetail(postId: String) {
        launchOnUI {
            reqHelper.requestSuspend { repository.loadDetail(postId) }.checkSuccess {

            }
        }
    }

    fun loadSubCategory(category: String) {
        launchOnUI {
            reqHelper.requestSuspend { repository.loadSubCategory(category) }
                .checkSuccess { subCategory.value = it }
        }
    }

    fun loadData(
        category: String,
        type: String,
        page: Int,
        count: Int = Constants.DEFAULT_COUNT,
        showLoading: Boolean = true
    ) {
        launchOnUI {
            reqHelper.requestPageSuspend(showLoading = showLoading) {
                repository.loadData(
                    category,
                    type,
                    page,
                    count
                )
            }
                .checkSuccess {
                    it ?: return@checkSuccess
                    gankPage.value = it
                }
        }
    }

}