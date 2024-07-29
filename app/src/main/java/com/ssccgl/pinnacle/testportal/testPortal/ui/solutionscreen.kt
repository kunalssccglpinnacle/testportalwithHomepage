//
//package com.ssccgl.pinnacle.testportal.ui
//
//import android.text.Html
//import android.widget.TextView
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.detectHorizontalDragGestures
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.ssccgl.pinnacle.testportal.viewmodel.SolutionViewModel
//import com.ssccgl.pinnacle.testportal.network.SolutionRequest
//import kotlinx.coroutines.launch
//import androidx.compose.foundation.lazy.items
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.navigation.NavHostController
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SolutionScreen(
//    viewModel: SolutionViewModel,
//    request: SolutionRequest
//) {
//    val data by viewModel.solutionData.observeAsState(emptyList())
//    val error by viewModel.error.observeAsState()
//    val currentQuestionId by viewModel.currentQuestionId.observeAsState(1)
//    val selectedOption by viewModel.selectedOption.observeAsState("")
//    val details = data.flatMap { it.details }
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchSolutionData(request)
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Solution") },
//                actions = {
//                    IconButton(onClick = {
//                        // Add your action here
//                    }) {
//                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        var startDragX by remember { mutableStateOf(0f) }
//        var endDragX by remember { mutableStateOf(0f) }
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .pointerInput(Unit) {
//                    detectHorizontalDragGestures(
//                        onDragStart = { startDragX = it.x },
//                        onDragEnd = {
//                            val dragDistance = endDragX - startDragX
//                            if (dragDistance > 50) { // Swipe right
//                                viewModel.moveToPreviousQuestion()
//                            } else if (dragDistance < -50) { // Swipe left
//                                viewModel.moveToNextQuestion()
//                            }
//                        },
//                        onHorizontalDrag = { change, dragAmount ->
//                            endDragX = change.position.x
//                        }
//                    )
//                }
//        ) {
//            if (error != null) {
//                Text(
//                    text = error ?: "Unknown error",
//                    color = MaterialTheme.colorScheme.error,
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.padding(16.dp)
//                )
//            } else {
//                val currentQuestion = details.find { it.qid == currentQuestionId }
//
//                if (currentQuestion != null) {
//                    Log.d("SolutionScreen", "Current question ID: ${currentQuestion.qid}, Correct answer: ${currentQuestion.correctAnswer}")
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        verticalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        LazyColumn(
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            item {
//                                HtmlText(html = currentQuestion.question)
//                                Spacer(modifier = Modifier.height(16.dp))
//                            }
//
//                            items(
//                                listOf(
//                                    Pair("a.", currentQuestion.option1),
//                                    Pair("b.", currentQuestion.option2),
//                                    Pair("c.", currentQuestion.option3),
//                                    Pair("d.", currentQuestion.option4)
//                                )
//                            ) { option ->
//                                OptionItem(
//                                    option = option.second,
//                                    optionValue = option.first,
//                                    selectedOption = selectedOption,
//                                    correctAnswer = currentQuestion.correctAnswer ?: "",
//                                    onSelectOption = { viewModel.updateSelectedOption(it) }
//                                )
//                            }
//
//                            item {
//                                HtmlText(html = currentQuestion.solution)
//                            }
//                        }
//
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Button(
//                                onClick = {
//                                    viewModel.moveToPreviousQuestion()
//                                }
//                            ) {
//                                Text("Previous")
//                            }
//
//                            Button(
//                                onClick = {
//                                    viewModel.moveToNextQuestion()
//                                }
//                            ) {
//                                Text("Next")
//                            }
//                        }
//                    }
//                } else {
//                    Log.d("SolutionScreen", "Current question is null.")
//                    Text(
//                        text = "Questions are loading...",
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun OptionItem(
//    option: String,
//    optionValue: String,
//    selectedOption: String,
//    correctAnswer: String,
//    onSelectOption: (String) -> Unit
//) {
//    val backgroundColor = when {
//        optionValue == correctAnswer && optionValue == selectedOption -> Color.Green // Correct and selected
//        optionValue == selectedOption && optionValue != correctAnswer -> Color.Red // Selected but incorrect
//        optionValue == correctAnswer -> Color.Green.copy(alpha = 0.3f) // Correct but not selected
//        else -> Color.Transparent
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(backgroundColor)
//            .clickable { onSelectOption(optionValue) }
//            .padding(8.dp)
//    ) {
//        Text(
//            text = optionValue,
//            fontSize = 18.sp,
//            modifier = Modifier.padding(end = 8.dp)
//        )
//        HtmlText(html = option)
//    }
//}
//
package com.ssccgl.pinnacle.testportal.ui

import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssccgl.pinnacle.testportal.viewmodel.SolutionViewModel
import com.ssccgl.pinnacle.testportal.network.SolutionRequest
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionScreen(
    viewModel: SolutionViewModel = viewModel(),
    request: SolutionRequest
) {
    val data by viewModel.solutionData.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()
    val currentQuestionId by viewModel.currentQuestionId.observeAsState(1)
    val selectedOption by viewModel.selectedOption.observeAsState("")
    val details = data.flatMap { it.details }
    var isHindi by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchSolutionData(request)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solution") },
                actions = {
                    IconButton(onClick = { isHindi = !isHindi }) {
                        Text(if (isHindi) "EN" else "HI")
                    }
                    IconButton(onClick = { /* Add your action here */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        var startDragX by remember { mutableStateOf(0f) }
        var endDragX by remember { mutableStateOf(0f) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { startDragX = it.x },
                        onDragEnd = {
                            val dragDistance = endDragX - startDragX
                            if (dragDistance > 50) {
                                viewModel.moveToPreviousQuestion()
                            } else if (dragDistance < -50) {
                                viewModel.moveToNextQuestion()
                            }
                        },
                        onHorizontalDrag = { change, _ -> endDragX = change.position.x }
                    )
                }
        ) {
            if (error != null) {
                Text(
                    text = error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                val currentQuestion = details.find { it.qid == currentQuestionId }
                if (currentQuestion != null) {
                    Log.d("SolutionScreen", "Current question ID: ${currentQuestion.qid}, Correct answer: ${currentQuestion.correct_answer ?: "null"}")
                    Log.d("SolutionScreen", "Displaying question: ${if (isHindi) currentQuestion.hindi_question else currentQuestion.question}")
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            item {
                                val questionHtml = if (isHindi) currentQuestion.hindi_question else currentQuestion.question
                                HtmlText(html = questionHtml ?: "Question not available")
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            items(
                                listOf(
                                    Pair("a.", if (isHindi) currentQuestion.hindi_option1 else currentQuestion.option1),
                                    Pair("b.", if (isHindi) currentQuestion.hindi_option2 else currentQuestion.option2),
                                    Pair("c.", if (isHindi) currentQuestion.hindi_option3 else currentQuestion.option3),
                                    Pair("d.", if (isHindi) currentQuestion.hindi_option4 else currentQuestion.option4)
                                )
                            ) { option ->
                                OptionItem(
                                    option = option.second ?: "Option not available",
                                    optionValue = option.first,
                                    selectedOption = selectedOption,
                                    correct_answer = currentQuestion.correct_answer ?: "",
                                    onSelectOption = { viewModel.updateSelectedOption(it) }
                                )
                            }

                            item {
                                val solutionHtml = if (isHindi) currentQuestion.hindi_solution else currentQuestion.solution
                                HtmlText(html = solutionHtml ?: "Solution not available")
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = { viewModel.moveToPreviousQuestion() }) {
                                Text("Previous")
                            }

                            Button(onClick = { viewModel.moveToNextQuestion() }) {
                                Text("Next")
                            }
                        }
                    }
                } else {
                    Log.d("SolutionScreen", "Current question is null.")
                    Text(
                        text = "Questions are loading...",
                        style = MaterialTheme.typography.bodyLarge,
                       // modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun OptionItem(
    option: String,
    optionValue: String,
    selectedOption: String,
    correct_answer: String,
    onSelectOption: (String) -> Unit
) {
    val backgroundColor = when {
        optionValue == correct_answer && optionValue == selectedOption -> Color.Green // Correct and selected
        optionValue == selectedOption && optionValue != correct_answer -> Color.Red // Selected but incorrect
        optionValue == correct_answer -> Color.Green.copy(alpha = 0.3f) // Correct but not selected
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onSelectOption(optionValue) }
            .padding(8.dp)
    ) {
        Text(
            text = optionValue,
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 8.dp)
        )
        HtmlText(html = option)
    }
}

//@Composable
//fun HtmlText3(html: String) {
//    val context = LocalContext.current
//    AndroidView(
//        factory = {
//            TextView(context).apply {
//                text = android.text.Html.fromHtml(html)
//            }
//        },
//        update = {
//            it.text = android.text.Html.fromHtml(html)
//        }
//    )
//}
