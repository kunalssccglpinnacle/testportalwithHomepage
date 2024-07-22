
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.network.AttemptedResponse
import com.ssccgl.pinnacle.testportal.network.TopRanker
import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavHostController,
    paperCode: String,
    emailId: String,
    examModeId: String,
    testSeriesId: String
) {
    val viewModel: ResultViewModel =
        viewModel(factory = ResultViewModelFactory(paperCode, emailId, examModeId, testSeriesId))
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
                        Toppers(it, it.TopRanker)
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
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Compare Sectional Summary", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    SectionSummaryCard(
                        title = "Score",
                        value = "${result.Score}/${result.TotalMarks}",
                        color = Color(0xFF6200EE), // Purple
                        subjects = result.Subjects.map { "${it.SubScore}/${it.SubTotalMarks} ${it.SubjectName}" }
                    )
                }
                item {
                    SectionSummaryCard(
                        title = "Accuracy",
                        value = "${result.Accuracy}%",
                        color = Color(0xFF00C853), // Green
                        subjects = result.Subjects.map { "${it.SubAccuracy} ${it.SubjectName}" }
                    )
                }
                item {
                    SectionSummaryCard(
                        title = "Attempted",
                        value = "${result.Attempted}/${result.TotalQuestions}",
                        color = Color(0xFFFFC107), // Amber
                        subjects = result.Subjects.map { "${it.SubAttempted}/${it.SubTotalQuestion} ${it.SubjectName}" }
                    )
                }
                item {
                    SectionSummaryCard(
                        title = "Time",
                        value = "${result.TotalTimeTaken}/${result.Totaltime}mins",
                        color = Color(0xFF00ACC1), // Teal
                        subjects = result.Subjects.map { "${it.SubTakingTime}/${it.SubTotalTime}mins ${it.SubjectName}" }
                    )
                }
            }
        }
    }
}

@Composable
fun SectionSummaryCard(title: String, value: String, color: Color, subjects: List<String>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(200.dp) // fixed width for the cards
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(color)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color)
                Spacer(modifier = Modifier.height(8.dp))
                Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                subjects.forEach { subject ->
                    Text(subject, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun Compare(result: AttemptedResponse) {
    var selectedComparison by remember { mutableStateOf("Score") }
    val scoreComparison = result.compare.firstOrNull() ?: return

    val data = when (selectedComparison) {
        "Score" -> listOf(
            BarData(scoreComparison.YourScore.toFloat(), "You"),
            BarData(scoreComparison.TopperScore.toFloat(), "Topper")
        )
        "Accuracy" -> listOf(
            BarData(scoreComparison.YourAccuracy.toFloat(), "You"),
            BarData(scoreComparison.TopperAccuracy.toFloat(), "Topper")
        )
        "Attempt" -> listOf(
            BarData(scoreComparison.YourCorrect.toFloat(), "You"), // Assuming YourCorrect as YourAttempt
            BarData(scoreComparison.TopperCorrect.toFloat(), "Topper") // Assuming TopperCorrect as TopperAttempt
        )
        "Correct" -> listOf(
            BarData(scoreComparison.YourCorrect.toFloat(), "You"),
            BarData(scoreComparison.TopperCorrect.toFloat(), "Topper")
        )
        "Incorrect" -> listOf(
            BarData(scoreComparison.YourWrong.toFloat(), "You"),
            BarData(scoreComparison.TopperWrong.toFloat(), "Topper")
        )
        "Time" -> listOf(
            BarData(scoreComparison.YourTime.toFloat(), "You"), // Time is typically in format HH:MM:SS and needs to be converted to a number
            BarData(scoreComparison.TopperTime.toFloat(), "Topper")
        )
        else -> emptyList()
    }

    val maxValue = when (selectedComparison) {
        "Score" -> scoreComparison.TotalScore.toFloat()
        "Accuracy" -> 100f
        "Attempt", "Correct", "Incorrect" -> result.TotalQuestions.toFloat()
        "Time" -> scoreComparison.TotalTime.toFloat()
        else -> 0f
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Compare", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                ComparisonButton("Score", selectedComparison) { selectedComparison = it }
                ComparisonButton("Accuracy", selectedComparison) { selectedComparison = it }
                ComparisonButton("Attempt", selectedComparison) { selectedComparison = it }
                ComparisonButton("Correct", selectedComparison) { selectedComparison = it }
                ComparisonButton("Incorrect", selectedComparison) { selectedComparison = it }
                ComparisonButton("Time", selectedComparison) { selectedComparison = it }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Graph
            BarChart(
                bars = data,
                maxValue = maxValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Composable
fun ComparisonButton(label: String, selected: String, onClick: (String) -> Unit) {
    val isSelected = selected == label
    val backgroundColor = if (isSelected) Color(0xFF4CAF50) else Color.White
    val contentColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick(label) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = contentColor)
    }
}

@Composable
fun BarChart(bars: List<BarData>, maxValue: Float, modifier: Modifier = Modifier) {
    val barWidth = 40.dp
    val spacing = 16.dp

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
    ) {
        bars.forEach { bar ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = spacing)
            ) {
                Box(
                    modifier = Modifier
                        .width(barWidth)
                        .height((bar.value / maxValue * 200).dp)
                        .background(Color.Green),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = bar.value.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Text(text = bar.label, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

data class BarData(val value: Float, val label: String)

@Composable
fun Toppers(result: AttemptedResponse, toppers: List<TopRanker>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Toppers", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            toppers.forEachIndexed { index, topRanker ->
                TopperItem(index + 1, topRanker, result.TotalMarks)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun TopperItem(rank: Int, topRanker: TopRanker, totalMarks: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .background(Color.Blue, shape = CircleShape)
        ) {
            Text(
                text = topRanker.Name.first().toString().uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "$rank. ${topRanker.Name}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${topRanker.RankerMarks}/$totalMarks",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}
