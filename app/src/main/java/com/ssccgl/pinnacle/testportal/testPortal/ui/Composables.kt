
package com.ssccgl.pinnacle.testportal.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun CircularButton(onClick: () -> Unit, text: String, answerType: Int) {
    val backgroundColor: Color
    val textColor: Color
    val border: BorderStroke?
    val icon: @Composable (() -> Unit)?

    when (answerType) {
        1 -> {
            backgroundColor = Color.Green
            textColor = Color.White
            border = null
            icon = null
        }
        2 -> {
            backgroundColor = Color.Red
            textColor = Color.White
            border = null
            icon = null
        }
        3 -> {
            backgroundColor = Color.White
            textColor = Color.Black
            border = BorderStroke(2.dp, Color.Blue)
            icon = {
                Icon(Icons.Default.Check, contentDescription = "Tick", tint = Color.Blue)
            }
        }
        4 -> {
            backgroundColor = Color.Green
            textColor = Color.White
            border = BorderStroke(2.dp, Color.Blue)
            icon = {
                Icon(
                    Icons.Default.Check, contentDescription = "Tick",
                    tint = Color.Blue)
            }
        }
        0 -> {
            backgroundColor = Color.White
            textColor = Color.Black
            border = null
            icon = null
        }
        else -> {
            backgroundColor = Color.Blue
            textColor = Color.White
            border = null
            icon = null
        }
    }

    Surface(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(4.dp),
        onClick = onClick,
        shape = CircleShape,
        color = backgroundColor,
        border = border
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = text, color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            icon?.invoke()
        }
    }
}

@Composable
fun OptionItem(option: String, optionValue: String, selectedOption: String, onSelectOption: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = selectedOption == optionValue,
            onClick = { onSelectOption(optionValue) }
        )
        HtmlText(html = option)
    }
}

fun formatTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val tSecond = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, tSecond)
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    borderColor: Color? = null,
    width: Int = 120,
    height: Int = 40,
    fontSize: Int = 12 // Default font size
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(width.dp, height.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(4.dp),
        border = borderColor?.let { BorderStroke(1.dp, it) }
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = textColor,
                fontSize = fontSize.sp
            )
        )
    }
}


@Composable
fun OptionItem(
    option: String,
    optionValue: String,
    selectedOption: String,
    correct_answer: String,
    onSelectOption: (String) -> Unit
) {
    val backgroundColor = when {
        optionValue == correct_answer && optionValue == selectedOption -> Color.Green // Correct and selected
        optionValue == selectedOption && optionValue != correct_answer -> Color.Red // Selected but incorrect
        optionValue == correct_answer -> Color.Green.copy(alpha = 0.3f) // Correct but not selected
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onSelectOption(optionValue) }
            .padding(8.dp)
    ) {
        Text(
            text = optionValue,
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 8.dp)
        )
        HtmlText(html = option)
    }
}
