
package com.ssccgl.pinnacle.testportal.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
            NavigationHost(navController = navController, homeViewModel = homeViewModel, loginData = null)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TestPortalScreen(navController: NavHostController, viewModel: TestPortalViewModel = viewModel()) {
    Scaffold {
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
            delay(3000) // Delay for 1.5 seconds
            currentIndex = (currentIndex + 1) % imageResIds.size
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp), // Set height to ensure dots visibility
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(imageResIds) { index, imageResId ->
                    if (index == currentIndex) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = imageResId),
                                contentDescription = "Carousel Image",
                                contentScale = ContentScale.Crop, // Adjust image scaling
                                modifier = Modifier
                                    .size(200.dp) // Adjust image size to match screenshot
                                    .padding(horizontal = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Mock Tests, PYP, Chapter wise, Sectional Tests, result oriented",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    }
                }
            }
        }

        // Dots indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            imageResIds.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(2.dp)
                        .background(
                            color = if (index == currentIndex) Color.Black else Color.Gray,
                            shape = CircleShape
                        )
                )
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
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                ExamCategoryCard(
                    imageRes = R.drawable.ssc_logo_final,
                    text = "SSC Exams",
                    onClick = { navController.navigate("test_pass") }
                )
            }
            item {
                ExamCategoryCard(
                    imageRes = R.drawable.img_1,
                    text = "Delhi Police Exams",
                    onClick = { navController.navigate("test_pass") }
                )
            }
            item {
                ExamCategoryCard(
                    imageRes = R.drawable.railway_logo_final,
                    text = "Railway Exams",
                    onClick = { navController.navigate("test_pass") }
                )
            }
        }
    }
}

@Composable
fun ExamCategoryCard(imageRes: Int, text: String, onClick: () -> Unit) {
    CustomCard(
        imageRes = imageRes,
        text = text,
        onClick = onClick,
        cardWidth = 160.dp, // Reduced size
        cardHeight = 160.dp, // Reduced size
        imageSize = 64.dp // Reduced size
    )
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
            CustomRadioButtonCard(
                text = "SSC",
                selectedCategory = selectedCategory,
                onClick = { selectedCategory = it }
            )
            CustomRadioButtonCard(
                text = "Delhi Police",
                selectedCategory = selectedCategory,
                onClick = { selectedCategory = it }
            )
            CustomRadioButtonCard(
                text = "Railway",
                selectedCategory = selectedCategory,
                onClick = { selectedCategory = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val tests = when (selectedCategory) {
            "SSC" -> listOf("SSC CGL Tier 1", "SSC CGL Tier 2", "SSC CHSL Tier 1", "SSC CHSL Tier 2", "SSC MTS Tier 1")
            "Delhi Police" -> listOf("Delhi Police Constable", "Delhi Police Head Constable", "Delhi Police SI")
            "Railway" -> listOf("RRB NTPC", "RRB Group D", "RRB ALP")
            else -> emptyList()
        }

        tests.chunked(2).forEach { rowTests ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                rowTests.forEach { test ->
                    TestCard(testName = test) { /* Handle test card click */ }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "View All ->",
            fontSize = 14.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.clickable { /* Handle view all click */ }
        )
    }
}

@Composable
fun CustomRadioButtonCard(
    text: String,
    selectedCategory: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(50.dp) // Reduced size
            .clickable { onClick(text) }
            .background(
                color = if (selectedCategory == text) Color.Blue else Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selectedCategory == text) Color.White else Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun TestCard(testName: String, onClick: () -> Unit) {
    CustomCard(
        imageRes = R.drawable.railway_logo_final,
        text = testName,
        onClick = onClick,
        cardWidth = 160.dp, // Reduced size
        cardHeight = 160.dp, // Reduced size
        imageSize = 64.dp // Reduced size
    )
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

@Composable
fun TestCategoryScreen() {
    var selectedCategory by remember { mutableStateOf("SSC") }
    val categories = listOf("SSC", "Delhi Police", "Railway")

    val categoryToTests = mapOf(
        "SSC" to listOf("SSC CGL Tier 1", "SSC CGL Tier 2", "SSC CHSL Tier 1", "SSC CHSL Tier 2", "SSC MTS Tier 1", "SSC MTS Tier 1"),
        "Delhi Police" to listOf("Delhi Police Constable", "Delhi Police Head Constable", "Delhi Police SI"),
        "Railway" to listOf("RRB NTPC", "RRB Group D", "RRB ALP", "RRB ALP")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        categories.forEach { category ->
            CustomRadioButtonCard(
                text = category,
                selectedCategory = selectedCategory,
                onClick = { selectedCategory = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val tests = categoryToTests[selectedCategory] ?: emptyList()
        tests.forEach { test ->
            TestCard(
                testName = test,
                onClick = { /* Handle test card click */ }
            )
        }
    }
}

@Composable
fun CustomCard(
    imageRes: Int,
    text: String,
    onClick: () -> Unit,
    cardWidth: Dp,
    cardHeight: Dp,
    imageSize: Dp
) {
    Card(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
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
                modifier = Modifier.size(imageSize)
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

@Preview(showBackground = true)
@Composable
fun PreviewTestCategoryScreen() {
    TestCategoryScreen()
}
