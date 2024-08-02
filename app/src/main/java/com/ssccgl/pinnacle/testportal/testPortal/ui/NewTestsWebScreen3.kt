//package com.ssccgl.pinnacle.testportal.ui
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.google.gson.Gson
//import com.ssccgl.pinnacle.testportal.R
//import com.ssccgl.pinnacle.testportal.network.NewTestsWebResponse
//import com.ssccgl.pinnacle.testportal.network.TestType
//import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.NewTestsWebViewModel
//
//
//@Composable
//fun NewTestsWebScreen(
//    emailId: String,
//    examPostId: String,
//    examId: String,
//    tierId: String,
//    viewModel: NewTestsWebViewModel = viewModel(),
//    navController: NavHostController
//) {
//    viewModel.fetchNewTestsWebData(emailId, examPostId, examId, tierId)
//
//    val newTestsWebState by viewModel.newTestsWebData.collectAsState()
//    val totalTestsState by viewModel.totalTests.collectAsState()
//    val chapterTestsState by viewModel.chapterTests.collectAsState()
//    val sectionalTestsState by viewModel.sectionalTests.collectAsState()
//    val previousYearTestsState by viewModel.previousYearTests.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("New Tests Web") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            if (newTestsWebState.isNotEmpty()) {
//                DetailSection(
//                    newTest = newTestsWebState[0],
//                    totalTests = totalTestsState,
//                    chapterTests = chapterTestsState,
//                    sectionalTests = sectionalTestsState,
//                    previousYearTests = previousYearTestsState
//                )
//                ReleasePlanSection(
//                    testTypes = newTestsWebState[0].TestType,
//                    navController = navController,
//                    emailId = emailId,
//                    examPostId = examPostId,
//                    examId = examId,
//                    tierId = tierId,
//                    newTestsWebResponse = newTestsWebState
//                )
//            } else {
//                Text(
//                    text = "No tests available.",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp,
//                    modifier = Modifier.padding(16.dp),
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun DetailSection(
//    newTest: NewTestsWebResponse,
//    totalTests: Int,
//    chapterTests: Int,
//    sectionalTests: Int,
//    previousYearTests: Int
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color(0xFF6200EA))
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = newTest.ExamPost ?: "No Exam Post",
//            fontWeight = FontWeight.Bold,
//            fontSize = 24.sp,
//            color = Color.White
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Image(
//            painter = painterResource(id = R.drawable.ssc_logo_final),
//            contentDescription = null,
//            modifier = Modifier.size(64.dp)
//        )
//
//        Text(
//            text = "$totalTests Tests",
//            fontWeight = FontWeight.Bold,
//            fontSize = 20.sp,
//            color = Color.White
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = "$chapterTests Chapter Tests $sectionalTests Sectional Tests $previousYearTests Previous Year Tests",
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            color = Color.White,
//            textAlign = TextAlign.Center
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//    }
//}
//
//@Composable
//fun ReleasePlanSection(
//    testTypes: List<TestType>,
//    navController: NavHostController,
//    emailId: String,
//    examPostId: String,
//    examId: String,
//    tierId: String,
//    newTestsWebResponse: List<NewTestsWebResponse>
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Release Plan",
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//        LazyColumn {
//            items(testTypes) { testType ->
//                ReleasePlanItem(
//                    testType = testType,
//                    navController = navController,
//                    emailId = emailId,
//                    examPostId = examPostId,
//                    examId = examId,
//                    tierId = tierId,
//                    newTestsWebResponse = newTestsWebResponse
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ReleasePlanItem(
//    testType: TestType,
//    navController: NavHostController,
//    emailId: String,
//    examPostId: String,
//    examId: String,
//    tierId: String,
//    newTestsWebResponse: List<NewTestsWebResponse>
//) {
//    val gson = Gson()
//    val newTestsWebResponseJson = gson.toJson(newTestsWebResponse)
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        elevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = testType.test_type,
//                fontWeight = FontWeight.Bold,
//                fontSize = 18.sp,
//                color = Color(0xFF6200EA)
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = "${testType.TotalTests} Tests",
//                fontWeight = FontWeight.Normal,
//                fontSize = 14.sp,
//                color = Color(0xFF1C1C1C)
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Total Test Series",
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 14.sp,
//                    color = Color(0xFF1C1C1C)
//                )
//                Text(
//                    text = "${testType.TotalTestSeries}",
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 14.sp,
//                    color = Color(0xFF1C1C1C)
//                )
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = {
//                    navController.navigate("test_series_screen/$emailId/$tierId/${testType.exam_mode_id}/$newTestsWebResponseJson")
//                },
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA000)),
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            ) {
//                Text(
//                    text = "Continue",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 14.sp,
//                    color = Color.White
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Icon(
//                    painter = painterResource(id = R.drawable.img_2),
//                    contentDescription = null,
//                    tint = Color.White
//                )
//            }
//        }
//    }
//}


package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.R
import com.ssccgl.pinnacle.testportal.network.NewTestsWebResponse
import com.ssccgl.pinnacle.testportal.network.TestType
import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.NewTestsWebViewModel

@Composable
fun NewTestsWebScreen(
    emailId: String,
    examPostId: String,
    examId: String,
    tierId: String,
    viewModel: NewTestsWebViewModel = viewModel(),
    navController: NavHostController
) {
    viewModel.fetchNewTestsWebData(emailId, examPostId, examId, tierId)

    val newTestsWebState by viewModel.newTestsWebData.collectAsState()
    val totalTestsState by viewModel.totalTests.collectAsState()
    val chapterTestsState by viewModel.chapterTests.collectAsState()
    val sectionalTestsState by viewModel.sectionalTests.collectAsState()
    val previousYearTestsState by viewModel.previousYearTests.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Tests Web") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (newTestsWebState.isNotEmpty()) {
                DetailSection(
                    newTest = newTestsWebState[0],
                    totalTests = totalTestsState,
                    chapterTests = chapterTestsState,
                    sectionalTests = sectionalTestsState,
                    previousYearTests = previousYearTestsState
                )
                ReleasePlanSection(
                    testTypes = newTestsWebState[0].TestType,
                    navController = navController,
                    emailId = emailId,
                    examPostId = examPostId,
                    examId = examId,
                    tierId = tierId
                )
            } else {
                Text(
                    text = "No tests available.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DetailSection(
    newTest: NewTestsWebResponse,
    totalTests: Int,
    chapterTests: Int,
    sectionalTests: Int,
    previousYearTests: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF6200EA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = newTest.ExamPost ?: "No Exam Post",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.ssc_logo_final),
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Text(
            text = "$totalTests Tests",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$chapterTests Chapter Tests $sectionalTests Sectional Tests $previousYearTests Previous Year Tests",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ReleasePlanSection(
    testTypes: List<TestType>,
    navController: NavHostController,
    emailId: String,
    examPostId: String,
    examId: String,
    tierId: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Release Plan",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn {
            items(testTypes) { testType ->
                ReleasePlanItem(
                    testType = testType,
                    navController = navController,
                    emailId = emailId,
                    examPostId = examPostId,
                    examId = examId,
                    tierId = tierId
                )
            }
        }
    }
}

@Composable
fun ReleasePlanItem(
    testType: TestType,
    navController: NavHostController,
    emailId: String,
    examPostId: String,
    examId: String,
    tierId: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = testType.test_type,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF6200EA)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${testType.TotalTests} Tests",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF1C1C1C)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total Test Series",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF1C1C1C)
                )
                Text(
                    text = "${testType.TotalTestSeries}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF1C1C1C)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("test_series_screen/$emailId/$tierId/${testType.exam_mode_id}")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA000)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Continue",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.img_2),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

