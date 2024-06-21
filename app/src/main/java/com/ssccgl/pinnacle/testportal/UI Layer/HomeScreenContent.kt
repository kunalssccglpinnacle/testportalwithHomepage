
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssccgl.pinnacle.testportal.data.HomeItem
import androidx.compose.material.Text
@Composable
fun HomeScreenContent(items: List<HomeItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        for (i in items.indices step 4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (j in 0 until 4) {
                    if (i + j < items.size) {
                        HomeIconItem(icon = items[i + j].icon, text = items[i + j].text)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeIconItem(icon: Int, text: String) {
    Column(
        modifier = Modifier.size(72.dp).background(Color(0xFF03DAC5), shape = CircleShape)
            .padding(8.dp)
            .clickable { }
          //  .background(Color.LightGray) // Add background color if needed
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = text,
            modifier = Modifier.size(56.dp) // Adjust the size as needed
        )
        Text(text = text, fontSize = 12.sp, textAlign = TextAlign.Center)
    }
}


