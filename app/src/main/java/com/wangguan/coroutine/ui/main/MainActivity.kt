package com.wangguan.coroutine.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.R
import com.wangguan.coroutine.base.BaseCoroutineActivity
import com.wangguan.coroutine.ui.group.GroupFragment
import com.wangguan.coroutine.ui.hot.HotFragment
import com.wangguan.coroutine.ui.hot.banner.BannerViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseCoroutineActivity() {

    private val titles = listOf("热门🔥", "好文", "干货", "靓姐")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frgs = listOf(
            HotFragment(),
            GroupFragment.newInstance(Constants.CATEGORY_ARTICLE),
            GroupFragment.newInstance(Constants.CATEGORY_GANHUO),
            GroupFragment.newInstance(Constants.CATEGORY_GIRL)
        )
        val pagerAdapter = PagerAdapter(this, frgs)

        viewpager2.adapter = pagerAdapter
        viewpager2.isUserInputEnabled = false

        TabLayoutMediator(tab, viewpager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = titles.getOrNull(position)
            }).attach()
    }

    inner class PagerAdapter(context: FragmentActivity, private val fragments: List<Fragment>) :
        FragmentStateAdapter(context) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }

}
