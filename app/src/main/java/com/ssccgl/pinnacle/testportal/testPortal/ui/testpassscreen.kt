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
            Button(
                onClick = {
                    individualExamTestPassViewModel.fetchIndividualExamTestPasses(testPass.exam_id)
                    navController.navigate("individual_exam_test_pass")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text(text = "Join Now")
            }
        }
    }
}



//
//// TestPassScreen.kt
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.ssccgl.pinnacle.testportal.network.TestPass
//import com.ssccgl.pinnacle.testportal.viewmodel.TestPassViewModel
//
//@Composable
//fun TestPassScreen(
//    navController: NavHostController,
//    testPassViewModel: TestPassViewModel = viewModel(),
//    individualExamTestPassViewModel: IndividualExamTestPassViewModel = viewModel()
//) {
//    val testPassesState = testPassViewModel.testPasses.collectAsState()
//    val testPasses = testPassesState.value
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Test Pass by Categories",
//            fontWeight = FontWeight.Bold,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//        if (testPasses.isEmpty()) {
//            Text(text = "Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
//        } else {
//            LazyColumn {
//                items(testPasses) { testPass ->
//                    TestPassCard(
//                        testPass = testPass,
//                        navController = navController,
//                        individualExamTestPassViewModel = individualExamTestPassViewModel
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun TestPassCard(
//    testPass: TestPass,
//    navController: NavHostController,
//    individualExamTestPassViewModel: IndividualExamTestPassViewModel
//) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        elevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier
//                .background(Color(0xFFBBDEFB))
//                .padding(16.dp)
//        ) {
//            Text(
//                text = testPass.name,
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Features")
//            Text(text = "• Mock Test based on latest pattern")
//            Text(text = "• Previous year included pdf")
//            Text(text = "• Sectional based on different level")
//            Text(text = "• Chapterwise based on weightage")
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "₹${testPass.price}",
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF1B5E20),
//                    fontSize = 18.sp
//                )
//                Text(
//                    text = "₹${testPass.max_price}",
//                    color = Color(0xFFD32F2F),
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(start = 8.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(
//                onClick = {
//                    individualExamTestPassViewModel.fetchIndividualExamTestPasses(testPass.exam_id)
//                    navController.navigate("individual_exam_test_pass")
//                },
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
//            ) {
//                Text(text = "Join Now")
//            }
//        }
//    }
//}
//






//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.ssccgl.pinnacle.testportal.R
//import com.ssccgl.pinnacle.testportal.network.TestPass
//import com.ssccgl.pinnacle.testportal.viewmodel.TestPassViewModel
//
//@Composable
//fun TestPassScreen(
//    navController: NavHostController,
//    testPassViewModel: TestPassViewModel = viewModel(),
//    individualExamTestPassViewModel: IndividualExamTestPassViewModel = viewModel()
//) {
//    val testPassesState = testPassViewModel.testPasses.collectAsState()
//    val testPasses = testPassesState.value
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Test Pass by Categories",
//            fontWeight = FontWeight.Bold,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // New Section
//        TestSeriesInfoSection()
//
//        if (testPasses.isEmpty()) {
//            Text(text = "Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
//        } else {
//            LazyColumn {
//                items(testPasses) { testPass ->
//                    TestPassCard(
//                        testPass = testPass,
//                        navController = navController,
//                        individualExamTestPassViewModel = individualExamTestPassViewModel
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TestSeriesInfoSection() {
//    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
//        Text(
//            text = "Why take Pinnacle test series",
//            fontWeight = FontWeight.Bold,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_my_courses),
//                    contentDescription = null,
//                    modifier = Modifier.size(64.dp)
//                )
//                Text(text = "Latest Exam Patterns")
//                Text(text = "Based on real time exam interface")
//            }
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_course),
//                    contentDescription = null,
//                    modifier = Modifier.size(64.dp)
//                )
//                Text(text = "Save Test & Continue")
//                Text(text = "Save important Tests & Questions to revise or reattempt them later")
//            }
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_attempted_tests),
//                    contentDescription = null,
//                    modifier = Modifier.size(64.dp)
//                )
//                Text(text = "In-depth Performance Analysis")
//                Text(text = "Get Insights on your strength & weakness. All India Rank & Performance comparison with the Topper")
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        FAQSection()
//    }
//}
//
//@Composable
//fun FAQSection() {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        FAQItem(
//            question = "Can I attempt a test multiple times?",
//            answer = "A test can be attempted only once. However, you can refer to the Test Series e-book including the solutions as many times as you want."
//        )
//        FAQItem(
//            question = "Can I download the question paper with solutions after the test?",
//            answer = "No, you cannot download the paper; however, you can access the paper and its solutions by logging into your account on the website or on the App."
//        )
//        FAQItem(
//            question = "Do you also provide solutions for the questions?",
//            answer = "Yes, detailed solutions will be available to you after you finish the test. You will be able to access these solutions anytime after you have taken the test."
//        )
//        FAQItem(
//            question = "Will I get any report card after the test?",
//            answer = ""
//        )
//        FAQItem(
//            question = "Till when can I access the solutions and analysis of my attempted test?",
//            answer = ""
//        )
//        FAQItem(
//            question = "Will all of my attempted tests be available during the Pass validity period?",
//            answer = ""
//        )
//    }
//}
//
//@Composable
//fun FAQItem(question: String, answer: String) {
//    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
//        Text(
//            text = question,
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp
//        )
//        Text(
//            text = answer,
//            fontSize = 16.sp,
//            modifier = Modifier.padding(top = 4.dp)
//        )
//    }
//}
//
//@Composable
//fun TestPassCard(
//    testPass: TestPass,
//    navController: NavHostController,
//    individualExamTestPassViewModel: IndividualExamTestPassViewModel
//) {
//    Card(
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        elevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier
//                .background(Color(0xFFBBDEFB))
//                .padding(16.dp)
//        ) {
//            Text(
//                text = testPass.name,
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Features")
//            Text(text = "• Mock Test based on latest pattern")
//            Text(text = "• Previous year included pdf")
//            Text(text = "• Sectional based on different level")
//            Text(text = "• Chapterwise based on weightage")
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "₹${testPass.price}",
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF1B5E20),
//                    fontSize = 18.sp
//                )
//                Text(
//                    text = "₹${testPass.max_price}",
//                    color = Color(0xFFD32F2F),
//                    fontSize = 14.sp,
//                    modifier = Modifier.padding(start = 8.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(
//                onClick = {
//                    individualExamTestPassViewModel.fetchIndividualExamTestPasses(testPass.exam_id)
//                    navController.navigate("individual_exam_test_pass")
//                },
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
//            ) {
//                Text(text = "Join Now")
//            }
//        }
//    }
//}
//
