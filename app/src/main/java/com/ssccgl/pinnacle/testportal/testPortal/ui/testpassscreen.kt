package com.ssccgl.pinnacle.testportal.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssccgl.pinnacle.testportal.network.TestPass
import com.ssccgl.pinnacle.testportal.viewmodel.TestPassViewModel

@Composable
fun TestPassScreen(viewModel: TestPassViewModel = viewModel()) {
    val testPassesState = viewModel.testPasses.collectAsState()
    val testPasses = testPassesState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Test Pass by Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (testPasses.isEmpty()) {
            Log.d("TestPassScreen", "No test passes available.")
            Text(text = "Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Log.d("TestPassScreen", "Displaying test passes: $testPasses")
            LazyColumn {
                items(testPasses) { testPass ->
                    TestPassCard(testPass, viewModel)
                }
            }
        }
    }
}

@Composable
fun TestPassCard(testPass: TestPass, viewModel: TestPassViewModel) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFBBDEFB))
                .padding(16.dp)
        ) {
            Text(
                text = testPass.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Features")
            Text(text = "• Mock Test based on latest pattern")
            Text(text = "• Previous year included pdf")
            Text(text = "• Sectional based on different level")
            Text(text = "• Chapterwise based on weightage")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹${testPass.price}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp
                )
                Text(
                    text = "₹${testPass.max_price}",
                    color = Color(0xFFD32F2F),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.joinTestPass(testPass.exam_id) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text(text = "Join Now")
            }
        }
    }
}
