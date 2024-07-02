//
//package com.ssccgl.pinnacle.testportal.testPortal.ui
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.material.Card
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.rememberImagePainter
//import com.ssccgl.pinnacle.testportal.network.ExamPost
//import com.ssccgl.pinnacle.testportal.viewmodel.ExamPostViewModel
//
//class ExamPostActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val viewModel: ExamPostViewModel = viewModel()
//            ExamPostScreen(viewModel = viewModel)
//        }
//    }
//}
//
//@Composable
//fun ExamPostScreen(viewModel: ExamPostViewModel = viewModel()) {
//    val examPosts by viewModel.examPosts.collectAsState()
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        horizontalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        items(examPosts) { examPost ->
//            ExamPostCard(examPost = examPost)
//        }
//    }
//}
//
//@Composable
//fun ExamPostCard(examPost: ExamPost) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(1f) // To make the card square
//            .clickable { /* Handle card click */ },
//        elevation = 4.dp
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Image(
//                painter = rememberImagePainter(data = examPost.logo),
//                contentDescription = examPost.post_name,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.size(48.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = examPost.post_name,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//    }
//}


package com.ssccgl.pinnacle.testportal.testPortal.ui
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.material.Card
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.rememberImagePainter
//import com.ssccgl.pinnacle.testportal.network.ExamPost
//import com.ssccgl.pinnacle.testportal.viewmodel.ExamPostViewModel
//import com.ssccgl.pinnacle.testportal.viewmodel.NewTestType
//
//class ExamPostActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val viewModel: ExamPostViewModel = viewModel()
//            ExamPostScreen(viewModel = viewModel)
//        }
//    }
//}
//
//@Composable
//fun ExamPostScreen(viewModel: ExamPostViewModel = viewModel()) {
//    val examPosts by viewModel.examPosts.collectAsState()
//    val newTestResponse by viewModel.newTestResponse.collectAsState()
//
//    Column {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            items(examPosts) { examPost ->
//                ExamPostCard(examPost = examPost)
//            }
//        }
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            items(newTestResponse.flatMap { it.TestType }) { testType ->
//                NewTestCard(testType = testType)
//            }
//        }
//    }
//}
//
//@Composable
//fun ExamPostCard(examPost: ExamPost) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(1f)
//            .clickable { /* Handle card click */ },
//        elevation = 4.dp
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Image(
//                painter = rememberImagePainter(data = examPost.logo),
//                contentDescription = examPost.post_name,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.size(48.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = examPost.post_name,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun NewTestCard(testType: NewTestType) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(1f)
//            .clickable { /* Handle card click */ },
//        elevation = 4.dp
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = testType.test_type,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "${testType.TotalTests} Total Tests | ${testType.FreeTests} Free Test",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Normal,
//                modifier = Modifier.padding(top = 4.dp)
//            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = "Total Test Series: ${testType.TotalTestSeries}",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Normal,
//                modifier = Modifier.padding(top = 4.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Continue",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold,
//                color = androidx.compose.ui.graphics.Color(0xFFFF5722),
//                modifier = Modifier.padding(top = 4.dp)
//            )
//        }
//    }
//}
