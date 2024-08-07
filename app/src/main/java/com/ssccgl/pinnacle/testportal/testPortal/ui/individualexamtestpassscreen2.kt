//package com.ssccgl.pinnacle.testportal.ui
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
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
//import com.ssccgl.pinnacle.testportal.network.IndividualExamTestPass
//import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.IndividualExamTestPassViewModel2
//
//@Composable
//fun IndividualExamTestPassScreen(
//    productId: Int,
//    emailId:String,
//    viewModel: IndividualExamTestPassViewModel2 = viewModel(),
//    navController: NavHostController
//) {
//    viewModel.fetchIndividualExamTestPasses(productId) // Fetch data based on the passed productId
//
//    val examTestPassesState = viewModel.individualExamTestPasses.collectAsState()
//    val examTestPasses = examTestPassesState.value
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Individual Exam Test Pass") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
//
//            TestPassHeader(emailId)
//
//
//
//            Text(
//                text = "Individual Exam Test Passes",
//                fontWeight = FontWeight.Bold,
//                fontSize = 24.sp,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            if (examTestPasses.isEmpty()) {
//                Text(
//                    text = "No test passes available.",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 24.sp,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//            } else {
//
//
//        }
//
//            LazyColumn {
//                items(examTestPasses) { examTestPass ->
//                    StyledCard(examTestPass, navController,emailId)
//                }
//            }
//        }
//
//    }
//}
//
//@Composable
//fun StyledCard(examTestPass: IndividualExamTestPass, navController: NavHostController,emailId: String) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        elevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier
//                .background(Color(0xFFE3F2FD))
//                .padding(16.dp)
//        ) {
//            Text(
//                text = examTestPass.post_tier_name,
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color(0xFF90CAF9))
//                    .padding(vertical = 8.dp),
//                color = Color.Black,
//                textAlign = TextAlign.Center
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Features",
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp,
//                color = Color.Black,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            FeatureList(examTestPass)
//            Spacer(modifier = Modifier.height(8.dp))
//            ActionButtons(navController, examTestPass, emailId )
//        }
//    }
//}
//
//@Composable
//fun FeatureList(examTestPass: IndividualExamTestPass) {
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        val features = listOf(
//            "Mock Test : ${examTestPass.mockTestCount}",
//            "Sectional Test : ${examTestPass.sectionalTestCount}",
//            "Chapterwise Test  ${examTestPass.chapterwiseTestCount}",
//            "Previous Year Test : ${examTestPass.previousYearTestCount}"
//        )
//        features.forEach { feature ->
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    painter = painterResource(id = androidx.appcompat.R.drawable.btn_checkbox_checked_mtrl), // Replace with your check icon
//                    contentDescription = null,
//                    tint = Color.Green,
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = feature,
//                    fontSize = 14.sp,
//                    color = Color.Black
//                )
//            }
//            Spacer(modifier = Modifier.height(4.dp))
//        }
//    }
//}
//
//@Composable
//fun ActionButtons(navController: NavHostController, examTestPass: IndividualExamTestPass,emailId: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Button(
//            onClick = {
//                navController.navigate("new_tests_web_screen/${examTestPass.exam_post_id}/${examTestPass.exam_id}/${examTestPass.id}/$emailId")
//            },
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF90CAF9))
//        ) {
//            Text(text = "Show more")
//        }
//
//        Button(
//            onClick = {
//                // Handle the buy now action
//            },
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
//        ) {
//            Text(text = "Buy now")
//        }
//    }
//}

package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.ssccgl.pinnacle.testportal.network.IndividualExamTestPass
import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.IndividualExamTestPassViewModel2

@Composable
fun IndividualExamTestPassScreen(
    productId: Int,
    emailId: String,
    viewModel: IndividualExamTestPassViewModel2 = viewModel(),
    navController: NavHostController
) {
    viewModel.fetchIndividualExamTestPasses(productId) // Fetch data based on the passed productId

    val examTestPassesState = viewModel.individualExamTestPasses.collectAsState()
    val examTestPasses = examTestPassesState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Individual Exam Test Pass") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            TestPassHeader(emailId)

            Text(
                text = "Individual Exam Test Passes",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (examTestPasses.isEmpty()) {
                Text(
                    text = "No test passes available.",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else {
                LazyColumn {
                    items(examTestPasses) { examTestPass ->
                        StyledCard(examTestPass, navController, emailId, productId)
                    }
                }
            }
        }
    }
}

@Composable
fun StyledCard(examTestPass: IndividualExamTestPass, navController: NavHostController, emailId: String, productId: Int) {
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
                text = examTestPass.post_tier_name,
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
            FeatureList(examTestPass)
            Spacer(modifier = Modifier.height(8.dp))
            ActionButtons(navController, examTestPass, emailId, productId)
        }
    }
}

@Composable
fun FeatureList(examTestPass: IndividualExamTestPass) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val features = listOf(
            "Mock Test : ${examTestPass.mockTestCount}",
            "Sectional Test : ${examTestPass.sectionalTestCount}",
            "Chapterwise Test : ${examTestPass.chapterwiseTestCount}",
            "Previous Year Test : ${examTestPass.previousYearTestCount}"
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
fun ActionButtons(navController: NavHostController, examTestPass: IndividualExamTestPass, emailId: String, productId: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                navController.navigate("new_tests_web_screen/${examTestPass.exam_post_id}/${examTestPass.exam_id}/${examTestPass.id}/$emailId/$productId")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF90CAF9))
        ) {
            Text(text = "Show more")
        }

        Button(
            onClick = {
                // Handle the buy now action
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
        ) {
            Text(text = "Buy now")
        }
    }
}
