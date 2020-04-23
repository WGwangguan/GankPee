package com.wangguan.coroutine.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by WG on 2020-04-17.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc: 配置 Viewpager2 实现的懒加载的 Fragment   AndroidX
 */
abstract class BaseLazyFragment : Fragment() {
    var isLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(view)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            initData()
            isLoaded = true
        }
    }

    protected abstract fun getLayoutId(): Int

    open fun getReplaceView(): View? = null

    protected abstract fun initView(view: View?)

    protected abstract fun initData()

}