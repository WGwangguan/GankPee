package com.wangguan.coroutine.ui.hot.banner

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wangguan.coroutine.net.Banner
import com.youth.banner.adapter.BannerAdapter


/**
 * Created by WG on 2020-04-17.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class MyBannerAdapter(private val context: Context, banners: List<Banner>) :
    BannerAdapter<Banner, MyBannerAdapter.BannerViewHolder>(banners) {


    class BannerViewHolder(view: ImageView) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view

    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val imageView = ImageView(context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: Banner?, position: Int, size: Int) {
        holder?.imageView?.also {
            Glide.with(context).load(data?.image.orEmpty()).into(it)
        }
    }
}