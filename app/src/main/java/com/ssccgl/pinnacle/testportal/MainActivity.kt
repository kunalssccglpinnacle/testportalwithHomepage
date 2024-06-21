package com.ssccgl.pinnacle.testportal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.ssccgl.pinnacle.testportal.ui.HomeScreen
import com.ssccgl.pinnacle.testportal.ui.MainScreen
import com.ssccgl.pinnacle.testportal.ui.TestPassScreen
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModelFactory


class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          //  DashboardScreen()

            MainScreen(homeViewModel)

           // TestPassScreen()
        }
    }
}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val navController = rememberNavController()
//
//            Scaffold(
//                topBar = {
//                    TopAppBar(title = { Text("Test Portal") })
//                },
//                content = {
//                    NavHost(navController = navController, startDestination = "home") {
//                        composable("home") {
//                            HomeScreen(navController)
//                        }
//                        composable("test_pass") {
//                            TestPassScreen()
//                        }
//                    }
//                }
//            )
//        }
//    }
//}


//class MainActivity : ComponentActivity() {
//    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            Scaffold(
//                topBar = {
//                    //TopAppBar(title = { Text("Test Passes") })
//                },
//                content = {
//                    TestPassScreen()
//                }
//            )
//        }
//    }
//}


@Composable
fun DashboardIcon(icon: @Composable () -> Unit, label: String, backgroundColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
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
fun DashboardScreen() {
    val icons = listOf(
//        Icons.Default.Home to "Home",
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
                backgroundColor = colors[index]
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen()
    }
}