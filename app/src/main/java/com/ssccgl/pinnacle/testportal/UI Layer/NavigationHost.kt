//
//package com.ssccgl.pinnacle.testportal.ui
//
//import androidx.compose.runtime.Composable
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.ssccgl.pinnacle.testportal.DashboardScreen
//import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
//import com.ssccgl.pinnacle.testportal.viewmodel.TestPortalViewModel
//
//@Composable
//fun NavigationHost(navController: NavHostController, homeViewModel: HomeViewModel) {
//    val homeViewModel: HomeViewModel = viewModel()
//    val testPortalViewModel: TestPortalViewModel = viewModel()
//
//    NavHost(navController, startDestination = "dashboard") {
//        composable("home") { HomeScreen(homeViewModel) }
//        composable("test_portal") { TestPortalScreen(testPortalViewModel) }
//        composable("product") { ProductScreen() }
//        composable("my_courses") { MyCoursesScreen() }
//        composable("dashboard") { DashboardScreen() }  // Added DashboardScreen route
//    }
//}
// Update your NavigationHost to include the new screen
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssccgl.pinnacle.testportal.DashboardScreen
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.TestPortalViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.TestPassViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.IndividualExamTestPassViewModel

@Composable
fun NavigationHost(navController: NavHostController, homeViewModel: HomeViewModel) {
    val homeViewModel: HomeViewModel = viewModel()
    val testPortalViewModel: TestPortalViewModel = viewModel()
    val testPassViewModel: TestPassViewModel = viewModel()
    val individualExamTestPassViewModel: IndividualExamTestPassViewModel = viewModel()

    NavHost(navController, startDestination = "dashboard") {
        composable("home") { HomeScreen(homeViewModel) }
        composable("test_portal") { TestPortalScreen(navController, testPortalViewModel) }
        composable("product") { ProductScreen() }
        composable("my_courses") { MyCoursesScreen() }
        composable("dashboard") { DashboardScreen() } // Added DashboardScreen route
        composable("test_pass") { TestPassScreen(testPassViewModel) } // Added TestPassScreen route
        composable("individual_exam_test_pass") { IndividualExamTestPassScreen(individualExamTestPassViewModel) } // Added IndividualExamTestPassScreen route
    }
}



