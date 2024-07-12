package com.ssccgl.pinnacle.testportal.ui

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssccgl.pinnacle.testportal.R
import com.ssccgl.pinnacle.testportal.network.TestPass
//import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.TestPassViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.TestPassViewModel

@Composable
fun TestPassScreen(
    navController: NavHostController,
    testPassViewModel: TestPassViewModel = viewModel()
) {
    val testPassesState = testPassViewModel.testPasses.collectAsState()
    val testPasses = testPassesState.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            TestPassHeader()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Test Pass by Categories",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        if (testPasses.isEmpty()) {
            item {
                Text(text = "Loading..." )
            }
        } else {
            items(testPasses) { testPass ->
                TestPassCard(
                    testPass = testPass,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun TestPassHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFDCE9E4))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = "Pinnacle Test Pass",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722),
                textAlign = TextAlign.Start
            )
            Text(
                text = "One for all",
                fontSize = 24.sp,
                color = Color(0xFFFF5722),
                textAlign = TextAlign.Start
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Special Offer Hurry Up!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722),
                textAlign = TextAlign.End
            )
            Text(
                text = "UPTO 75% OFF",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722),
                textAlign = TextAlign.End
            )
            Text(
                text = "On All Test Courses",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun TestPassCard(
    testPass: TestPass,
    navController: NavHostController
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFE3F2FD))
                .padding(16.dp)
        ) {
            Text(
                text = testPass.Product_title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF90CAF9))
                    .padding(vertical = 8.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Features",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FeatureList(testPass)
            Spacer(modifier = Modifier.height(8.dp))
            PriceSection(testPass)
            Spacer(modifier = Modifier.height(8.dp))
            ActionButtons(navController, testPass)
        }
    }
}

@Composable
fun FeatureList(testPass: TestPass) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val features = listOf(
            "Included Test series: ${testPass.totalTestSeries}",
            "Included all ssc Exams",
            "Full Mock Test based on Latest Pattern",
            "sectional based on different level",
            "chapterwise based on weightage",
            "Previous Year Included"
        )
        features.forEach { feature ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = androidx.appcompat.R.drawable.btn_checkbox_checked_mtrl), // Replace with your check icon
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = feature,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun PriceSection(testPass: TestPass) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(
                text = "Price: ",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "₹${testPass.product_max_price}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Red,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "₹${testPass.product_price}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Green
            )
        }
    }
}

@Composable
fun ActionButtons(navController: NavHostController, testPass: TestPass) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                navController.navigate("individual_exam_test_pass/${testPass.id}")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF90CAF9))
        ) {
            Text(text = "show more")
        }

        Button(
            onClick = {
                navController.navigate("individual_exam_test_pass/${testPass.id}")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
        ) {
            Text(text = "buy now")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestPassScreenPreview() {
    val navController = rememberNavController()
    TestPassScreen(navController)
}
