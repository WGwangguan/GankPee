package com.wangguan.coroutine.ui.hot

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.R
import com.wangguan.coroutine.base.BaseVmFragment
import com.wangguan.coroutine.net.Banner
import com.wangguan.coroutine.ui.hot.banner.BannerViewModel
import com.wangguan.coroutine.ui.hot.banner.MyBannerAdapter
import kotlinx.android.synthetic.main.frg_hot.*

/**
 * Created by WG on 2020-04-17.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class HotFragment : BaseVmFragment<BannerViewModel>() {
    private lateinit var bannerAdapter: MyBannerAdapter
    private val bannerList = arrayListOf<Banner>()
    private val fragments = arrayListOf<Fragment>()
    private val tabsText = listOf("好文", "干货", "靓姐")

    override fun getLayoutId() = R.layout.frg_hot

    override fun initView(view: View?) {
        val ctx = activity ?: return
        bannerAdapter = MyBannerAdapter(ctx, bannerList)
        banner.adapter = bannerAdapter

        vm.banners.observe(this, Observer {
            bannerList.apply {
                clear()
                addAll(it)
            }
            bannerAdapter.notifyDataSetChanged()
        })

        fragments.apply {
            add(HotDataFragment.newInstance(Constants.CATEGORY_ARTICLE))
            add(HotDataFragment.newInstance(Constants.CATEGORY_GANHUO))
            add(HotDataFragment.newInstance(Constants.CATEGORY_GIRL))
        }

        viewPage_hot.adapter = TabAdapter(ctx, fragments)

        TabLayoutMediator(tab_hot, viewPage_hot,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabsText[position]
            }).attach()


    }

    override fun initData() {
        vm.loadBanners()

    }

    class TabAdapter(context: FragmentActivity, val data: List<Fragment>) :
        FragmentStateAdapter(context) {

        override fun getItemCount() = data.size

        override fun createFragment(position: Int) = data[position]
    }

}