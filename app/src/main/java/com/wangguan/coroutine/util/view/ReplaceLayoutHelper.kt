package com.wangguan.coroutine.util.view

import android.R
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * Created by WG on 2020-04-22.
 * Email: wg5329@163.com
 * Github: https://github.com/WGwangguan
 * Desc:
 */
class ReplaceLayoutHelper : IStatusLayoutHelper {
    /**
     * 需要替换的 View
     */
    private var contentLayout: View? = null
    /**
     * contentLayout 的布局参数
     */
    private var params: ViewGroup.LayoutParams? = null
    /**
     * contentLayout 的父 ViewGroup
     */
    private var parentLayout: ViewGroup? = null
    /**
     * contentLayout 在 parentLayout 中的位置
     */
    private var viewIndex = 0
    /**
     * 当前显示的 View
     */
    private var currentLayout: View? = null

    constructor(contentLayout: View?) {
        this.contentLayout = contentLayout
        getContentLayoutParams()
    }

    /**
     * 获取 contentLayout 的参数信息 LayoutParams、Parent
     */
    private fun getContentLayoutParams() {
        val content = contentLayout?:return
        params = content.layoutParams
        parentLayout = if (content.parent != null) { // 有直接的父控件
            content.parent as ViewGroup
        } else {
            // 认为 contentLayout 是 activity 的跟布局
            // 所以它的父控件就是 android.R.id.content
            content.rootView.findViewById(R.id.content)
        }
        if (parentLayout == null) {
            // 以上两种方法还没有获取到父控件
            // contentLayout 非 activity 的跟布局
            // 父控件就是自己
            if (contentLayout is ViewGroup) {
                parentLayout = contentLayout as ViewGroup
                viewIndex = 0
            }
        } else {
            val count = parentLayout?.childCount ?: 0
            for (index in 0 until count) {
                if (contentLayout == parentLayout?.getChildAt(index)) { // 获取 contentLayout 在 parentLayout 中的位置
                    viewIndex = index
                    break
                }
            }
        }
        currentLayout = contentLayout
    }

    override fun showStatusLayout(view: View?) {
        view ?: return
        if (currentLayout != view) {
            currentLayout = view
            val parent = view.parent as? ViewGroup
            // 去除 view 的 父 view，才能添加到别的 ViewGroup 中
            parent?.removeView(view)
            // 替换 = 移除 + 添加  修复bugly报错，view为null
            // 初步理解一般这里不会越界才对
            if (parentLayout?.childCount ?: 0 > viewIndex) { //移除上一个statusLayout(可能是statusLayout，或者contentView)
                parentLayout?.removeViewAt(viewIndex)
                //再在相同位置添加新的statusLayout
                parentLayout?.addView(view, viewIndex, params)
            }
            // 父布局为CoordinatorLayout时，如果替换直接子 view，首次会出现 子view measure 异常问题
            (parentLayout as? CoordinatorLayout)?.apply {
                // 在消息队列后重新请求一次视图刷新。
                requestLayout()
                invalidate()
            }
        }
    }

    override fun restoreLayout() {
        showStatusLayout(contentLayout)
    }

}