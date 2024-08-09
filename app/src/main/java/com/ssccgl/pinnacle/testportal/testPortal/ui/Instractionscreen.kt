package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color.Companion.Red

@Composable
fun InstructionsScreen(
    navController: NavController,
    testSeriesName: String,
    marks: String,
    time: String,
    testSeriesId: String,
    paperCode: String,
    examModeId: String,
    questions: String, emailId:String
) {
    val selectedLanguage = remember { mutableStateOf("Select") }
    val languages = listOf("Select", "Hindi", "English")
    var expanded by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$testSeriesName") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = testSeriesName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Duration : $time mins        Maximum Mark : $marks marks")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Read the following instructions carefully.", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "• The test contains a total of $questions questions.\n" +
                        "• Test is developed based on the exam pattern.\n" +
                        "• Each question has 4 options out of which only one is correct.\n" +
                        "• You have to finish the test in the given time.\n" +
                        "• There is negative marking 0.50 marks will be deducted in case of wrong answer and 2 mark will be given in case correct answer. Unattempted question marks will not be deducted or awarded. Instead of making guesses, students can choose not to attempt the question.\n" +
                        "• You can attempt the test once, so attempt all questions and take the test seriously to get the full advantages of the test. So don’t leave the test unattempted. Submit the test once you attempt all questions.")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "• Choose your default language:")
                Spacer(modifier = Modifier.height(8.dp))
                Box {
                    Button(onClick = { expanded = true }) {
                        Text(text = selectedLanguage.value)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        languages.forEach { language ->
                            DropdownMenuItem(onClick = {
                                selectedLanguage.value = language
                                expanded = false
                                showError = false
                            }) {
                                Text(text = language)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Questions will appear in your chosen default language, however later on in case you can change your language. This option you will find in the test panel during the test also.")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Declaration:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "I have read and understood all the instructions/rules/policies and will abide by the same. I understand that cheating or using unfair means in the exam for own advantages or someone else's advantages may lead to disqualification and cancel registration. Pinnacle’s/Frontier IAS in this regard is final and binding.")
                }
                if (showError) {
                    Text(text = "Please select a language and agree to the declaration.", color = Red)
                }
                Spacer(modifier = Modifier.height(16.dp))
//                Button(onClick = {
//                    if (selectedLanguage.value != "Select" && checked) {
//                        navController.navigate("data_screen/$testSeriesId/$paperCode/$examModeId/$emailId")
//                    } else {
//                        showError = true
//                    }
//                }) {
//                    Text(text = "Agree and Continue")
//                }
                Button(onClick = {
                    if (selectedLanguage.value != "Select" && checked) {
                        navController.navigate("data_screen/$testSeriesId/$paperCode/$examModeId/$emailId/${selectedLanguage.value}")
                    } else {
                        showError = true
                    }
                }) {
                    Text(text = "Agree and Continue")
                }
            }
        }
    )
}
