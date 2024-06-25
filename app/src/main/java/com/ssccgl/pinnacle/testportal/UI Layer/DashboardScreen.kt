package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DashboardIcon(
    icon: @Composable () -> Unit,
    label: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(backgroundColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 14.sp)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    val icons = listOf(
        Icons.Default.Assessment to "Test Portal",
        Icons.Default.Dashboard to "Dashboard",
        Icons.Default.ShoppingCart to "Product",
        Icons.Default.School to "My Courses",
        Icons.Default.Book to "Books",
        Icons.Default.CheckCircle to "Attempted Tests",
        Icons.Default.TrackChanges to "Tracking",
        Icons.Default.LibraryBooks to "eBooks",
        Icons.Default.Save to "Saved",
        Icons.Default.OfflinePin to "Offline eBooks",
        Icons.Default.QrCodeScanner to "QR Scanner",
        Icons.Default.Keyboard to "Typing Software",
        Icons.Default.VideoLibrary to "Offline Videos"
    )

    val colors = listOf(
        Color(0xFF9C27B0),
        Color(0xFF03A9F4),
        Color(0xFFFFC107),
        Color(0xFFF44336),
        Color(0xFF3F51B5),
        Color(0xFF4CAF50),
        Color(0xFFFF5722),
        Color(0xFF673AB7),
        Color(0xFF009688),
        Color(0xFF795548),
        Color(0xFF607D8B),
        Color(0xFFFF9800),
        Color(0xFFE91E63),
        Color(0xFF2196F3)
    )

    val onClickHandlers = listOf(
        { navController.navigate("test_portal") },
       // {navController.navigate("test_pass")},
        { navController.navigate("dashboard") },
        { navController.navigate("product") },
        { navController.navigate("my_courses") },
        { /* Handle click for Books */ },
        { /* Handle click for Attempted Tests */ },
        { /* Handle click for Tracking */ },
        { /* Handle click for eBooks */ },
        { /* Handle click for Saved */ },
        { /* Handle click for Offline eBooks */ },
        { /* Handle click for QR Scanner */ },
        { /* Handle click for Typing Software */ },
        { /* Handle click for Offline Videos */ }
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // Number of columns in the grid
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(icons.size) { index ->
            DashboardIcon(
                icon = { Icon(icons[index].first, contentDescription = icons[index].second, tint = Color.White) },
                label = icons[index].second,
                backgroundColor = colors[index],
                onClick = onClickHandlers[index]
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        DashboardScreen(navController)
    }
}


