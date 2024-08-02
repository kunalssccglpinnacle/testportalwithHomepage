
//package com.ssccgl.pinnacle.testportal.ui
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
//import kotlinx.coroutines.launch
//
//@Composable
//fun MainScreen(homeViewModel: HomeViewModel) {
//    val scaffoldState = rememberScaffoldState()
//    val navController = rememberNavController()
//    val coroutineScope = rememberCoroutineScope()
//    val currentBackStackEntry = navController.currentBackStackEntryAsState()
//    val currentRoute = currentBackStackEntry.value?.destination?.route
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = {
//            if (currentRoute == "dashboard") {
//                TopAppBarWithDrawerButton(scaffoldState)
//            }
//        },
//        drawerContent = { AppDrawer(navController, scaffoldState) },
//        bottomBar = {
//            if (currentRoute == "dashboard") {
//                BottomNavigationBar(navController)
//            }
//        },
//        floatingActionButtonPosition = FabPosition.Center,
//        isFloatingActionButtonDocked = true,
//        content = { padding ->
//            Box(modifier = Modifier.padding(padding)) {
//                NavigationHost(navController, homeViewModel)
//            }
//        }
//    )
//}

//package com.ssccgl.pinnacle.testportal.ui
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
//import com.ssccgl.pinnacle.testportal.viewmodel.LoginViewModel
//
//@Composable
//fun MainScreen(homeViewModel: HomeViewModel, loginViewModel: LoginViewModel) {
//    val scaffoldState = rememberScaffoldState()
//    val navController = rememberNavController()
//    val coroutineScope = rememberCoroutineScope()
//    val currentBackStackEntry = navController.currentBackStackEntryAsState()
//    val currentRoute = currentBackStackEntry.value?.destination?.route
//    val loginData = loginViewModel.loginData.collectAsState().value
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        topBar = {
//            if (currentRoute == "dashboard") {
//                TopAppBarWithDrawerButton(scaffoldState)
//            }
//        },
//        drawerContent = { loginData?.let { AppDrawer(navController, scaffoldState, it.full_name, it.email_id, it.mobile_number) } },
//        bottomBar = {
//            if (currentRoute == "dashboard") {
//                BottomNavigationBar(navController)
//            }
//        },
//        floatingActionButtonPosition = FabPosition.Center,
//        isFloatingActionButtonDocked = true,
//        content = { padding ->
//            Box(modifier = Modifier.padding(padding)) {
//                NavigationHost(navController, homeViewModel, loginData)
//            }
//        }
//    )
//}
package com.ssccgl.pinnacle.testportal.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.LoginViewModel

@Composable
fun MainScreen(homeViewModel: HomeViewModel, loginViewModel: LoginViewModel) {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val loginData = loginViewModel.loginData.collectAsState().value

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentRoute == "dashboard") {
                TopAppBarWithDrawerButton(scaffoldState)
            }
        },
        drawerContent = {
            loginData?.let {
                AppDrawer(navController, scaffoldState, it.full_name, it.email_id, it.mobile_number)
            }
        },
        bottomBar = {
            if (currentRoute == "dashboard") {
                BottomNavigationBar(navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavigationHost(navController, homeViewModel, loginData)
            }
        }
    )
}
