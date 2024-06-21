package com.ssccgl.pinnacle.testportal.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssccgl.pinnacle.testportal.network.IndividualExamTestPass
import com.ssccgl.pinnacle.testportal.viewmodel.IndividualExamTestPassViewModel

@Composable
fun IndividualExamTestPassScreen(viewModel: IndividualExamTestPassViewModel = viewModel()) {
    val examTestPassesState = viewModel.individualExamTestPasses.collectAsState()
    val examTestPasses = examTestPassesState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Individual Exam Test Passes",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (examTestPasses.isEmpty()) {
            Log.d("IndividualExamTestPassScreen", "No exam test passes available.")
            Text(text = "Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Log.d("IndividualExamTestPassScreen", "Displaying exam test passes: $examTestPasses")
            LazyColumn {
                items(examTestPasses) { examTestPass ->
                    IndividualExamTestPassCard(examTestPass)
                }
            }
        }
    }
}

@Composable
fun IndividualExamTestPassCard(examTestPass: IndividualExamTestPass) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFBBDEFB))
                .padding(16.dp)
        ) {
            Text(
                text = examTestPass.exam_post_name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: ${examTestPass.id}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Handle join now action */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text(text = "Join Now")
            }
        }
    }
}
