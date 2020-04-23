package com.wangguan.coroutine.net

/**
 * Created by WG on 2020-04-21.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc: 标识网络流转状态
 */
sealed class RequestState {
    data class LOADING(val type: LoadingType = LoadingType.REPLACE) : RequestState()
    object SUCCESS : RequestState()
    data class EXCEPTION(val e: Exception) : RequestState()

}