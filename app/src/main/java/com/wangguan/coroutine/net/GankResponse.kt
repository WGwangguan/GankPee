package com.wangguan.coroutine.net

/**
 * Created by WG on 2020-04-20.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
open class GankResponse<T> {
    var data: T? = null
    var status: Int? = null // 状态  100 成功 101 失败
    var msg: String? = null // 错误信息
}