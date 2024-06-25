package com.ssccgl.pinnacle.testportal.ui

import IndividualExamTestPassViewModel
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.R
import com.ssccgl.pinnacle.testportal.network.TestPass



import com.ssccgl.pinnacle.testportal.viewmodel.TestPassViewModel

@Composable
fun TestPassScreen(
    navController: NavHostController,
    testPassViewModel: TestPassViewModel = viewModel(),
    individualExamTestPassViewModel: IndividualExamTestPassViewModel = viewModel()
) {
    val testPassesState = testPassViewModel.testPasses.collectAsState()
    val testPasses = testPassesState.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            TestPassHeader() // Add the new header UI here
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
              //  Text(text = "Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        } else {
            items(testPasses) { testPass ->
                TestPassCard(
                    testPass = testPass,
                    navController = navController,
                    individualExamTestPassViewModel = individualExamTestPassViewModel
                )
            }
        }
    }
}

@Composable
fun TestPassHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFBBDEFB))
            .padding(16.dp)
    ) {
        Text(
            text = "Pinnacle",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Get ready for every exam with one pass",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FeatureItem(text = "All Test Series")
                FeatureItem(text = "Prev.Year Paper")
                FeatureItem(text = "Practice")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FeatureItem(text = "Pro live test")
                FeatureItem(text = "Unlimited Test Re-Attempts")
            }
        }
    }
}

@Composable
fun FeatureItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.img), // Replace with your check icon
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun TestPassCard(
    testPass: TestPass,
    navController: NavHostController,
    individualExamTestPassViewModel: IndividualExamTestPassViewModel
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
            Button(
                onClick = {
                    individualExamTestPassViewModel.fetchIndividualExamTestPasses(testPass.exam_id)
                    navController.navigate("individual_exam_test_pass")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text(text = "buy Now")
            }

            Button(
                onClick = {
                    individualExamTestPassViewModel.fetchIndividualExamTestPasses(testPass.exam_id)
                    navController.navigate("individual_exam_test_pass")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text(text = "show more")
            }}
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Use coupon code for extra discount.... Apply code",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

