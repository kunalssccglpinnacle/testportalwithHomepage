//package com.ssccgl.pinnacle.testportal.viewmodel
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.font.FontWeight
//import com.ssccgl.pinnacle.testcheck_2.HtmlText
//import com.ssccgl.pinnacle.testportal.repository.TestRepository
//import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DataScreen(
//    testSeriesId: String,
//    paperCode: String,
//    examModeId: Int,
//    viewModel: MainViewModel = viewModel(
//        factory = MainViewModelFactory(paperCode, "anshulji100@gmail.com", examModeId.toString(), testSeriesId, TestRepository(RetrofitInstance.api))
//    )
//) {
//    val data by viewModel.data.observeAsState(emptyList())
//    val error by viewModel.error.observeAsState()
//
//    val details = data.flatMap { it.details }
//
//    val currentQuestionId by viewModel.currentQuestionId.observeAsState(1)
//    val selectedOption by viewModel.selectedOption.observeAsState("")
//    val isDataDisplayed by viewModel.isDataDisplayed.observeAsState(false)
//
//    val selectedOptions = viewModel.selectedOptions
//    val elapsedTimeMap = viewModel.elapsedTimeMap
//
//    val remainingCountdown by viewModel.remainingCountdown.observeAsState(3600L)
//    val countdownStarted by viewModel.countdownStarted.observeAsState(false)
//
//    val startTimeMap = viewModel.startTimeMap
//    val elapsedTime by viewModel.elapsedTime.observeAsState(0L)
//    val displayTime by viewModel.displayTime.observeAsState("00:00")
//
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val coroutineScope = rememberCoroutineScope()
//
//    LaunchedEffect(Pair(isDataDisplayed, currentQuestionId)) {
//        if (isDataDisplayed) {
//            val currentQuestion = details.find { it.question_id == currentQuestionId }
//            if (currentQuestion != null) {
//                viewModel.saveCurrentQuestionState(currentQuestionId, selectedOption, elapsedTime)
//                viewModel.initializeElapsedTime(currentQuestionId)
//                viewModel.setSelectedOption(currentQuestionId)
//            }
//        }
//    }
//
//    LaunchedEffect(currentQuestionId, isDataDisplayed) {
//        if (isDataDisplayed) {
//            while (true) {
//                viewModel.updateElapsedTime(currentQuestionId)
//                delay(1000L)
//            }
//        }
//    }
//
//    LaunchedEffect(isDataDisplayed) {
//        if (isDataDisplayed && !countdownStarted) {
//            viewModel.startCountdown()
//        }
//    }
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        scrimColor = Color.White.copy(alpha = 0.5f),
//        gesturesEnabled = true,
//        modifier = Modifier.fillMaxWidth(),
//        drawerContent = {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(16.dp)
//            ) {
//                val buttonRows = details.chunked(5)
//                items(buttonRows) { row ->
//                    Row(
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        row.forEach { detail ->
//                            CircularButton(
//                                onClick = {
//                                    val currentTime = System.currentTimeMillis()
//                                    val startTime = startTimeMap[currentQuestionId] ?: currentTime
//                                    val elapsed = elapsedTimeMap[currentQuestionId] ?: 0L
//                                    val newElapsedTime = elapsed + (currentTime - startTime) / 1000
//                                    elapsedTimeMap[currentQuestionId] = newElapsedTime
//
//                                    viewModel.saveAnswer(
//                                        paperId = currentQuestionId,
//                                        option = selectedOption.ifEmpty { "" },
//                                        subject = detail.subject_id,
//                                        currentPaperId = currentQuestionId,
//                                        remainingTime = formatTime(remainingCountdown),
//                                        singleTm = formatTime(newElapsedTime) // Save time in seconds
//                                    )
//
//                                    viewModel.moveToQuestion(detail.question_id)
//                                    coroutineScope.launch { drawerState.close() }
//                                },
//                                text = detail.question_id.toString()
//                            )
//                        }
//                    }
//                    Spacer(Modifier.padding(4.dp))
//                }
//            }
//        },
//        content = {
//            Scaffold(
//                topBar = {
//                    TopAppBar(
//                        title = { Text("Pinnacle SSC CGL Tier I") },
//                        actions = {
//                            IconButton(onClick = {
//                                coroutineScope.launch { drawerState.open() }
//                            }) {
//                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Drawer")
//                            }
//                        }
//                    )
//                }
//            ) { paddingValues ->
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues)
//                ) {
//                    val tabTitles = listOf("Reasoning", "General Awareness", "Quantitative Aptitude", "English")
//                    val selectedTabIndex by viewModel.selectedTabIndex.observeAsState(0)
//
//                    TabRow(selectedTabIndex = selectedTabIndex) {
//                        tabTitles.forEachIndexed { index, title ->
//                            Tab(
//                                selected = selectedTabIndex == index,
//                                onClick = {
//                                    viewModel.moveToSection(index)
//                                },
//                                text = { Text(title) }
//                            )
//                        }
//                    }
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Countdown: ${formatTime(remainingCountdown)}",
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Text(
//                            text = "Time: $displayTime",
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    if (error != null) {
//                        Text(
//                            text = error ?: "Unknown error",
//                            color = MaterialTheme.colorScheme.error,
//                            style = MaterialTheme.typography.bodyLarge,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    } else {
//                        val currentQuestion = details.find { it.question_id == currentQuestionId }
//
//                        if (currentQuestion != null) {
//                            viewModel.setIsDataDisplayed(true)
//                            Column(
//                                modifier = Modifier.weight(1f),
//                                verticalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                LazyColumn(
//                                    modifier = Modifier.weight(1f),
//                                    verticalArrangement = Arrangement.SpaceBetween
//                                ) {
//                                    item {
//                                        HtmlText(html = currentQuestion.question)
//                                        Spacer(modifier = Modifier.height(16.dp))
//
//                                        OptionItem(
//                                            option = currentQuestion.option1,
//                                            optionValue = "a",
//                                            selectedOption = selectedOption,
//                                            onSelectOption = { viewModel.updateSelectedOption(it) }
//                                        )
//                                        OptionItem(
//                                            option = currentQuestion.option2,
//                                            optionValue = "b",
//                                            selectedOption = selectedOption,
//                                            onSelectOption = { viewModel.updateSelectedOption(it) }
//                                        )
//                                        OptionItem(
//                                            option = currentQuestion.option3,
//                                            optionValue = "c",
//                                            selectedOption = selectedOption,
//                                            onSelectOption = { viewModel.updateSelectedOption(it) }
//                                        )
//                                        OptionItem(
//                                            option = currentQuestion.option4,
//                                            optionValue = "d",
//                                            selectedOption = selectedOption,
//                                            onSelectOption = { viewModel.updateSelectedOption(it) }
//                                        )
//
//                                        Spacer(modifier = Modifier.height(16.dp))
//                                    }
//
//                                    item {
//                                        Row(
//                                            modifier = Modifier.fillMaxWidth(),
//                                            horizontalArrangement = Arrangement.SpaceBetween
//                                        ) {
//                                            if (currentQuestionId > 1) {
//                                                Button(
//                                                    onClick = {
//                                                        val currentTime = System.currentTimeMillis()
//                                                        val startTime = startTimeMap[currentQuestionId] ?: currentTime
//                                                        val elapsed = elapsedTimeMap[currentQuestionId] ?: 0L
//                                                        val newElapsedTime = elapsed + (currentTime - startTime) / 1000
//                                                        elapsedTimeMap[currentQuestionId] = newElapsedTime
//
//                                                        viewModel.saveAnswer(
//                                                            paperId = currentQuestion.question_id,
//                                                            option = selectedOption.ifEmpty { "" },
//                                                            subject = currentQuestion.subject_id,
//                                                            currentPaperId = currentQuestionId,
//                                                            remainingTime = "",
//                                                            singleTm = formatTime(newElapsedTime) // Save time in seconds
//                                                        )
//
//                                                        viewModel.moveToPreviousQuestion()
//                                                    },
//                                                ) {
//                                                    Text("Previous")
//                                                }
//                                            }
//
//                                            if (currentQuestionId < details.maxOf { it.question_id }) {
//                                                Button(
//                                                    onClick = {
//                                                        val currentTime = System.currentTimeMillis()
//                                                        val startTime = startTimeMap[currentQuestionId] ?: currentTime
//                                                        val elapsed = elapsedTimeMap[currentQuestionId] ?: 0L
//                                                        val newElapsedTime = elapsed + (currentTime - startTime) / 1000
//                                                        elapsedTimeMap[currentQuestionId] = newElapsedTime
//
//                                                        viewModel.saveAnswer(
//                                                            paperId = currentQuestion.question_id,
//                                                            option = selectedOption.ifEmpty { "" },
//                                                            subject = currentQuestion.subject_id,
//                                                            currentPaperId = currentQuestionId,
//                                                            remainingTime = formatTime(remainingCountdown),
//                                                            singleTm = formatTime(newElapsedTime)  // Save time in seconds
//                                                        )
//
//                                                        viewModel.moveToNextQuestion()
//                                                    },
//                                                ) {
//                                                    Text("Save and Next")
//                                                }
//                                            }
//
//                                            // Add the Submit button here
//                                            Button(
//                                                onClick = {
//                                                    viewModel.submit()
//                                                },
//                                                colors = ButtonDefaults.buttonColors(
//                                                    containerColor = Color.Red
//                                                )
//                                            ) {
//                                                Text("Submit", color = Color.White)
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        } else {
//                            viewModel.setIsDataDisplayed(false)
//                            Text(
//                                text = "Questions are loading...",
//                                style = MaterialTheme.typography.bodyLarge,
//                                modifier = Modifier.align(Alignment.CenterHorizontally)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun CircularButton(onClick: () -> Unit, text: String) {
//    Surface(
//        modifier = Modifier
//            .size(48.dp)
//            .clip(CircleShape)
//            .background(Color(0xFFAB47BC))
//            .padding(4.dp),
//        onClick = onClick,
//        shape = CircleShape,
//        color = Color(0xFFAB47BC)
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Text(text = text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
//        }
//    }
//}
//
//@Composable
//fun OptionItem(option: String, optionValue: String, selectedOption: String, onSelectOption: (String) -> Unit) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp)
//    ) {
//        RadioButton(
//            selected = selectedOption == optionValue,
//            onClick = { onSelectOption(optionValue) }
//        )
//        HtmlText(html = option)
//    }
//}
//
//fun formatTime(seconds: Long): String {
//    val minutes = seconds / 60
//    val tSecond = seconds % 60
//    return String.format("%02d:%02d", minutes, tSecond)
//}
//
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import com.ssccgl.pinnacle.testcheck_2.HtmlText
import com.ssccgl.pinnacle.testportal.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(
    testSeriesId: String,
    paperCode: String,
    examModeId: Int,
    viewModel: MainViewModel = viewModel()
) {
    val data by viewModel.data.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()

    val details = data.flatMap { it.details }

    val currentQuestionId by viewModel.currentQuestionId.observeAsState(1)
    val selectedOption by viewModel.selectedOption.observeAsState("")
    val isDataDisplayed by viewModel.isDataDisplayed.observeAsState(false)

    val elapsedTimeMap = viewModel.elapsedTimeMap

    val remainingCountdown by viewModel.remainingCountdown.observeAsState(3600L)
    val countdownStarted by viewModel.countdownStarted.observeAsState(false)

    val startTimeMap = viewModel.startTimeMap
    val elapsedTime by viewModel.elapsedTime.observeAsState(0L)
    val displayElapsedTime by viewModel.displayElapsedTime.observeAsState("00:00:00") // Renamed
    val displayCountdownTime by viewModel.displayCountdownTime.observeAsState("00:00:00") // New

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Pair(isDataDisplayed, currentQuestionId)) {
        if (isDataDisplayed) {
            val currentQuestion = details.find { it.question_id == currentQuestionId }
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
                viewModel.moveToQuestion(details.first().question_id)
            }
            if (!countdownStarted) {
                viewModel.startCountdown()
            }
        }
    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        gesturesEnabled = true,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                val buttonRows = details.chunked(5)
                items(buttonRows) { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        row.forEach { detail ->
                            CircularButton(
                                onClick = {
                                    val currentTime = System.currentTimeMillis()
                                    val startTime = startTimeMap[currentQuestionId] ?: currentTime
                                    val elapsed = elapsedTimeMap[currentQuestionId] ?: 0L
                                    val newElapsedTime = elapsed + (currentTime - startTime) / 1000
                                    elapsedTimeMap[currentQuestionId] = newElapsedTime

                                    viewModel.saveAnswer(
                                        paperId = currentQuestionId,
                                        option = viewModel.validateOption(selectedOption),
                                        subject = detail.subject_id,
                                        currentPaperId = detail.question_id,
                                        remainingTime = formatTime(remainingCountdown),
                                        singleTm = formatTime(newElapsedTime), // Save time in seconds
                                        saveType = "nav", // Pass "nav" as SaveType
                                        answerStatus = "4"
                                    )

                                    viewModel.moveToQuestion(detail.question_id)
                                    coroutineScope.launch { drawerState.close() }
                                },
                                text = detail.question_id.toString()
                            )
                        }
                    }
                    Spacer(Modifier.padding(4.dp))
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Pinnacle SSC CGL Tier I") },
                        actions = {
                            IconButton(onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Drawer")
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    val tabTitles = listOf("Reasoning", "General Awareness", "Quantitative Aptitude", "English")
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
                        val currentQuestion = details.find { it.question_id == currentQuestionId }

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

                                    item {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            if (currentQuestionId > 1) {
                                                Button(
                                                    onClick = {
                                                        val currentTime = System.currentTimeMillis()
                                                        val startTime = startTimeMap[currentQuestionId] ?: currentTime
                                                        val elapsed = elapsedTimeMap[currentQuestionId] ?: 0L
                                                        val newElapsedTime = elapsed + (currentTime - startTime) / 1000
                                                        elapsedTimeMap[currentQuestionId] = newElapsedTime

                                                        val previousQuestionId = currentQuestionId - 1

                                                        viewModel.saveAnswer(
                                                            paperId = currentQuestion.question_id,
                                                            option = viewModel.validateOption(selectedOption),
                                                            subject = currentQuestion.subject_id,
                                                            currentPaperId = previousQuestionId, // Set CurrentPaperId to the previous question number
                                                            remainingTime = formatTime(remainingCountdown),
                                                            singleTm = formatTime(newElapsedTime), // Save time in seconds
                                                            saveType = "nxt",
                                                            answerStatus = "1"
                                                        )

                                                        viewModel.moveToPreviousQuestion()
                                                    },
                                                ) {
                                                    Text("Previous")
                                                }
                                            }

                                            if (currentQuestionId < details.maxOf { it.question_id }) {
                                                Button(
                                                    onClick = {
                                                        val currentTime = System.currentTimeMillis()
                                                        val startTime = startTimeMap[currentQuestionId] ?: currentTime
                                                        val elapsed = elapsedTimeMap[currentQuestionId] ?: 0L
                                                        val newElapsedTime = elapsed + (currentTime - startTime) / 1000
                                                        elapsedTimeMap[currentQuestionId] = newElapsedTime

                                                        val nextQuestionId = currentQuestionId + 1

                                                        viewModel.saveAnswer(
                                                            paperId = currentQuestion.question_id,
                                                            option = viewModel.validateOption(selectedOption),
                                                            subject = currentQuestion.subject_id,
                                                            currentPaperId = nextQuestionId,
                                                            remainingTime = formatTime(remainingCountdown),
                                                            singleTm = formatTime(newElapsedTime),  // Save time in seconds
                                                            saveType = "nxt",
                                                            answerStatus = "1"
                                                        )

                                                        viewModel.moveToNextQuestion()
                                                    },
                                                ) {
                                                    Text("Save and Next")
                                                }
                                            }

                                            // Add the Submit button here
                                            Button(
                                                onClick = {
                                                    viewModel.submit()
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color.Red
                                                )
                                            ) {
                                                Text("Submit", color = Color.White)
                                            }
                                        }
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
    )
}

@Composable
fun CircularButton(onClick: () -> Unit, text: String) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color(0xFFAB47BC))
            .padding(4.dp),
        onClick = onClick,
        shape = CircleShape,
        color = Color(0xFFAB47BC)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun OptionItem(option: String, optionValue: String, selectedOption: String, onSelectOption: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = selectedOption == optionValue,
            onClick = { onSelectOption(optionValue) }
        )
        HtmlText(html = option)
    }
}

fun formatTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val tSecond = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, tSecond)
}