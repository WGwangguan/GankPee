package com.wangguan.coroutine.ui.web

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wangguan.coroutine.Constants
import com.wangguan.coroutine.R
import com.wangguan.coroutine.base.BaseCoroutineActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseCoroutineActivity() {

    private lateinit var wb: WebView
    private val url by lazy { intent?.getStringExtra(Constants.WEB_URL) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        wb = WebView(this)
        webParent.addView(
            wb,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        initWb()

        wb.loadUrl(url)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWb() {
        val settings: WebSettings = wb.settings
        // //Android容器允许JS脚本
        // //Android容器允许JS脚本
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = false //设置false fix 3.21 WebView file域同源策略绕过风险

        settings.allowFileAccessFromFileURLs = false
        settings.allowUniversalAccessFromFileURLs = false
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setGeolocationEnabled(true)

        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        //不显示webview缩放按钮
        //不显示webview缩放按钮
        settings.displayZoomControls = false

        settings.useWideViewPort = true
        settings.databaseEnabled = true //允许使用WebSQL

        //Android5.0以后默认为MIXED_CONTENT_NEVER_ALLOW
        //所以在webview加载页面之前，设置加载模式为MIXED_CONTENT_ALWAYS_ALLOW
        //Android5.0以后默认为MIXED_CONTENT_NEVER_ALLOW
        //所以在webview加载页面之前，设置加载模式为MIXED_CONTENT_ALWAYS_ALLOW
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //可以一了百了的设置，总是允许混合，兼容性实在是难搞
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        wb.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY

        wb.webViewClient = WebViewClient()
    }

    override fun onBackPressed() {
        if (wb.canGoBack()) {
            wb.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
