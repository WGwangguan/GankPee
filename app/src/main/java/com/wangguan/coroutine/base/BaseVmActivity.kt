package com.wangguan.coroutine.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wangguan.coroutine.net.RequestState
import com.wangguan.coroutine.util.view.ReplaceLayoutManager
import java.lang.reflect.ParameterizedType

/**
 * Created by WG on 2020-04-23.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
abstract class BaseVmActivity<T : BaseViewModel> : BaseCoroutineActivity() {
    lateinit var vm: T
    private val replaceManager by lazy { replaceBuilder().build() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pd: ParameterizedType = this.javaClass.genericSuperclass as ParameterizedType
        val clazz = pd.actualTypeArguments[0] as Class<T>
        vm = ViewModelProvider(this).get(clazz)
        setContentView(getLayoutId())

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

        initView()
        initData(savedInstanceState)
    }

    protected abstract fun getLayoutId(): Int

    open fun getReplaceView(): View? = null

    protected abstract fun initView()

    protected abstract fun initData(savedInstanceState: Bundle?)

    open fun replaceBuilder(): ReplaceLayoutManager.Builder =
        ReplaceLayoutManager.Builder(getReplaceView())
}