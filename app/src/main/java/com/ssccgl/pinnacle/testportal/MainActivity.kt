package com.ssccgl.pinnacle.testportal

import LoginViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.ssccgl.pinnacle.testportal.ui.MainScreen
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModel
import com.ssccgl.pinnacle.testportal.viewmodel.HomeViewModelFactory
//import com.ssccgl.pinnacle.testportal.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory() }
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(homeViewModel, loginViewModel)
        }
    }
}
//}