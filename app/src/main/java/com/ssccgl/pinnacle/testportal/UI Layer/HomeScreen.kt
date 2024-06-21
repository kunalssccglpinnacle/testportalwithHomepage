

package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val uiState = homeViewModel.uiState.collectAsState().value
    LazyColumn {
        item { HomeScreenContent(uiState.items) }
    }
}
