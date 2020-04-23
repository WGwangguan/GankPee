package com.wangguan.coroutine.ui.hot

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.Constants.CATEGORY
import com.wangguan.coroutine.Constants.WEB_URL
import com.wangguan.coroutine.R
import com.wangguan.coroutine.base.BaseVmFragment
import com.wangguan.coroutine.net.GankBean
import com.wangguan.coroutine.ui.main.GankViewModel
import com.wangguan.coroutine.ui.web.WebActivity
import kotlinx.android.synthetic.main.frg_hot_data.*

/**
 * Created by WG on 2020-04-17.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class HotDataFragment : BaseVmFragment<GankViewModel>() {

    private val category by lazy { arguments?.getString(CATEGORY).orEmpty() }
    private val articleAdapter by lazy { HotDataAdapter(category) }

    override fun getLayoutId() = R.layout.frg_hot_data

    override fun initView(view: View?) {
        val ctx = activity ?: return

        articleAdapter.setOnItemClickListener { adapter, view, position ->
            if (category == Constants.CATEGORY_GIRL) return@setOnItemClickListener
            val item = adapter.getItem(position) as GankBean
            startActivity(Intent(ctx, WebActivity::class.java).apply {
                putExtra(WEB_URL, item.url)
            })
        }
        recycler_content.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = articleAdapter
        }

        refresh.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                vm.loadHot(category, false)
            }
        }
    }

    override fun initData() {
        vm.hotData.observe(this, Observer {
            articleAdapter.setNewInstance(it.toMutableList())
            refresh.finishRefresh()
        })

        vm.loadHot(category)
    }

    override fun getReplaceView(): View? = refresh

    companion object {

        @JvmStatic
        fun newInstance(category: String): HotDataFragment {
            val frg = HotDataFragment()
            return frg.apply {
                arguments = Bundle().apply {
                    putString(CATEGORY, category)
                }
            }
        }
    }
}