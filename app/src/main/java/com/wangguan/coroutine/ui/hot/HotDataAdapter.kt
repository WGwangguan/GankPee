package com.wangguan.coroutine.ui.hot

import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.R
import com.wangguan.coroutine.dp2px
import com.wangguan.coroutine.fillVisible
import com.wangguan.coroutine.net.GankBean
import kotlinx.android.synthetic.main.adapter_item_data.view.*

/**
 * Created by WG on 2020-04-17.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class HotDataAdapter(val category: String) :
    BaseQuickAdapter<GankBean, HotDataAdapter.ViewHolder>(R.layout.adapter_item_data) {

    class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val title = itemView.text_title
        val tag = itemView.text_tag
        val image = itemView.image
    }

    override fun convert(holder: ViewHolder, item: GankBean) {
        holder.title.text = item.title
        holder.tag.text = item.type
        if (category == Constants.CATEGORY_GIRL) {
            holder.tag.fillVisible(false)
            holder.image.layoutParams.height = context.dp2px(350)
        }
        Glide.with(context).load(item.images.getOrNull(0).orEmpty()).into(holder.image)
    }
}