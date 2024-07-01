
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.network.IndividualExamPost
import com.ssccgl.pinnacle.testportal.viewmodel.IndividualExamPostViewModel

@Composable
fun IndividualExamPostScreen(viewModel: IndividualExamPostViewModel, navController: NavHostController, examPostId: String) {
    val individualExamPostsState = viewModel.individualExamPosts.collectAsState()
    val individualExamPosts = individualExamPostsState.value

    LaunchedEffect(Unit) {
        viewModel.fetchIndividualExamPostData(examPostId.toInt())
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Exam Post Details3",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (individualExamPosts.isEmpty()) {
            Text(
                text = "No exam post data available.",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else {
            LazyColumn {
                items(individualExamPosts) { examPost ->
                    IndividualExamPostCard(examPost, navController)
                }
            }
        }
    }
}

@Composable
fun IndividualExamPostCard(examPost: IndividualExamPost, navController: NavHostController) {
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
                text = examPost.post_tier_name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Features")
            Text(text = "• Mock Test based on latest pattern")
            Text(text = "• Previous year included pdf")
            Text(text = "• Sectional based on different level")
            Text(text = "• Chapterwise based on weightage")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate("exam_post_screen") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
                ) {
                    Text(text = "buy Now")
                }
                Button(
                    onClick = { navController.navigate("exam_post_screen") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5))
                ) {
                    Text(text = "show more")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Use coupon code for extra discount.... Apply code",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

