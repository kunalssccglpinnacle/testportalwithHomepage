//package com.ssccgl.pinnacle.testportal.testPortal.ui
//
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.NewTestsWebViewModel
//
//@Composable
//fun NewTestsWebScreen() {
//    val viewModel: NewTestsWebViewModel = viewModel()
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchExamPostData()
//    }
//
//    val examPostResponse by viewModel.examPostResponse.collectAsState()
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        when {
//            examPostResponse == null -> {
//                Text("Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
//                Log.d("NewTestsWebScreen", "Loading data...")
//            }
//            examPostResponse?.isEmpty() == true -> {
//                Text("No data available", modifier = Modifier.align(Alignment.CenterHorizontally))
//                Log.d("NewTestsWebScreen", "No data available")
//            }
//            else -> {
//                Log.d("NewTestsWebScreen", "Rendering data...")
//                val examPost = examPostResponse?.firstOrNull()?.ExamPost ?: "Unknown"
//                Text(
//                    text = examPost,
//                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp
//                )
//                LazyColumn {
//                    items(examPostResponse?.firstOrNull()?.TestType?.size ?: 0) { index ->
//                        val testType = examPostResponse?.firstOrNull()?.TestType?.get(index)
//                        testType?.let {
//                            Log.d("NewTestsWebScreen", "Rendering testType: $it")
//                            TestCard(
//                                testType = it.test_type,
//                                totalTests = it.TotalTests,
//                                freeTests = it.FreeTests,
//                                totalTestSeries = it.TotalTestSeries
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TestCard(testType: String, totalTests: Int, freeTests: Int, totalTestSeries: Int) {
//    Card(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxWidth(),
//        shape = RoundedCornerShape(8.dp),
//        elevation = 4.dp,
//        backgroundColor = Color(0xFFF0F0F0)
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = testType,
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color.Black
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "$totalTests Total Tests | $freeTests Free Test",
//                fontSize = 14.sp,
//                color = Color.Gray
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Total Test Series",
//                    fontSize = 14.sp,
//                    color = Color.Gray
//                )
//                Text(
//                    text = "$totalTestSeries",
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                )
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = { /* TODO: Handle button click */ },
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
//                shape = RoundedCornerShape(4.dp),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    text = "Continue",
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewTestCard() {
//    TestCard(
//        testType = "Mock Tests(Full Length)",
//        totalTests = 30,
//        freeTests = 0,
//        totalTestSeries = 1
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewNewTestsWebScreen() {
//    Column {
//        Text(
//            text = "SSC CGL Tier 2",
//            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
//            fontWeight = FontWeight.Bold,
//            fontSize = 20.sp
//        )
//        TestCard(
//            testType = "Mock Tests(Full Length)",
//            totalTests = 30,
//            freeTests = 0,
//            totalTestSeries = 1
//        )
//        TestCard(
//            testType = "Sectional Tests",
//            totalTests = 310,
//            freeTests = 0,
//            totalTestSeries = 6
//        )
//    }
//}


package com.ssccgl.pinnacle.testportal.testPortal.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.NewTestsWebViewModel

@Composable
fun NewTestsWebScreen(viewModel: NewTestsWebViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchExamPostData()
    }

    val examPostResponse by viewModel.examPostResponse.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        when {
            examPostResponse == null -> {
                Text("Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
                Log.d("NewTestsWebScreen", "Loading data...")
            }
            examPostResponse?.isEmpty() == true -> {
                Text("No data available", modifier = Modifier.align(Alignment.CenterHorizontally))
                Log.d("NewTestsWebScreen", "No data available")
            }
            else -> {
                Log.d("NewTestsWebScreen", "Rendering data...")
                val examPost = examPostResponse?.firstOrNull()?.ExamPost ?: "Unknown"
                Text(
                    text = examPost,
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                LazyColumn {
                    items(examPostResponse?.firstOrNull()?.TestType?.size ?: 0) { index ->
                        val testType = examPostResponse?.firstOrNull()?.TestType?.get(index)
                        testType?.let {
                            Log.d("NewTestsWebScreen", "Rendering testType: $it")
                            TestCard(
                                testType = it.test_type,
                                totalTests = it.TotalTests,
                                freeTests = it.FreeTests,
                                totalTestSeries = it.TotalTestSeries
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TestCard(testType: String, totalTests: Int, freeTests: Int, totalTestSeries: Int) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF0F0F0)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = testType,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$totalTests Total Tests | $freeTests Free Test",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Test Series",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$totalTestSeries",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: Handle button click */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Continue",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTestCard() {
    TestCard(
        testType = "Mock Tests(Full Length)",
        totalTests = 30,
        freeTests = 0,
        totalTestSeries = 1
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNewTestsWebScreen() {
    Column {
        Text(
            text = "SSC CGL Tier 2",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        TestCard(
            testType = "Mock Tests(Full Length)",
            totalTests = 30,
            freeTests = 0,
            totalTestSeries = 1
        )
        TestCard(
            testType = "Sectional Tests",
            totalTests = 310,
            freeTests = 0,
            totalTestSeries = 6
        )
    }
}
