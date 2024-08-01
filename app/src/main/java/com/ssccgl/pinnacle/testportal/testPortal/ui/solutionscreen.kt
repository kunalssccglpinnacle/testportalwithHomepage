package com.ssccgl.pinnacle.testportal.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssccgl.pinnacle.testportal.viewmodel.SolutionViewModel
import com.ssccgl.pinnacle.testportal.network.SolutionRequest
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import com.ssccgl.pinnacle.testportal.network.Detailsol
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionScreen(
    viewModel: SolutionViewModel = viewModel(),
    request: SolutionRequest
) {
    val data by viewModel.solutionData.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()
    val currentQuestionId by viewModel.currentQuestionId.observeAsState(1)
    val details = data.flatMap { it.details }
    var isHindi by remember { mutableStateOf(false) }
    var isDrawerOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchSolutionData(request)
    }

    BoxWithConstraints {
        val screenWidth = constraints.maxWidth

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Solution") },
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (isHindi) "HINDI" else "ENGLISH", modifier = Modifier.padding(end = 8.dp))
                            Switch(
                                checked = isHindi,
                                onCheckedChange = { isHindi = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.secondary,
                                    uncheckedThumbColor = Color.Green,
                                    checkedTrackColor = Color.Green.copy(alpha = 0.5f),
                                    uncheckedTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                                )
                            )
                            IconButton(onClick = { isDrawerOpen = true }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            var startDragX by remember { mutableStateOf(0f) }
            var endDragX by remember { mutableStateOf(0f) }

            Box(modifier = Modifier.fillMaxSize()) {
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

                                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        ColorCodedLabel(color = Color.Green, text = "Attempted:${currentQuestion.correct_count}")
                                        ColorCodedLabel(color = Color.Red, text = "Incorrect:${currentQuestion.incorrect_count} ")
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        ColorCodedLabel(color = Color.Gray, text = "Unattempted:${currentQuestion.unattempted_ques}")
                                        ColorCodedLabel(color = Color(0xFFFFA500), text = "Bookmark:${currentQuestion.bookmark_ques}")
                                    }

                                }
                                Spacer(modifier = Modifier.height(16.dp))

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
                                            details = currentQuestion,
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
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = isDrawerOpen,
                    enter = fadeIn(animationSpec = tween(500)),
                    exit = fadeOut(animationSpec = tween(500))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(with(LocalDensity.current) { (screenWidth / 2).toDp() })
                            .align(Alignment.CenterEnd)
                            .background(Color.White)
                            .clickable { isDrawerOpen = false }
                    ) {
                        DrawerContent(onCloseDrawer = { isDrawerOpen = false })
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Menu Item 1", modifier = Modifier.clickable { onCloseDrawer() })
        Spacer(modifier = Modifier.height(8.dp))
        Text("Menu Item 2", modifier = Modifier.clickable { onCloseDrawer() })
        Spacer(modifier = Modifier.height(8.dp))
        Text("Menu Item 3", modifier = Modifier.clickable { onCloseDrawer() })





    }


}

@Composable
fun ColorCodedLabel(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun OptionItem(
    option: String,
    optionValue: String,
    details: Detailsol,
    onSelectOption: (String) -> Unit
) {
    val backgroundColor = when {
        optionValue == details.correct_answer -> Color.Blue // Always green for correct answer
        optionValue == details.choose_answer && optionValue != details.correct_answer -> Color.Magenta // Selected but incorrect
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

