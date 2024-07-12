
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.*
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.TestPassViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.TestPortalViewModel

@Composable
fun NavigationHost(navController: NavHostController, homeViewModel: HomeViewModel) {
    val testPortalViewModel: TestPortalViewModel = viewModel()
    val testPassViewModel: TestPassViewModel = viewModel()
    val individualExamTestPassViewModel: IndividualExamTestPassViewModel2 = viewModel()

    NavHost(navController, startDestination = "dashboard") {
        composable("home") { HomeScreen(homeViewModel) }
        composable("test_portal") { TestPortalScreen(navController, testPortalViewModel) }
        composable("product") { ProductScreen() }
        composable("my_courses") { MyCoursesScreen() }
        composable("dashboard") { DashboardScreen(navController) }
        composable("test_pass") { TestPassScreen(navController, testPassViewModel) }
        composable(
            route = "individual_exam_test_pass/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            IndividualExamTestPassScreen(productId = productId, viewModel = individualExamTestPassViewModel, navController = navController)
        }
    }
}

