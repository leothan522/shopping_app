package com.leothan.shoppingcenter.apis

import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.leothan.shoppingcenter.MainActivity
import com.leothan.shoppingcenter.R
import com.leothan.shoppingcenter.dialogs.Dialogs

object WebViewHelper {

    fun webView(webView: WebView, url: String, activity: Activity?, loading: ConstraintLayout){
        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                //Toast.makeText(webView.context, "NO INTERNET: $error", Toast.LENGTH_LONG).show()
                //(activity as MainActivity?)!!.onBackPressed()
                webView.isVisible = false
                Dialogs().noInternetMain(
                    webView.context,
                    activity!!.layoutInflater.inflate(R.layout.dialog_no_internet, null),
                    activity!!
                )
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress > 30){
                    loading.isVisible = false
                }
            }
        }
        webView.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> //This is the filter
            if (event.action !== KeyEvent.ACTION_DOWN) return@OnKeyListener true
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    (activity as MainActivity?)!!.onBackPressed()
                }
                return@OnKeyListener true
            }
            false
        })
    }

}