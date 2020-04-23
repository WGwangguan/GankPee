package com.wangguan.coroutine.net

import com.wangguan.coroutine.base.BaseViewModel
import kotlinx.coroutines.coroutineScope

/**
 * Created by WG on 2020-04-22.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class ReqStatusHelper(private val vm: BaseViewModel? = null) {

    suspend fun <T> requestSuspend(
        showLoading: Boolean = true,
        niceTips: Boolean = true,
        call: suspend () -> GankResponse<T>
    ): GankResult<T> {
        return coroutineScope {
            if (showLoading) {
                vm?.requestState?.value = RequestState.LOADING()
            }
            try {
                val response = call()
                when (response.status) {
                    100 -> {
                        vm?.requestState?.value = RequestState.SUCCESS
                        GankResult.SUCCESS(response.data)
                    }
                    else -> {
                        throw GankException(response.status, response.msg)
                    }
                }
            } catch (e: Exception) {
                vm?.requestState?.value = RequestState.EXCEPTION(e)
                if (niceTips) vm?.errorTips?.value = e.message
                GankResult.ERROR(e)
            }
        }
    }

    suspend fun <T> requestPageSuspend(
        showLoading: Boolean = true,
        niceTips: Boolean = true,
        call: suspend () -> GankPageResponse<T>
    ): GankResult<GankPage<T>> {
        return coroutineScope {
            if (showLoading) {
                vm?.requestState?.value = RequestState.LOADING()
            }
            try {
                val response = call()
                when (response.status) {
                    100 -> {
                        vm?.requestState?.value = RequestState.SUCCESS
                        val page = GankPage(
                            PageBean(
                                response.page,
                                response.pageCount,
                                response.totalCounts
                            ), response.data
                        )
                        GankResult.SUCCESS(page)
                    }
                    else -> {
                        throw GankException(response.status, response.msg)
                    }
                }
            } catch (e: Exception) {
                vm?.requestState?.value = RequestState.EXCEPTION(e)
                if (niceTips) vm?.errorTips?.value = e.message
                GankResult.ERROR(e)
            }
        }
    }

}