
package com.ssccgl.pinnacle.testportal.ui

import IndividualExamTestPassViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.ssccgl.pinnacle.testportal.testPortal.ui.ExamPostScreen
import com.ssccgl.pinnacle.testportal.viewmodel.*

@Composable
fun NavigationHost(navController: NavHostController, homeViewModel: HomeViewModel) {
    val testPortalViewModel: TestPortalViewModel = viewModel()
    val testPassViewModel: TestPassViewModel = viewModel()
    val individualExamTestPassViewModel: IndividualExamTestPassViewModel = viewModel()
    val examPostViewModel: ExamPostViewModel = viewModel()
    val individualExamPostViewModel: IndividualExamPostViewModel = viewModel()

    NavHost(navController, startDestination = "dashboard") {
        composable("home") { HomeScreen(homeViewModel) }
        composable("test_portal") { TestPortalScreen(navController, testPortalViewModel) }
        composable("product") { ProductScreen() }
        composable("my_courses") { MyCoursesScreen() }
        composable("dashboard") { DashboardScreen(navController) }
        composable("test_pass") { TestPassScreen(navController, testPassViewModel, individualExamTestPassViewModel) }
        composable("individual_exam_test_pass") { IndividualExamTestPassScreen(individualExamTestPassViewModel, navController) }
        composable("exam_post_screen") { ExamPostScreen(examPostViewModel) }
        composable(
            route = "individual_exam_post_screen/{examPostId}",
            arguments = listOf(navArgument("examPostId") { type = NavType.StringType })
        ) { backStackEntry ->
            val examPostId = backStackEntry.arguments?.getString("examPostId") ?: ""
            IndividualExamPostScreen(individualExamPostViewModel, navController, examPostId)
        }
    }
}
