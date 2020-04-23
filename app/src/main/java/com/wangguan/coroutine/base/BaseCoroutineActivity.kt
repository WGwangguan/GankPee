package com.wangguan.coroutine.base

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
open class BaseCoroutineActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}