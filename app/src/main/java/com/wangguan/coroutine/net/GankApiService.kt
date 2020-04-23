package com.wangguan.coroutine.net

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
interface GankApiService {

    /**
     * 加载 banner
     */
    @GET("banners")
    suspend fun banners(): GankResponse<List<Banner>>

    /**
     * 加载热门
     */
    @GET("hot/{type}/category/{category}/count/{count}")
    suspend fun loadHot(
        @Path("type") type: String,
        @Path("category") category: String,
        @Path("count") count: Int
    ): GankResponse<List<GankBean>>

    /**
     * 获取分类列表
     */
    @GET("categories/{category}")
    suspend fun loadSubCategory(@Path("category") category: String): GankResponse<List<SubCategory>>

    @GET("data/category/{category}/type/{type}/page/{page}/count/{count}")
    suspend fun loadData(
        @Path("category") category: String,
        @Path("type") type: String,
        @Path("page") page: Int,
        @Path("count") count: Int
    ): GankPageResponse<List<GankBean>>


    @GET("post/{post_id}")
    suspend fun loadDetail(@Path("post_id") postId: String): GankResponse<Nothing>

    companion object {
        const val BASE_URL = "https://gank.io/api/v2/"
    }
}