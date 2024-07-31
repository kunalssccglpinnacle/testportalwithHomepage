package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController

import com.ssccgl.pinnacle.testportal.viewmodel.MainViewModel
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(
    testSeriesId: String,
    paperCode: String,
    examModeId: Int,
    navController: NavHostController,
    viewModel: MainViewModel = viewModel()
) {
    val data by viewModel.data.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()
    val title by viewModel.title.observeAsState("")
    val paperCodeDetails by viewModel.paperCodeDetails.observeAsState()

    val details = data.flatMap { it.details }

    val currentQuestionId by viewModel.currentQuestionId.observeAsState(1)
    val selectedOption by viewModel.selectedOption.observeAsState("")
    val isDataDisplayed by viewModel.isDataDisplayed.observeAsState(false)

    val elapsedTimeMap = viewModel.elapsedTimeMap

    val remainingCountdown by viewModel.remainingCountdown.observeAsState(3600L)
    val countdownStarted by viewModel.countdownStarted.observeAsState(false)

    val startTimeMap = viewModel.startTimeMap
    val elapsedTime by viewModel.elapsedTime.observeAsState(0L)
    val displayElapsedTime by viewModel.displayElapsedTime.observeAsState("00:00:00")
    val displayCountdownTime by viewModel.displayCountdownTime.observeAsState("00:00:00")

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.75f

    val markedForReviewMap by viewModel.markedForReviewMap.observeAsState(emptyMap())
    val isMarkedForReview = markedForReviewMap[currentQuestionId] ?: false

    val answerTyp by viewModel.answerTyp.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    var swipeTriggered by remember { mutableStateOf(false) }


    LaunchedEffect(Pair(isDataDisplayed, currentQuestionId)) {
        if (isDataDisplayed) {
            val currentQuestion = details.find { it.qid == currentQuestionId }
            if (currentQuestion != null) {
                viewModel.saveCurrentQuestionState(currentQuestionId, selectedOption, elapsedTime)
                viewModel.initializeElapsedTime(currentQuestionId)
                viewModel.setSelectedOption(currentQuestionId)
            }
        }
    }

    LaunchedEffect(currentQuestionId, isDataDisplayed) {
        if (isDataDisplayed) {
            while (true) {
                viewModel.updateElapsedTime(currentQuestionId)
                delay(1000L)
            }
        }
    }

    LaunchedEffect(isDataDisplayed) {
        if (isDataDisplayed && !countdownStarted) {
            viewModel.startCountdown()
        }
    }

    LaunchedEffect(isDataDisplayed) {
        if (isDataDisplayed) {
            if (currentQuestionId == 1 && details.isNotEmpty()) {
                viewModel.moveToQuestion(details.first().qid)
            }
            if (!countdownStarted) {
                viewModel.startCountdown()
            }
        }
    }

    val draggableState = rememberDraggableState { delta ->
        if (abs(delta) > 50 && !swipeTriggered) {
            swipeTriggered = true
            if (delta > 0) {
                // Swipe right
                val currentQuestion = details.find { it.qid == currentQuestionId }
                if (currentQuestion != null && currentQuestionId > 1) {
                    viewModel.saveAnswer(
                        paperId = currentQuestion.qid,
                        option = viewModel.validateOption(selectedOption),
                        subject = currentQuestion.subject_id,
                        currentPaperId = currentQuestionId - 1,
                        remainingTime = formatTime(remainingCountdown),
                        singleTm = formatTime(elapsedTime),
                        saveType = "nxt",
                        answerStatus = if (isMarkedForReview) "4" else "1"
                    )
                    viewModel.moveToPreviousQuestion()
                }
            } else {
                // Swipe left
                val currentQuestion = details.find { it.qid == currentQuestionId }
                if (currentQuestion != null && currentQuestionId < details.maxOf { it.qid }) {
                    viewModel.saveAnswer(
                        paperId = currentQuestion.qid,
                        option = viewModel.validateOption(selectedOption),
                        subject = currentQuestion.subject_id,
                        currentPaperId = currentQuestionId + 1,
                        remainingTime = formatTime(remainingCountdown),
                        singleTm = formatTime(elapsedTime),
                        saveType = "nxt",
                        answerStatus = if (isMarkedForReview) "4" else "1"
                    )
                    viewModel.moveToNextQuestion()
                }
            }
            // Reset the flag after a short delay
            coroutineScope.launch {
                delay(300)
                swipeTriggered = false
            }
        }
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
                paperCodeDetails?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularButton(onClick = {}, text = "", answerType = 1)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Answered: ${it.answered_count}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularButton(onClick = {}, text = "", answerType = 2)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Not Answered: ${it.notanswered_count}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularButton(onClick = {}, text = "", answerType = 4)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Marked for Review and Answered: ${it.marked_answered_count}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularButton(onClick = {}, text = "", answerType = 3)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Marked for Review: ${it.marked_count}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularButton(onClick = {}, text = "", answerType = 0)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Not Visited: ${it.not_visited}")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (drawerState.isOpen) {
                    LazyColumn(
                        modifier = Modifier
                            .width(drawerWidth)  // Ensure LazyColumn takes full width of the drawer
                            .weight(1f)     // Make LazyColumn take up remaining space in the drawer
                    ) {
                        val buttonRows = details.chunked(5)
                        items(buttonRows) { row ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                row.forEach { detail ->
                                    val answerType = answerTyp[detail.qid] ?: 0

                                    CircularButton(
                                        onClick = {
                                            val currentTime = System.currentTimeMillis()
                                            val startTime =
                                                startTimeMap[currentQuestionId] ?: currentTime
                                            val elapsed = elapsedTimeMap[currentQuestionId] ?: 0L
                                            val newElapsedTime =
                                                elapsed + (currentTime - startTime) / 1000
                                            elapsedTimeMap[currentQuestionId] = newElapsedTime

                                            viewModel.saveAnswer(
                                                paperId = currentQuestionId,
                                                option = viewModel.validateOption(selectedOption),
                                                subject = detail.subject_id,
                                                currentPaperId = detail.qid,
                                                remainingTime = formatTime(remainingCountdown),
                                                singleTm = formatTime(newElapsedTime),
                                                saveType = "nav",
                                                answerStatus = if (isMarkedForReview) "4" else "1"
                                            )

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
                }

                Spacer(modifier = Modifier.height(16.dp)) // Add space between LazyColumn and Submit button

                Button(
                    onClick = {
                        showDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Align the button to the center horizontally
                ) {
                    Text("Submit", color = Color.White)
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(title) },
                        actions = {
                            IconButton(onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Drawer")
                            }
                        },
                    )
                },
                bottomBar = {
                    val currentQuestion = details.find { it.qid == currentQuestionId }
                    if (currentQuestion != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (currentQuestionId < details.maxOf { it.qid }) {
                                CustomButton(
                                    text = "Save & Next",
                                    onClick = {
                                        viewModel.saveAnswer(
                                            paperId = currentQuestion.qid,
                                            option = viewModel.validateOption(selectedOption),
                                            subject = currentQuestion.subject_id,
                                            currentPaperId = currentQuestionId + 1,
                                            remainingTime = formatTime(remainingCountdown),
                                            singleTm = formatTime(elapsedTime),
                                            saveType = "nxt",
                                            answerStatus = if (isMarkedForReview) "4" else "1"
                                        )
                                        viewModel.moveToNextQuestion()
                                    },
                                    backgroundColor = Color(0xFF6200EE),
                                    textColor = Color.White
                                )
                            }

                            // Clear Response Button
                            CustomButton(
                                text = "Clear Response",
                                onClick = {
                                    viewModel.clearResponse()
                                },
                                backgroundColor = Color.White,
                                textColor = Color.Black,
                                borderColor = Color.Black,
                                fontSize = 10
                            )

                            // Mark for Review Button
                            CustomButton(
                                text = if (isMarkedForReview) "Marked for Review" else "Mark for Review",
                                onClick = { viewModel.toggleMarkForReview(currentQuestionId) },
                                backgroundColor = if (isMarkedForReview) Color.Cyan else Color.Gray,
                                textColor = Color.White,
                                fontSize = 10
                            )
                        }
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .draggable(
                            state = draggableState,
                            orientation = Orientation.Horizontal,
                            onDragStopped = { /* Do nothing */ }
                        )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val tabTitles = data.flatMap { it.subjects }.map { it.subject_name }
                        val selectedTabIndex by viewModel.selectedTabIndex.observeAsState(0)

                        TabRow(selectedTabIndex = selectedTabIndex) {
                            tabTitles.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTabIndex == index,
                                    onClick = {
                                        viewModel.moveToSection(index)
                                    },
                                    text = { Text(title) }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Countdown: $displayCountdownTime",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Time: $displayElapsedTime",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

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
                                viewModel.setIsDataDisplayed(true)
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    LazyColumn(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        item {
                                            HtmlText(html = currentQuestion.question)
                                            Spacer(modifier = Modifier.height(16.dp))

                                            OptionItem(
                                                option = currentQuestion.option1,
                                                optionValue = "a",
                                                selectedOption = selectedOption,
                                                onSelectOption = { viewModel.updateSelectedOption(it) }
                                            )
                                            OptionItem(
                                                option = currentQuestion.option2,
                                                optionValue = "b",
                                                selectedOption = selectedOption,
                                                onSelectOption = { viewModel.updateSelectedOption(it) }
                                            )
                                            OptionItem(
                                                option = currentQuestion.option3,
                                                optionValue = "c",
                                                selectedOption = selectedOption,
                                                onSelectOption = { viewModel.updateSelectedOption(it) }
                                            )
                                            OptionItem(
                                                option = currentQuestion.option4,
                                                optionValue = "d",
                                                selectedOption = selectedOption,
                                                onSelectOption = { viewModel.updateSelectedOption(it) }
                                            )

                                            Spacer(modifier = Modifier.height(16.dp))
                                        }
                                    }
                                }
                            } else {
                                viewModel.setIsDataDisplayed(false)
                                Text(
                                    text = "Questions are loading...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            }
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {Text("Test Submission")},
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        viewModel.submit(navController)
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("No")
                }
            },
            text = {
                Column {
                    Text("Do you want to submit the test?")
                    Spacer(modifier = Modifier.height(8.dp))
                    paperCodeDetails?.let {
                        Text("Time Remaining: $displayCountdownTime")
                        Text("Answered: ${it.answered_count}")
                        Text("Not Answered: ${it.notanswered_count}")
                        Text("Marked for Review: ${it.marked_count}")
                        Text("Marked for Review and Answered: ${it.marked_answered_count}")
                        Text("Not Visited: ${it.not_visited}")
                    }
                }
            }
        )
    }
}