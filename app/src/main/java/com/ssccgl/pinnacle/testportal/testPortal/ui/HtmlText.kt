//
//package com.ssccgl.pinnacle.testportal.ui
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberUpdatedState
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//
//@Composable
//fun HtmlText(html: String) {
//    var webView: WebView? by remember { mutableStateOf<WebView?>(null) }
//    val currentHtml by rememberUpdatedState(html)
//
//    AndroidView(
//        factory = { context ->
//            WebView(context).apply {
//                webViewClient = WebViewClient()
//                loadData(currentHtml, "text/html", "UTF-8")
//                webView = this
//            }
//        },
//        update = {
//            webView?.loadData(currentHtml, "text/html", "UTF-8")
//        }
//    )
//}
////package com.ssccgl.pinnacle.testportal.ui
////
////import android.widget.TextView
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.graphics.toArgb
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.viewinterop.AndroidView
////import androidx.core.text.HtmlCompat
////
////@Composable
////fun HtmlText(html: String) {
////    val context = LocalContext.current
////    AndroidView(
////    factory = {
////        TextView(context).apply {
////            text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
////            setTextColor(Color.Black.toArgb())
////            textSize = 16f
////        }
////    })
////}


package com.ssccgl.pinnacle.testportal.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun HtmlText(html: String) {
    var webView: WebView? by remember { mutableStateOf<WebView?>(null) }
    val currentHtml by rememberUpdatedState(html)
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                        }
                    }
                    loadDataWithBaseURL(null, currentHtml, "text/html", "UTF-8", null)
                    webView = this
                }
            },
            update = {
                webView?.loadDataWithBaseURL(null, currentHtml, "text/html", "UTF-8", null)
            },
            modifier = Modifier.fillMaxSize()
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
            )
        }
    }
}
