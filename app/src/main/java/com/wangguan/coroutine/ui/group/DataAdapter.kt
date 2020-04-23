package com.wangguan.coroutine.ui.group

import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.R
import com.wangguan.coroutine.dp2px
import com.wangguan.coroutine.fillVisible
import com.wangguan.coroutine.net.GankBean
import kotlinx.android.synthetic.main.adapter_item_data.view.*

/**
 * Created by WG on 2020-04-21.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class DataAdapter(val category: String) :
    BaseQuickAdapter<GankBean, DataAdapter.DataHolder>(R.layout.adapter_item_data, null),
    LoadMoreModule {

    override fun convert(holder: DataHolder, item: GankBean) {
        item.also {
            holder.title.text = it.title
            holder.tag.text = it.type
            holder.tag.fillVisible(false)
            if (category == Constants.CATEGORY_GIRL) {
                holder.image.layoutParams.height = context.dp2px(350)
            }
            Glide.with(context).load(it.images.getOrNull(0).orEmpty()).into(holder.image)
        }
    }

    class DataHolder(itemView: View) : BaseViewHolder(itemView) {
        val title = itemView.text_title
        val tag = itemView.text_tag
        val image = itemView.image
    }
}