//package com.ssccgl.pinnacle.testportal.ui
//
//import androidx.compose.runtime.Composable
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import androidx.navigation.NavType
//import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
//import com.ssccgl.pinnacle.testportal.repository.TestRepository
//import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.IndividualExamTestPassViewModel2
//import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.NewTestsWebViewModel
//import com.ssccgl.pinnacle.testportal.viewmodel.*
//
//@Composable
//fun NavigationHost(navController: NavHostController, homeViewModel: HomeViewModel) {
//    val testPortalViewModel: TestPortalViewModel = viewModel()
//    val testPassViewModel: TestPassViewModel = viewModel()
//    val individualExamTestPassViewModel: IndividualExamTestPassViewModel2 = viewModel()
//    val newTestsWebViewModel: NewTestsWebViewModel = viewModel()
//    val testSeriesViewModelFactory = TestSeriesViewModelFactory(TestRepository(RetrofitInstance.api))
//    val testSeriesViewModel: TestSeriesViewModel = viewModel(factory = testSeriesViewModelFactory)
//
//    NavHost(navController, startDestination = "dashboard") {
//        composable("home") { HomeScreen(homeViewModel) }
//        composable("test_portal") { TestPortalScreen(navController, testPortalViewModel) }
//        composable("product") { ProductScreen() }
//        composable("my_courses") { MyCoursesScreen() }
//        composable("dashboard") { DashboardScreen(navController) }
//        composable("test_pass") { TestPassScreen(navController, testPassViewModel) }
//        composable(
//            route = "individual_exam_test_pass/{productId}",
//            arguments = listOf(navArgument("productId") { type = NavType.IntType })
//        ) { backStackEntry ->
//            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
//            IndividualExamTestPassScreen(productId = productId, viewModel = individualExamTestPassViewModel, navController = navController)
//        }
//        composable(
//            route = "new_tests_web_screen/{examPostId}/{examId}/{tierId}",
//            arguments = listOf(
//                navArgument("examPostId") { type = NavType.IntType },
//                navArgument("examId") { type = NavType.IntType },
//                navArgument("tierId") { type = NavType.IntType }
//            )
//        ) { backStackEntry ->
//            val examPostId = backStackEntry.arguments?.getInt("examPostId") ?: 0
//            val examId = backStackEntry.arguments?.getInt("examId") ?: 0
//            val tierId = backStackEntry.arguments?.getInt("tierId") ?: 0
//            NewTestsWebScreen(
//                emailId = "harishmodi@129@gmail.com", // replace with actual email if needed
//                examPostId = examPostId.toString(),
//                examId = examId.toString(),
//                tierId = tierId.toString(),
//                viewModel = newTestsWebViewModel,
//                navController = navController
//            )
//        }
//        composable(
//            route = "test_series_screen/{emailId}/{examPostId}/{examModeId}",
//            arguments = listOf(
//                navArgument("emailId") { type = NavType.StringType },
//                navArgument("examPostId") { type = NavType.StringType },
//                navArgument("examModeId") { type = NavType.StringType }
//            )
//        ) { backStackEntry ->
//            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
//            val examPostId = backStackEntry.arguments?.getString("examPostId") ?: ""
//            val examModeId = backStackEntry.arguments?.getString("examModeId") ?: ""
//            TestSeriesScreen(
//                emailId = emailId,
//                examPostId = examPostId,
//                examModeId = examModeId,
//                navController = navController
//            )
//        }
//        composable(
//            route = "test_series_details2_screen/{testSeriesId}",
//            arguments = listOf(
//                navArgument("testSeriesId") { type = NavType.StringType }
//            )
//        ) { backStackEntry ->
//            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: ""
//            TestSeriesDetails2Screen(
//                testSeriesId = testSeriesId,
//                navController = navController
//            )
//        }
//        composable(
//            route = "data_screen/{testSeriesId}/{paperCode}/{examModeId}",
//            arguments = listOf(
//                navArgument("testSeriesId") { type = NavType.StringType },
//                navArgument("paperCode") { type = NavType.StringType },
//                navArgument("examModeId") { type = NavType.IntType }
//            )
//        ) { backStackEntry ->
//            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: ""
//            val paperCode = backStackEntry.arguments?.getString("paperCode") ?: ""
//            val examModeId = backStackEntry.arguments?.getInt("examModeId") ?: 0
//            val viewModelFactory = MainViewModelFactory(
//                paperCode,
//                "anshulji100@gmail.com",
//                examModeId.toString(),
//                testSeriesId,
//                TestRepository(RetrofitInstance.api)
//            )
//            DataScreen(
//                testSeriesId = testSeriesId,
//                paperCode = paperCode,
//                examModeId = examModeId,
//                viewModel = viewModel(factory = viewModelFactory)
//            )
//        }
//    }
//}


package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import com.ssccgl.pinnacle.testportal.repository.TestRepository
import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.IndividualExamTestPassViewModel2
import com.ssccgl.pinnacle.testportal.testPortal.Viewmodel.NewTestsWebViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.*

@Composable
fun NavigationHost(navController: NavHostController, homeViewModel: HomeViewModel) {
    val testPortalViewModel: TestPortalViewModel = viewModel()
    val testPassViewModel: TestPassViewModel = viewModel()
    val individualExamTestPassViewModel: IndividualExamTestPassViewModel2 = viewModel()
    val newTestsWebViewModel: NewTestsWebViewModel = viewModel()
    val testSeriesViewModelFactory = TestSeriesViewModelFactory(TestRepository(RetrofitInstance.api))
    val testSeriesViewModel: TestSeriesViewModel = viewModel(factory = testSeriesViewModelFactory)

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
        composable(
            route = "new_tests_web_screen/{examPostId}/{examId}/{tierId}",
            arguments = listOf(
                navArgument("examPostId") { type = NavType.IntType },
                navArgument("examId") { type = NavType.IntType },
                navArgument("tierId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val examPostId = backStackEntry.arguments?.getInt("examPostId") ?: 0
            val examId = backStackEntry.arguments?.getInt("examId") ?: 0
            val tierId = backStackEntry.arguments?.getInt("tierId") ?: 0
            NewTestsWebScreen(
                emailId = "harishmodi@129@gmail.com", // replace with actual email if needed
                examPostId = examPostId.toString(),
                examId = examId.toString(),
                tierId = tierId.toString(),
                viewModel = newTestsWebViewModel,
                navController = navController
            )
        }
        composable(
            route = "test_series_screen/{emailId}/{examPostId}/{examModeId}",
            arguments = listOf(
                navArgument("emailId") { type = NavType.StringType },
                navArgument("examPostId") { type = NavType.StringType },
                navArgument("examModeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val emailId = backStackEntry.arguments?.getString("emailId") ?: ""
            val examPostId = backStackEntry.arguments?.getString("examPostId") ?: ""
            val examModeId = backStackEntry.arguments?.getString("examModeId") ?: ""
            TestSeriesScreen(
                emailId = emailId,
                examPostId = examPostId,
                examModeId = examModeId,
                navController = navController
            )
        }
        composable(
            route = "test_series_details2_screen/{testSeriesId}",
            arguments = listOf(
                navArgument("testSeriesId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: ""
            TestSeriesDetails2Screen(
                testSeriesId = testSeriesId,
                navController = navController
            )
        }
        composable(
            route = "data_screen/{testSeriesId}/{paperCode}/{examModeId}",
            arguments = listOf(
                navArgument("testSeriesId") { type = NavType.StringType },
                navArgument("paperCode") { type = NavType.StringType },
                navArgument("examModeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val testSeriesId = backStackEntry.arguments?.getString("testSeriesId") ?: ""
            val paperCode = backStackEntry.arguments?.getString("paperCode") ?: ""
            val examModeId = backStackEntry.arguments?.getInt("examModeId") ?: 0
            val viewModelFactory = MainViewModelFactory(
                paperCode,
                "anshulji100@gmail.com",
                examModeId.toString(),
                testSeriesId,
//                TestRepository(RetrofitInstance.api)
            )
            DataScreen(
                testSeriesId = testSeriesId,
                paperCode = paperCode,
                examModeId = examModeId,
                viewModel = viewModel(factory = viewModelFactory)
            )
        }
    }
}

