package com.ssccgl.pinnacle.testportal.testPortal.ui // Ensure package name matches directory structure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssccgl.pinnacle.testportal.viewmodel.SolutionViewModel
import com.ssccgl.pinnacle.testportal.network.SolutionRequest
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import com.ssccgl.pinnacle.testportal.network.AttemptedRequest
import com.ssccgl.pinnacle.testportal.ui.CircularButton
import com.ssccgl.pinnacle.testportal.ui.HtmlText
import com.ssccgl.pinnacle.testportal.ui.OptionItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionScreen(
    viewModel: SolutionViewModel = viewModel(),
    request: SolutionRequest,
    request1: AttemptedRequest
) {
    // State variables
    val data by viewModel.solutionData.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()
    val currentQuestionId by viewModel.currentQuestionId.observeAsState(1)
    val selectedOption by viewModel.selectedOption.observeAsState("")
    val details = data.flatMap { it.details }
    var isHindi by remember { mutableStateOf(false) }

    // Drawer state and coroutine scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.75f

    // Attempted response
    val attemptedResponse by viewModel.attemptedResponse.collectAsState()

    val userScoreDetail = attemptedResponse?.transformedData?.overall?.user
    val totalQuestions = userScoreDetail?.total_questions ?: 0
    val correctAnswers = userScoreDetail?.correct ?: 0
    val wrongAnswers = userScoreDetail?.wrong ?: 0

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Sectional View", "Full View")

    // Fetch data
    LaunchedEffect(Unit) {
        viewModel.fetchSolutionData(request)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchAttemptedData(request1)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.White,
        gesturesEnabled = true,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(drawerWidth)
                    .padding(16.dp)
            ) {
                // Display the total questions, correct, and wrong answers at the top
                Text("Total Questions: $totalQuestions")
                Text("Correct Answers: $correctAnswers")
                Text("Wrong Answers: $wrongAnswers")

                Spacer(modifier = Modifier.height(16.dp))

                // Tab Row for Sectional View and Full View
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }

                if (selectedTabIndex == 0) {
                    val subjects = data.flatMap { it.subjects }

                    LazyColumn(
                        modifier = Modifier
                            .width(drawerWidth)
                            .weight(1f)
                    ) {
                        items(subjects) { subject ->
                            var expanded by remember { mutableStateOf(false) }
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { expanded = !expanded },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(subject.subject_name)
                                    Icon(
                                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = null
                                    )
                                }
                                if (expanded) {
                                    val subjectQuestions = details.filter { it.subject_id == subject.sb_id }
                                    val buttonRows = subjectQuestions.chunked(5)
                                    buttonRows.forEach { row ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            row.forEach { detail ->
                                                val answerType = when (detail.answer_type) {
                                                    "Correct" -> 1
                                                    "Incorrect" -> 2
                                                    else -> 0
                                                }
                                                CircularButton(
                                                    onClick = {
                                                        viewModel.moveToQuestion(detail.qid)
                                                        coroutineScope.launch { drawerState.close() }
                                                    },
                                                    text = detail.qid.toString(),
                                                    answerType = answerType
                                                )
                                            }
                                        }
                                        Spacer(Modifier.padding(4.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .width(drawerWidth)
                            .weight(1f)
                    ) {
                        val buttonRows = details.chunked(5)
                        items(buttonRows) { row ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                row.forEach { detail ->
                                    val answerType = when (detail.answer_type) {
                                        "Correct" -> 1
                                        "Incorrect" -> 2
                                        else -> 0
                                    }
                                    CircularButton(
                                        onClick = {
                                            viewModel.moveToQuestion(detail.qid)
                                            coroutineScope.launch { drawerState.close() }
                                        },
                                        text = detail.qid.toString(),
                                        answerType = answerType
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Solution") },
                        actions = {
                            IconButton(onClick = { isHindi = !isHindi }) {
                                Text(if (isHindi) "EN" else "HI")
                            }
                            IconButton(onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
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
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                val tabTitles = data.flatMap { it.subjects }.map { it.subject_name }
                                val selectedTabIndex by viewModel.selectedTabIndex.observeAsState(0)
                                TabRow(selectedTabIndex = selectedTabIndex) {
                                    tabTitles.forEachIndexed { index, title ->
                                        Tab(
                                            selected = selectedTabIndex == index,
                                            onClick = { viewModel.moveToSection(index) },
                                            text = { Text(title) }
                                        )
                                    }
                                }
                                LazyColumn(modifier = Modifier.weight(1f)) {
                                    item {
                                        val questionHtml =
                                            if (isHindi) currentQuestion.hindi_question else currentQuestion.question
                                        HtmlText(html = questionHtml ?: "Question not available")
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }

                                    items(
                                        listOf(
                                            Pair(
                                                "a.",
                                                if (isHindi) currentQuestion.hindi_option1 else currentQuestion.option1
                                            ),
                                            Pair(
                                                "b.",
                                                if (isHindi) currentQuestion.hindi_option2 else currentQuestion.option2
                                            ),
                                            Pair(
                                                "c.",
                                                if (isHindi) currentQuestion.hindi_option3 else currentQuestion.option3
                                            ),
                                            Pair(
                                                "d.",
                                                if (isHindi) currentQuestion.hindi_option4 else currentQuestion.option4
                                            )
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
                                        val solutionHtml =
                                            if (isHindi) currentQuestion.hindi_solution else currentQuestion.solution
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
                            Text(
                                text = "Questions are loading...",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }
            }
        }
    )
}
