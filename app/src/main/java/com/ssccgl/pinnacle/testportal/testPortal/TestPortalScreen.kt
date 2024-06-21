
package com.ssccgl.pinnacle.testportal.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssccgl.pinnacle.testportal.R
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.TestPortalViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestPortalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val homeViewModel: HomeViewModel = viewModel()
            NavigationHost(navController = navController, homeViewModel = homeViewModel)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TestPortalScreen(navController: NavHostController, viewModel: TestPortalViewModel = viewModel()) {
    Scaffold(

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            HeadingSection()
            Spacer(modifier = Modifier.height(16.dp))
            AutoScrollableCarousel(
                imageResIds = listOf(
                    R.drawable.s_1,
                    R.drawable.s_2,
                    R.drawable.s_3,
                    R.drawable.s_4
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            ExamCategorySection(navController)
            Spacer(modifier = Modifier.height(16.dp))
            AllTestsSection()
            Spacer(modifier = Modifier.height(16.dp))
            PinnacleTestPassSection()
        }
    }
}

@Composable
fun HeadingSection() {
    Column {
        Text(
            text = "Test Pass",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Get online test series based on latest TCS pattern with advanced features",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun AutoScrollableCarousel(imageResIds: List<Int>) {
    var currentIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = currentIndex) {
        coroutineScope.launch {
            delay(3000) // Delay for 3 seconds
            currentIndex = (currentIndex + 1) % imageResIds.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(imageResIds) { index, imageResId ->
                if (index == currentIndex) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "Carousel Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ExamCategorySection(navController: NavHostController) {
    Column {
        Text(
            text = "Select Your Exam Category",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Click to view all test series in the exam",
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ExamCategoryCard(
                imageRes = R.drawable.ssc_logo_final,
                text = "SSC Exams",
                onClick = { navController.navigate("test_pass") }
            )
            ExamCategoryCard(
                imageRes = R.drawable.ssc_logo_final,
                text = "Delhi Police Exams",
                onClick = { /* Handle click */ }
            )
            ExamCategoryCard(
                imageRes = R.drawable.railway_logo_final,
                text = "Railway Exams",
                onClick = { /* Handle click */ }
            )
        }
    }
}

@Composable
fun ExamCategoryCard(imageRes: Int, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = text,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AllTestsSection() {
    Column {
        Text(
            text = "All Tests Series and Mock tests",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Click to view test series and mock tests for all exams",
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        var selectedCategory by remember { mutableStateOf("SSC") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TestCategoryRadioButton("SSC", selectedCategory) { selectedCategory = it }
            TestCategoryRadioButton("Delhi Police", selectedCategory) { selectedCategory = it }
            TestCategoryRadioButton("Railway", selectedCategory) { selectedCategory = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TestCard("SSC CGL Tier 1") { /* Handle click */ }
            TestCard("SSC CGL Tier 2") { /* Handle click */ }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TestCard("SSC CHSL Tier 1") { /* Handle click */ }
            TestCard("SSC CHSL Tier 2") { /* Handle click */ }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "View All ->",
            fontSize = 14.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.clickable { /* Handle view all click */ }
        )
    }
}

@Composable
fun TestCategoryRadioButton(text: String, selectedCategory: String, onClick: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick(text) }
    ) {
        RadioButton(
            selected = selectedCategory == text,
            onClick = { onClick(text) }
        )
        Text(text = text)
    }
}

@Composable
fun TestCard(testName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ssc_logo_final),
                contentDescription = testName,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = testName,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PinnacleTestPassSection() {
    Column {
        Text(
            text = "Pinnacle Test Pass",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "In this test pass SSC exams are covered currently. In due course we will add other exams category also which are showing here. For the moment those students who are preparing for SSC CGL Tier 2, SSC CHSL, SSC MTS, Delhi Police, Steno, SSC GD students join Pinnacle test pass. In a single test pass students will get access of all test series of SSC exams. Each Test series has many tests. Like SSC CGL Tier 2 test series has 80 mock tests based on TCS pattern and previous year papers conducted by TCS are also included.",
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Test Pass includes",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(text = "• Mock tests based on TCS pattern")
        Text(text = "• TCS Previous year papers")
        Text(text = "• Sectional Tests")
        Text(text = "• Chapter wise Tests")
        Text(text = "• Misc. Tests")
    }
}