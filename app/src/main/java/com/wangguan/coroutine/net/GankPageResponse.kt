package com.wangguan.coroutine.net

import com.google.gson.annotations.SerializedName

/**
 * Created by WG on 2020-04-20.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class GankPageResponse<T> : GankResponse<T>() {
    var page: Int = -1
    @SerializedName("page_count")
    var pageCount: Int = -1
    @SerializedName("total_counts")
    var totalCounts: Int = -1
}