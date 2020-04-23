package com.wangguan.coroutine.util.view

import android.view.LayoutInflater
import android.view.View
import com.wangguan.coroutine.R

/**
 * Created by WG on 2020-04-22.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class ReplaceLayoutManager private constructor(builder: Builder) {

    private val loadingResId = builder.loadingResId
    private val errorResId = builder.errorResId
    private val content = builder.contentView
    private val inflater by lazy { content?.context?.let { LayoutInflater.from(it) } }
    private val helper by lazy { ReplaceLayoutHelper(content) }
    private val loadingView by lazy { builder.loadingView ?: inflater?.inflate(loadingResId, null) }
    private val errorView by lazy { builder.errorView ?: inflater?.inflate(errorResId, null) }

    fun showLoading() {
        helper.showStatusLayout(loadingView)
    }

    fun showError() {
        helper.showStatusLayout(errorView)
    }

    fun restore() {
        helper.restoreLayout()
    }

    class Builder {
        var loadingResId: Int = R.layout.replace_layout_loading
        var errorResId: Int = R.layout.replace_layout_error
        var loadingView: View? = null
        var errorView: View? = null
        var contentView: View? = null

        constructor(content: View?) {
            contentView = content
        }

        fun loadingResId(id: Int): Builder {
            loadingResId = id
            return this
        }

        fun errorResId(id: Int): Builder {
            errorResId = id
            return this
        }

        fun loadingView(view: View): Builder {
            loadingView = view
            return this
        }

        fun errorView(view: View): Builder {
            errorView = view
            return this
        }

        fun build(): ReplaceLayoutManager {
            return ReplaceLayoutManager(this)
        }
    }

}