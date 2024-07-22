//package com.ssccgl.pinnacle.testportal.ui
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.google.gson.GsonBuilder
//import com.ssccgl.pinnacle.testportal.network.AttemptedResponse
//import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModel
//import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModelFactory
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ResultScreen(navController: NavHostController, paperCode: String, emailId: String, examModeId: String, testSeriesId: String) {
//    val viewModel: ResultViewModel = viewModel(factory = ResultViewModelFactory(paperCode, emailId, examModeId, testSeriesId))
//    val result by viewModel.result.observeAsState()
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Test Result") }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            if (result != null) {
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    contentPadding = PaddingValues(16.dp)
//                ) {
//                    items(result!!) { item ->
//                        ResultItem(item = item)
//                        Divider(modifier = Modifier.padding(vertical = 8.dp))
//                    }
//                }
//            } else {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ResultItem(item: AttemptedResponse) {
//    val gson = GsonBuilder().setPrettyPrinting().create()
//    val json = gson.toJson(item)
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//       // elevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = json,
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp),
//                overflow = TextOverflow.Clip
//            )
//        }
//    }
//}
//

package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.R
import com.ssccgl.pinnacle.testportal.network.AttemptedResponse
import com.ssccgl.pinnacle.testportal.network.TopRanker
import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(navController: NavHostController, paperCode: String, emailId: String, examModeId: String, testSeriesId: String) {
    val viewModel: ResultViewModel = viewModel(factory = ResultViewModelFactory(paperCode, emailId, examModeId, testSeriesId))
    val result by viewModel.result.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Result") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                if (result != null) {
                    val firstResult = result!!.firstOrNull()
                    firstResult?.let {
                        QuickSummary(it)
                        Spacer(modifier = Modifier.height(16.dp))
                        CompareSectionalSummary(it)
                        Spacer(modifier = Modifier.height(16.dp))
                        Compare(it)
                        Spacer(modifier = Modifier.height(16.dp))
                        Toppers(it.TopRanker)
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun QuickSummary(result: AttemptedResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Quick Summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            SummaryItem(
                icon = Icons.Default.Assessment,
                title = "Rank",
                value = "${result.Rank}/${result.TotalStudent}",
                color = Color.Red
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryItem(
                icon = Icons.Default.School,
                title = "Score",
                value = "${result.Score}/${result.TotalMarks}",
                color = Color(0xFF6200EE) // Purple
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryItem(
                icon = Icons.Default.Percent,
                title = "Percentile",
                value = "${result.Percentile}%",
                color = Color.Blue
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryItem(
                icon = Icons.Default.GpsFixed,
                title = "Accuracy",
                value = "${result.Accuracy}%",
                color = Color.Green
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryItem(
                icon = Icons.Default.CheckCircle,
                title = "Attempted",
                value = "${result.Attempted}/${result.TotalQuestions}",
                color = Color.Cyan
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Best Score", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        "${result.compare[0].TopperScore}/${result.TotalMarks}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Average Marks", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        "${result.averageMarks}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryItem(icon: ImageVector, title: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color, shape = CircleShape)
                .padding(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color.White)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun CompareSectionalSummary(result: AttemptedResponse) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Compare Sectional Summary", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            // Add Compare Sectional Summary items here using properties from result.Subjects or result.compare
        }
    }
}

@Composable
fun Compare(result: AttemptedResponse) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Compare", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            // Add Compare items here using properties from result.compare
        }
    }
}

@Composable
fun Toppers(toppers: List<TopRanker>) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Toppers", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            toppers.forEachIndexed { index, topRanker ->
                Text("${index + 1}. ${topRanker.Name} - Score: ${topRanker.RankerMarks}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

