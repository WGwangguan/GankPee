package com.wangguan.coroutine.net

/**
 * Created by WG on 2020-04-22.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
sealed class LoadingType {
    object DIALOG : LoadingType()
    object REPLACE : LoadingType()
}