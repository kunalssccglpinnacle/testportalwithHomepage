//package com.ssccgl.pinnacle.testportal.ui
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.google.gson.GsonBuilder
//import com.ssccgl.pinnacle.testportal.network.AttemptedResponse
//import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModel
//import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModelFactory
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ResultScreen(navController: NavHostController, paperCode: String, emailId: String, examModeId: String, testSeriesId: String) {
//    val viewModel: ResultViewModel = viewModel(factory = ResultViewModelFactory(paperCode, emailId, examModeId, testSeriesId))
//    val result by viewModel.result.observeAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Test Result") }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
//            if (result != null) {
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    contentPadding = PaddingValues(16.dp)
//                ) {
//                    items(result!!) { item ->
//                        ResultItem(item = item)
//                        Divider(modifier = Modifier.padding(vertical = 8.dp))
//                    }
//                }
//            } else {
//                Text(
//                    text = "Loading result...",
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ResultItem(item: AttemptedResponse) {
//    val gson = GsonBuilder().setPrettyPrinting().create()
//    val json = gson.toJson(item)
//    Text(
//        text = json,
//        style = MaterialTheme.typography.bodyMedium,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        maxLines = 10,
//        overflow = TextOverflow.Ellipsis
//    )
//}

package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.gson.GsonBuilder
import com.ssccgl.pinnacle.testportal.network.AttemptedResponse
import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.ResultViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(navController: NavHostController, paperCode: String, emailId: String, examModeId: String, testSeriesId: String) {
    val viewModel: ResultViewModel = viewModel(factory = ResultViewModelFactory(paperCode, emailId, examModeId, testSeriesId))
    val result by viewModel.result.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Test Result") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (result != null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(result!!) { item ->
                        ResultItem(item = item)
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            } else {
                Box(
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
fun ResultItem(item: AttemptedResponse) {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson(item)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
       // elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = json,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                overflow = TextOverflow.Clip
            )
        }
    }
}

