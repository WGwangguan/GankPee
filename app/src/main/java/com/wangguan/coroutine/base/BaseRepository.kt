package com.wangguan.coroutine.base

import com.wangguan.coroutine.net.GankPageResponse
import com.wangguan.coroutine.net.GankResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
open class BaseRepository {
    suspend fun <T : Any> call(call: suspend () -> GankResponse<T>): GankResponse<T> {
        return withContext(IO) {
            call()
        }
    }

    suspend fun <T : Any> pageCall(call: suspend () -> GankPageResponse<T>): GankPageResponse<T> {
        return withContext(IO) {
            call()
        }
    }
}