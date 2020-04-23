package com.wangguan.coroutine.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wangguan.coroutine.net.ReqStatusHelper
import com.wangguan.coroutine.net.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by WG on 2020-04-16.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
open class BaseViewModel : ViewModel() {
    val errorTips by lazy { MutableLiveData<String>() }
    val requestState by lazy { MutableLiveData<RequestState>() }
    val reqHelper by lazy { ReqStatusHelper(this) }

    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch {
            block()
        }

}