package com.wangguan.coroutine

import android.content.Context
import android.view.View
import com.wangguan.coroutine.net.GankException
import com.wangguan.coroutine.net.GankResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import retrofit2.HttpException

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */

/**
 * 由主线程启动的视图点击协程。绑定生命周期
 */
fun View.onClick(action: suspend (View) -> Unit) {
    val superJob = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.Main + superJob)
    // 启动一个 actor
    val eventActor = scope.actor<View>(Dispatchers.Main, capacity = Channel.RENDEZVOUS) {
        for (event in channel) action(event)
    }
    // 设置一个监听器来启用 actor
    setOnClickListener {
        eventActor.offer(it)
    }

    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            superJob.cancel()
            eventActor.close()
        }

        override fun onViewAttachedToWindow(v: View?) {
        }

    })
}

fun View?.fillVisible(visible: Boolean) {
    this?.visibility = if (visible) View.VISIBLE else View.GONE
}

fun <T> GankResult<T>.checkResult(successBlock: (T?) -> Unit, failBlock: (Int, String) -> Unit) {
    when (this) {
        is GankResult.SUCCESS -> successBlock.invoke(this.data)
        is GankResult.ERROR -> {
            when (this.e) {
                is HttpException -> failBlock(e.code(), e.message())
                is GankException -> failBlock(e.code ?: -1, e.msg.orEmpty())
                else -> failBlock(-1, "")
            }
        }
    }
}

fun <T> GankResult<T>.checkSuccess(successBlock: (T?) -> Unit) {
    if (this is GankResult.SUCCESS) {
        successBlock(data)
    }
}

//********************Context***********************8

fun Context.dp2px(dp: Int):Int{
    return (dp * resources.displayMetrics.density).toInt()
}

fun Context.px2dp(px:Int):Int{
    return (px / resources.displayMetrics.density).toInt()
}
