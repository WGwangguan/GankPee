package com.wangguan.coroutine.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wangguan.coroutine.net.RequestState
import com.wangguan.coroutine.util.view.ReplaceLayoutManager
import java.lang.reflect.ParameterizedType

/**
 * Created by WG on 2020-04-17.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc: 配置 Viewpager2 实现的懒加载的 Fragment   AndroidX
 */
abstract class BaseVmFragment<T : BaseViewModel> : BaseLazyFragment() {
    lateinit var vm: T
    private val replaceManager by lazy { replaceBuilder().build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pd: ParameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        val clazz = pd.actualTypeArguments[0] as Class<T>
        vm = ViewModelProvider(this).get(clazz)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.requestState.observe(this, Observer {
            when (it) {
                is RequestState.LOADING -> {
                    // 展示加载动画
                    replaceManager.showLoading()
                }
                is RequestState.EXCEPTION -> {
                    // 展示错误信息（网络错误、后台错误等）
                    replaceManager.showError()
                }
                is RequestState.SUCCESS -> {
                    // 恢复 view
                    replaceManager.restore()
                }
            }
        })
    }

    open fun replaceBuilder(): ReplaceLayoutManager.Builder =
        ReplaceLayoutManager.Builder(getReplaceView())

}