package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.network.AttemptedResponse
import com.ssccgl.pinnacle.testportal.network.Graph
import com.ssccgl.pinnacle.testportal.network.TopRanker
import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModelFactory
import kotlin.math.roundToInt

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
                result?.firstOrNull()?.let { firstResult ->
                    QuickSummary(firstResult)
                    Spacer(modifier = Modifier.height(16.dp))
                    CompareSectionalSummary(firstResult)
                    Spacer(modifier = Modifier.height(16.dp))
                    Compare(firstResult)
                    Spacer(modifier = Modifier.height(16.dp))
                    MarksDistribution(firstResult.graph)
                    Spacer(modifier = Modifier.height(16.dp))
                    Toppers(firstResult, firstResult.TopRanker)
                } ?: Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun MarksDistribution(graph: List<Graph>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Marks Distribution", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Blue)
            Spacer(modifier = Modifier.height(8.dp))
            LineGraph(
                labels = graph.firstOrNull()?.label ?: emptyList(),
                data = graph.firstOrNull()?.data ?: emptyList()
            )
        }
    }
}

@Composable
fun LineGraph(labels: List<String>, data: List<Int>) {
    val maxValue = data.maxOrNull()?.toFloat() ?: 1f
    val avgValue = data.average().toFloat()
    val markerValue = 2.5f
    val yAxisLabel = "No. of Students"
    val xAxisLabel = "Marks"

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val spacing = size.width / (labels.size - 1)
            val stepHeight = size.height / maxValue

            // Draw grid lines and labels
            for (i in 0..5) {
                val y = size.height - (stepHeight * i * maxValue / 5)
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1f,
                    cap = StrokeCap.Round,
                )
                drawContext.canvas.nativeCanvas.drawText(
                    (maxValue / 5 * i).roundToInt().toString(),
                    0f,
                    y - 4.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = Color.Gray.toArgb()
                        textSize = 12.sp.toPx()
                    }
                )
            }

            for (i in labels.indices) {
                val x = i * spacing
                drawContext.canvas.nativeCanvas.drawText(
                    labels[i],
                    x,
                    size.height + 20.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = Color.Gray.toArgb()
                        textSize = 12.sp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }

            // Draw lines
            for (i in 1 until data.size) {
                val x1 = (i - 1) * spacing
                val y1 = size.height - (data[i - 1] * stepHeight)
                val x2 = i * spacing
                val y2 = size.height - (data[i] * stepHeight)

                drawLine(
                    color = Color.Cyan,
                    start = Offset(x1, y1),
                    end = Offset(x2, y2),
                    strokeWidth = 4.dp.toPx()
                )
            }

            // Draw points
            for (i in data.indices) {
                val x = i * spacing
                val y = size.height - (data[i] * stepHeight)

                drawCircle(
                    color = Color.Cyan,
                    radius = 4.dp.toPx(),
                    center = Offset(x, y)
                )

                if (i == 0) {
                    drawContext.canvas.nativeCanvas.drawText(
                        "You are here: $markerValue",
                        x,
                        y - 10.dp.toPx(),
                        android.graphics.Paint().apply {
                            color = Color.Black.toArgb()
                            textSize = 12.sp.toPx()
                            textAlign = android.graphics.Paint.Align.LEFT
                        }
                    )
                } else if (i == 4) {
                    drawContext.canvas.nativeCanvas.drawText(
                        "Average ${"%.2f".format(avgValue)}",
                        x,
                        y - 10.dp.toPx(),
                        android.graphics.Paint().apply {
                            color = Color.Black.toArgb()
                            textSize = 12.sp.toPx()
                            textAlign = android.graphics.Paint.Align.LEFT
                        }
                    )
                }
            }
        }

        // Draw Y-axis label
        Text(
            text = yAxisLabel,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .rotate(270f)
                .padding(8.dp)
        )

        // Draw X-axis label
        Text(
            text = xAxisLabel,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        )
    }
}
data class GraphData(val label: List<String>, val data: List<Int>)

@Composable
fun QuickSummary(result: AttemptedResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
                        "${result.compare.getOrNull(0)?.TopperScore ?: 0}/${result.TotalMarks}",
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
            BarData(scoreComparison.YourAccuracy.toFloatOrNull() ?: 0f, "You"),
            BarData(scoreComparison.TopperAccuracy.toFloatOrNull() ?: 0f, "Topper")
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
//        "Time" -> {
//            val yourTimeInSeconds = convertTimeToSeconds(scoreComparison.YourTime)
//            val topperTimeInSeconds = convertTimeToSeconds(scoreComparison.TopperTime)
//            listOf(
//                BarData(yourTimeInSeconds.toFloat(), "You"),
//                BarData(topperTimeInSeconds.toFloat(), "Topper")
//            )
//        }
        else -> emptyList()
    }

    val maxValue = when (selectedComparison) {
        "Score" -> scoreComparison.TotalScore.toFloat()
        "Accuracy" -> 100f
        "Attempt", "Correct", "Incorrect" -> result.TotalQuestions.toFloat()
        "Time" -> {
            val totalSeconds = convertTimeToSeconds(scoreComparison.TotalTime.toString())
            if (totalSeconds > 0) totalSeconds.toFloat() else 1f
        }
        else -> 0f
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Compare", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Buttons
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ComparisonButton("Score", selectedComparison) { selectedComparison = it }
                    ComparisonButton("Accuracy", selectedComparison) { selectedComparison = it }
                    ComparisonButton("Attempt", selectedComparison) { selectedComparison = it }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ComparisonButton("Correct", selectedComparison) { selectedComparison = it }
                    ComparisonButton("Incorrect", selectedComparison) { selectedComparison = it }
                    //ComparisonButton("Time", selectedComparison) { selectedComparison = it }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Graph
            BarChart(
                bars = data,
                maxValue = maxValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}

@Composable
fun ComparisonButton(label: String, selected: String, onClick: (String) -> Unit) {
    val isSelected = selected == label
    val backgroundColor = if (isSelected) Color(0xFF4CAF50) else Color.White
    val contentColor = if (isSelected) Color.White else Color.Black
    val borderColor = if (isSelected) Color(0xFF4CAF50) else Color.Black

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(16.dp))
            .clickable { onClick(label) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = contentColor)
    }
}

fun convertTimeToSeconds(time: String): Int {
    return try {
        val parts = time.split(":").map { it.toInt() }
        parts[0] * 3600 + parts[1] * 60 + parts[2]
    } catch (e: Exception) {
        // If there's an error in parsing the time, return 0 as a fallback
        0
    }
}

@Composable
fun BarChart(bars: List<BarData>, maxValue: Float, modifier: Modifier = Modifier) {
    val barWidth = 120.dp
    val spacing = 32.dp
    val graphHeight = 250.dp

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stepSize = maxValue / 5
            val stepHeight = size.height / 5

            // Draw grid lines and labels
            for (i in 0..5) {
                val y = size.height - (stepHeight * i)
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1f,
                    cap = StrokeCap.Round,
                )
                drawContext.canvas.nativeCanvas.drawText(
                    (stepSize * i).roundToInt().toString(),
                    0f,
                    y - 4.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = Color.Gray.toArgb()
                        textSize = 12.sp.toPx()
                    }
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
        ) {
            bars.forEach { bar ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = spacing)
                ) {
                    Box(
                        modifier = Modifier
                            .width(barWidth)
                            .height((bar.value / maxValue * graphHeight.value).dp)
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
}

data class BarData(val value: Float, val label: String)

@Composable
fun Toppers(result: AttemptedResponse, toppers: List<TopRanker>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Toppers", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            toppers.forEachIndexed { index, topRanker ->
                TopperItemCard(index + 1, topRanker, result.TotalMarks)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun TopperItemCard(rank: Int, topRanker: TopRanker, totalMarks: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        TopperItem(rank, topRanker, totalMarks)
    }
}

@Composable
fun TopperItem(rank: Int, topRanker: TopRanker, totalMarks: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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

