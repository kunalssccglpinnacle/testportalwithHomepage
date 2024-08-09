
package com.ssccgl.pinnacle.testportal.ui

import LoginViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.ssccgl.pinnacle.testportal.network.AttemptedRequest
import com.ssccgl.pinnacle.testportal.network.LoginData
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import com.ssccgl.pinnacle.testportal.network.SolutionRequest
import com.ssccgl.pinnacle.testportal.repository.TestRepository
import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.IndividualExamTestPassViewModel2
import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.NewTestsWebViewModel
import com.ssccgl.pinnacle.testportal.testPortal.ui.SolutionScreen
import com.ssccgl.pinnacle.testportal.viewmodel.*

@Composable
fun NavigationHost(navController: NavHostController, homeViewModel: HomeViewModel, loginData: LoginData?) {
    val loginViewModel: LoginViewModel = viewModel()
    val testPortalViewModel: TestPortalViewModel = viewModel()
    val testPassViewModel: TestPassViewModel = viewModel()
    val individualExamTestPassViewModel: IndividualExamTestPassViewModel2 = viewModel()
    val newTestsWebViewModel: NewTestsWebViewModel = viewModel()
    val solutionViewModel: SolutionViewModel = viewModel()
    val testSeriesViewModelFactory = TestSeriesViewModelFactory(TestRepository(RetrofitInstance.api))
    val testSeriesViewModel: TestSeriesViewModel = viewModel(factory = testSeriesViewModelFactory)

    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, loginViewModel) }
        composable("home") { HomeScreen(homeViewModel) }
        composable("test_portal") { TestPortalScreen(navController, testPortalViewModel) }
        composable("product") { ProductScreen() }
        composable("my_courses") { MyCoursesScreen() }
        composable("dashboard") { DashboardScreen(navController, loginData?.email_id ?: "") }
        composable(
            route = "test_pass/{emailId}",
            arguments = listOf(navArgument("emailId") { type = NavType.StringType })
        ) { backStackEntry ->
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            TestPassScreen(navController = navController, emailId = emailId, testPassViewModel = testPassViewModel)
        }

        composable(
            route = "individual_exam_test_pass/{id}/{email}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val email = backStackEntry.arguments?.getString("email") ?: ""
            IndividualExamTestPassScreen(
                productId = id,
                emailId = email,
                viewModel = individualExamTestPassViewModel,
                navController = navController
            )
        }


        composable(
            route = "new_tests_web_screen/{examPostId}/{examId}/{tierId}/{emailId}/{productId}",
            arguments = listOf(
                navArgument("examPostId") { type = NavType.IntType },
                navArgument("examId") { type = NavType.IntType },
                navArgument("tierId") { type = NavType.IntType },
                navArgument("emailId") { type = NavType.StringType },
                navArgument("productId") { type = NavType.IntType } // Change to IntType if productId is an Int
            )
        ) { backStackEntry ->
            val examPostId = backStackEntry.arguments?.getInt("examPostId") ?: 0
            val examId = backStackEntry.arguments?.getInt("examId") ?: 0
            val tierId = backStackEntry.arguments?.getInt("tierId") ?: 0
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0 // Change to getInt if productId is an Int
            NewTestsWebScreen(
                emailId = emailId,
                examPostId = examPostId.toString(),
                examId = examId.toString(),
                tierId = tierId.toString(),
                productId = productId.toString(),
                viewModel = newTestsWebViewModel,
                navController = navController
            )
        }


        composable(
            route = "test_series_screen/{emailId}/{tierId}/{examModeId}/{examId}/{examPostId}/{productId}",
            arguments = listOf(
                navArgument("emailId") { type = NavType.StringType },
                navArgument("tierId") { type = NavType.StringType },
                navArgument("examModeId") { type = NavType.IntType },
                navArgument("examId") { type = NavType.IntType },
                navArgument("examPostId") { type = NavType.IntType },
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            val tierId = backStackEntry.arguments?.getString("tierId") ?: ""
            val examModeId = backStackEntry.arguments?.getInt("examModeId") ?: 0
            val examId = backStackEntry.arguments?.getInt("examId") ?: 0
            val examPostId = backStackEntry.arguments?.getInt("examPostId") ?: 0
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            TestSeriesScreen(
                emailId = emailId,
                tierId = tierId,
                examModeId = examModeId,
                examId = examId,
                examPostId = examPostId,
                productId = productId,
                navController = navController
            )
        }

        composable(
            route = "test_series_details2_screen/{testSeriesId}/{emailId}/{examId}/{examPostId}/{tierId}/{productId}/{examModeId}",
            arguments = listOf(
                navArgument("testSeriesId") { type = NavType.StringType },
                navArgument("emailId") { type = NavType.StringType },
                navArgument("examId") { type = NavType.IntType },
                navArgument("examPostId") { type = NavType.IntType },
                navArgument("tierId") { type = NavType.StringType },
                navArgument("productId") { type = NavType.IntType },
                navArgument("examModeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: ""
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            val examId = backStackEntry.arguments?.getInt("examId") ?: 0
            val examPostId = backStackEntry.arguments?.getInt("examPostId") ?: 0
            val tierId = backStackEntry.arguments?.getString("tierId") ?: ""
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            val examModeId = backStackEntry.arguments?.getInt("examModeId") ?: 0
            TestSeriesDetails2Screen(
                testSeriesId = testSeriesId,
                emailId = emailId,
                examId = examId,
                examPostId = examPostId,
                tierId = tierId,
                productId = productId,
                examModeId = examModeId,
                navController = navController
            )
        }

        composable(
            route = "data_screen/{testSeriesId}/{paperCode}/{examModeId}/{emailId}/{selectedLanguage}",
            arguments = listOf(
                navArgument("testSeriesId") { type = NavType.StringType },
                navArgument("paperCode") { type = NavType.StringType },
                navArgument("examModeId") { type = NavType.IntType },
                navArgument("emailId") { type = androidx.navigation.NavType.StringType },
                navArgument("selectedLanguage") { type = androidx.navigation.NavType.StringType }
            )
        ) { backStackEntry ->
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: ""
            val paperCode = backStackEntry.arguments?.getString("paperCode") ?: ""
            val examModeId = backStackEntry.arguments?.getInt("examModeId") ?: 0
            val selectedLanguage = backStackEntry.arguments?.getString("selectedLanguage") ?: "English"
            val viewModelFactory = MainViewModelFactory(
                paperCode,
                emailId = emailId,
                examModeId.toString(),
                testSeriesId
            )
            DataScreen(
                testSeriesId = testSeriesId,
                paperCode = paperCode,
                examModeId = examModeId,
                navController = navController,
                emailId = emailId,
                selectedLanguage = selectedLanguage,
                viewModel = viewModel(factory = viewModelFactory)
            )
        }
        composable(
            route = "result_screen/{paperCode}/{emailId}/{examModeId}/{testSeriesId}",
            arguments = listOf(
                navArgument("paperCode") { type = NavType.StringType },
                navArgument("emailId") { type = NavType.StringType },
                navArgument("examModeId") { type = NavType.StringType },
                navArgument("testSeriesId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val paperCode = backStackEntry.arguments?.getString("paperCode") ?: "3201"
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            val examModeId = backStackEntry.arguments?.getString("examModeId") ?: "1"
            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: "2"
            ResultScreen(
                navController = navController,
                paperCode = paperCode,
                emailId = emailId,
                examModeId = examModeId,
                testSeriesId = testSeriesId
            )
        }
        composable(
            route = "instructions_screen/{testSeriesName}/{marks}/{time}/{testSeriesId}/{paperCode}/{examModeId}/{questions}/{emailId}",
            arguments = listOf(
                navArgument("testSeriesName") { type = NavType.StringType },
                navArgument("marks") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("testSeriesId") { type = NavType.StringType },
                navArgument("paperCode") { type = NavType.StringType },
                navArgument("examModeId") { type = NavType.StringType },
                navArgument("questions") { type = NavType.StringType },
                navArgument("emailId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val testSeriesName = backStackEntry.arguments?.getString("testSeriesName") ?: ""
            val marks = backStackEntry.arguments?.getString("marks") ?: "0"
            val time = backStackEntry.arguments?.getString("time") ?: "0"
            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: ""
            val paperCode = backStackEntry.arguments?.getString("paperCode") ?: ""
            val examModeId = backStackEntry.arguments?.getString("examModeId") ?: ""

            val questions = backStackEntry.arguments?.getString("questions") ?: ""
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            InstructionsScreen(
                navController = navController,
                testSeriesName = testSeriesName,
                marks = marks,
                time = time,
                testSeriesId = testSeriesId,
                paperCode = paperCode,
                examModeId = examModeId,
                questions = questions,
                emailId = emailId
            )
        }
        composable(
            route = "solution_screen/{paperCode}/{emailId}/{examModeId}/{testSeriesId}",
            arguments = listOf(
                navArgument("paperCode") { type = NavType.StringType },
                navArgument("emailId") { type = NavType.StringType },
                navArgument("examModeId") { type = NavType.StringType },
                navArgument("testSeriesId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val paperCode = backStackEntry.arguments?.getString("paperCode") ?: ""
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            val examModeId = backStackEntry.arguments?.getString("examModeId") ?: ""
            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: ""
            if (paperCode.isNotBlank() && emailId.isNotBlank() && examModeId.isNotBlank() && testSeriesId.isNotBlank()) {
                val request = SolutionRequest(paperCode, emailId, examModeId, testSeriesId)
                val request1 = AttemptedRequest(paperCode, emailId, examModeId, testSeriesId)
                SolutionScreen(viewModel = solutionViewModel, request = request, request1 = request1)
            } else {
                Text("Error: Missing or invalid arguments")
            }
        }
    }
}
