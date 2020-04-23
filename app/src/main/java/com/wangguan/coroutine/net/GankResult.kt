package com.wangguan.coroutine.net

/**
 * Created by WG on 2020-04-20.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
sealed class GankResult<out T> {
    // 成功
    data class SUCCESS<out T>(val data: T?) : GankResult<T>()

    // 失败
    data class ERROR(val e: Exception) : GankResult<Nothing>()
}