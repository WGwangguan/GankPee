package com.wangguan.coroutine.util

import android.content.pm.ApplicationInfo
import com.wangguan.coroutine.App

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
object DebugUtil {

    @JvmStatic
    val isDebug by lazy {
        App.getInstance().applicationInfo != null && App.getInstance().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }
}