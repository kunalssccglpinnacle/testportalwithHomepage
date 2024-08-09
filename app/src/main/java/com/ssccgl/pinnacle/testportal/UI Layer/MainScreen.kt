

package com.ssccgl.pinnacle.testportal.ui

// MainScreen.kt
import LoginViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
//import com.ssccgl.pinnacle.testportal.viewmodel.LoginViewModel

@Composable
fun MainScreen(homeViewModel: HomeViewModel, loginViewModel: LoginViewModel) {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val loginData = loginViewModel.loginData.collectAsState().value

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        loginViewModel.autoLogin(context)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentRoute == "dashboard") {
                TopAppBarWithDrawerButton(scaffoldState = scaffoldState) {
                    // Handle logout click
                    loginViewModel.logout(context)
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
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

