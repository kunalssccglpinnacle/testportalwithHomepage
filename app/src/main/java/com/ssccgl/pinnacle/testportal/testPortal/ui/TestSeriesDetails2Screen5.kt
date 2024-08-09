
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.R
import com.ssccgl.pinnacle.testportal.network.AR
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import com.ssccgl.pinnacle.testportal.network.TestSeriesAccessRequest
import com.ssccgl.pinnacle.testportal.repository.TestRepository
import com.ssccgl.pinnacle.testportal.viewmodel.TestSeriesDetails2ViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.TestSeriesDetails2ViewModelFactory

@Composable
fun TestSeriesDetails2Screen(
    emailId: String,
    examId: Int,
    examPostId: Int,
    tierId: String,
    productId: Int,
    testSeriesId: String,
    examModeId: Int,
    navController: NavHostController
) {
    val testRepository = TestRepository(RetrofitInstance.api)
    val testSeriesDetails2ViewModelFactory = TestSeriesDetails2ViewModelFactory(testRepository)
    val viewModel: TestSeriesDetails2ViewModel = viewModel(factory = testSeriesDetails2ViewModelFactory)

    val testSeriesDetailsState by viewModel.testSeriesDetails.collectAsState()
    val userStatus by viewModel.userStatus.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTestSeriesDetails(testSeriesId, emailId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Series Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (testSeriesDetailsState != null) {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(testSeriesDetailsState?.ar ?: emptyList()) { test ->
                        TestDetailsCard(
                            test, navController, emailId, viewModel,
                            examId, examPostId, tierId, productId, examModeId, errorMessage
                        )
                    }
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun TestDetailsCard(
    test: AR,
    navController: NavController,
    emailId: String,
    viewModel: TestSeriesDetails2ViewModel,
    examId: Int,
    examPostId: Int,
    tierId: String,
    productId: Int,
    examModeId: Int,
    errorMessage: String?
) {

    val userStatus by viewModel.userStatus.collectAsState()

    LaunchedEffect(Unit) {
        val accessRequest = TestSeriesAccessRequest(
            email_id = emailId,
            exam_id = examId,
            post_id = examPostId,
            tier_id = tierId,
            exam_mode_id = examModeId,
            product_id = productId
        )
        viewModel.checkTestSeriesAccess(listOf(accessRequest))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFD6EAF8)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = test.Title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
            }


            if (userStatus == 1) {
                when (test.PaperStatus.toInt()) {
                    2 -> {
                        OutlinedButton(
                            onClick = {
                                navController.navigate("instructions_screen/${test.Title}/${test.Marks}/${test.Time}/${test.test_series_id}/${test.paper_code}/${test.exam_mode_id}/${test.Questions}/${emailId}")
                            },
                            border = BorderStroke(1.dp, Color(0xFF8E44AD)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color(0xFF8E44AD)
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Start Test",
                                color = Color(0xFF8E44AD),
                                fontSize = 12.sp
                            )
                        }
                    }
                    1 -> {
                        OutlinedButton(
                            onClick = {
                                navController.navigate(
                                    "result_screen/${test.paper_code}/${emailId}/${examModeId}/${test.test_series_id}"
                                )
//                                navController.navigate("result_screen/${test.Title}/${test.test_series_id}/${test.paper_code}/${test.exam_mode_id}/${emailId}")
                            },
                            border = BorderStroke(1.dp, Color(0xFF8E44AD)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color(0xFF8E44AD)
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "View Result",
                                color = Color(0xFF8E44AD),
                                fontSize = 12.sp
                            )
                        }
                    }
                    else -> {
                        OutlinedButton(
                            onClick = {
                                navController.navigate("instructions_screen/${test.Title}/${test.Marks}/${test.Time}/${test.test_series_id}/${test.paper_code}/${test.exam_mode_id}/${test.Questions}/${emailId}")
//                                navController.navigate("resume_screen/${test.Title}/${test.test_series_id}/${test.paper_code}/${test.exam_mode_id}/${emailId}")
                            },
                            border = BorderStroke(1.dp, Color(0xFF8E44AD)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = Color.Transparent,
                                contentColor = Color(0xFF8E44AD)
                            ),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text(
                                text = "Resume Test",
                                color = Color(0xFF8E44AD),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            } else {
                OutlinedButton(
                    onClick = { /* Handle unlock action */ },
                    border = BorderStroke(1.dp, Color(0xFF8E44AD)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color(0xFF8E44AD)
                    ),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Unlock",
                        color = Color(0xFF8E44AD),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                InfoIconWithText(iconId = R.drawable.ic_quemark, text = test.Questions.toString())
                Spacer(modifier = Modifier.width(16.dp))
                InfoIconWithText(iconId = R.drawable.img, text = test.Marks.toString())
                Spacer(modifier = Modifier.width(16.dp))
                InfoIconWithText(iconId = R.drawable.ic_time, text = test.Time.toString())
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Syllabus | ${test.languages}",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(Color(0xFF0D47A1))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (test.free_status == "0") "Free Mock" else "(${test.start_date} ${test.start_time})",
                    color = if (test.free_status == "1") Color.Blue else Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun InfoIconWithText(iconId: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

