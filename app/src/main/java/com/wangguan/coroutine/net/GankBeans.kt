package com.wangguan.coroutine.net

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */

data class Banner(
    val image: String?,
    val title: String?,
    val url: String?
)

data class GankBean(
    val _id: String,
    val author: String,
    val category: String,
    val createdAt: String,
    val desc: String,
    val images: List<String>,
    val likeCounts: Int,
    val publishedAt: String,
    val stars: Int,
    val title: String,
    val type: String,
    val url: String,
    val views: Int
)

data class SubCategory(
    val _id: String,
    val coverImageUrl: String,
    val desc: String,
    val title: String,
    val type: String
)

data class PageBean(val page: Int, val pageCount: Int, val totalCount: Int)

data class GankPage<T>(val pageBean: PageBean, val data:T?)