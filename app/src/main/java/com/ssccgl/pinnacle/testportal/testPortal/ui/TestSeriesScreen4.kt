package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.Image
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
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.ssccgl.pinnacle.testportal.R
import com.ssccgl.pinnacle.testportal.network.NewTestsWebResponse
import com.ssccgl.pinnacle.testportal.network.TestSeriesResponse
import com.ssccgl.pinnacle.testportal.viewmodel.TestSeriesViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.TestSeriesViewModelFactory
import com.ssccgl.pinnacle.testportal.repository.TestRepository
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance

@Composable
fun TestSeriesScreen(
    emailId: String,
    tierId: String,
    examModeId: Int,
    newTestsWebResponseJson: String,
    navController: NavHostController
) {
    val gson = Gson()
    val newTestsWebResponse: List<NewTestsWebResponse> = runCatching {
        gson.fromJson(newTestsWebResponseJson, Array<NewTestsWebResponse>::class.java).toList()
    }.getOrElse { emptyList() }

    val testRepository = TestRepository(RetrofitInstance.api)
    val testSeriesViewModelFactory = TestSeriesViewModelFactory(testRepository)
    val viewModel: TestSeriesViewModel = viewModel(factory = testSeriesViewModelFactory)

    val testSeriesState by viewModel.testSeries.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTestSeriesBasedOnNewTestsWebResponse(emailId, newTestsWebResponse)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Series") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(testSeriesState) { testSeries ->
                TestSeriesCard(testSeries, navController)
            }
        }
    }
}

@Composable
fun TestSeriesCard(testSeries: TestSeriesResponse, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ssc_logo_final),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = testSeries.test_series_name ?: "N/A",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${testSeries.total_test ?: 0} TOTAL TESTS | ${testSeries.free_total_test ?: 0} FREE TESTS",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("test_series_details2_screen/${testSeries.test_series_id ?: ""}")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0D47A1)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "View Test Series",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}
