package com.wangguan.coroutine.ui.group

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.R
import com.wangguan.coroutine.base.BaseVmFragment
import com.wangguan.coroutine.net.GankBean
import com.wangguan.coroutine.ui.main.GankViewModel
import com.wangguan.coroutine.ui.web.WebActivity
import kotlinx.android.synthetic.main.frg_data.*

/**
 * Created by WG on 2020-04-21.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class DataFragment : BaseVmFragment<GankViewModel>() {

    private val category by lazy { arguments?.getString(Constants.CATEGORY).orEmpty() }
    private val dataAdapter by lazy { DataAdapter(category) }
    private val type by lazy { arguments?.getString(Constants.TYPE).orEmpty() }
    private var page = 1

    override fun getLayoutId() = R.layout.frg_data

    override fun initView(view: View?) {
        val ctx = activity ?: return

        dataAdapter.setOnItemClickListener { adapter, view, position ->
            if (category == Constants.CATEGORY_GIRL) return@setOnItemClickListener
            val item = adapter.getItem(position) as GankBean
            startActivity(Intent(ctx, WebActivity::class.java).apply {
                putExtra(Constants.WEB_URL, item.url)
            })
        }

        recycler_data.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = dataAdapter
        }

        refresh.apply {
            setEnableLoadMore(false)
            setOnRefreshListener {
                page = 1
                request(false)
            }
        }
    }

    override fun initData() {
        val loadMoreModule = dataAdapter.loadMoreModule
        loadMoreModule.isEnableLoadMoreIfNotFullPage = false

        vm.gankPage.observe(this, Observer {
            it ?: return@Observer
            when (it.pageBean.page) {
                1 -> {
                    dataAdapter.setNewInstance(it.data?.toMutableList())
                }
                else -> {
                    dataAdapter.addData(it.data ?: emptyList())
                }
            }
            if (it.data?.size ?: 0 < Constants.DEFAULT_COUNT) {
                loadMoreModule.loadMoreEnd()
            } else {
                loadMoreModule.loadMoreComplete()
            }
            refresh.finishRefresh()
            page++
        })

        loadMoreModule.setOnLoadMoreListener {
            request(false)
        }

        request()

    }

    private fun request(showLoading: Boolean = true) {
        vm.loadData(category, type, page, showLoading = showLoading)
    }

    override fun getReplaceView(): View? {
        return refresh
    }

    companion object {
        @JvmStatic
        fun newInstance(category: String, type: String): DataFragment {
            return DataFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CATEGORY, category)
                    putString(Constants.TYPE, type)
                }
            }
        }
    }
}