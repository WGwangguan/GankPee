package com.wangguan.coroutine.ui.group

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.R
import com.wangguan.coroutine.base.BaseVmFragment
import com.wangguan.coroutine.net.SubCategory
import com.wangguan.coroutine.ui.main.GankViewModel
import kotlinx.android.synthetic.main.frg_group.*

/**
 * Created by WG on 2020-04-21.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class GroupFragment : BaseVmFragment<GankViewModel>() {

    private val subCategory = arrayListOf<SubCategory>()
    private val category by lazy { arguments?.getString(Constants.CATEGORY).orEmpty() }
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun getLayoutId() = R.layout.frg_group

    override fun initView(view: View?) {
        tabLayoutMediator = TabLayoutMediator(tab_group, pager_group,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                subCategory.getOrNull(position)?.also { tab.text = it.title }
            })
    }

    override fun initData() {
        val ctx = activity ?: return
        vm.subCategory.observe(this, Observer {
            it ?: return@Observer
            subCategory.apply {
                clear()
                addAll(it)
            }

            val fragments = subCategory.map { DataFragment.newInstance(category, it.type) }
            val groupAdapter = GroupAdapter(ctx, fragments)
            pager_group.adapter = groupAdapter

            tabLayoutMediator.attach()
        })

        vm.loadSubCategory(category)

    }

    class GroupAdapter(context: FragmentActivity, val data: List<Fragment>) :
        FragmentStateAdapter(context) {

        override fun getItemCount() = data.size

        override fun createFragment(position: Int) = data[position]
    }

    companion object {
        @JvmStatic
        fun newInstance(category: String): GroupFragment {
            return GroupFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.CATEGORY, category)
                }
            }
        }
    }
}