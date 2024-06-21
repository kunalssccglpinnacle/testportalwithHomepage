package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssccgl.pinnacle.testportal.data.HomeRepository

class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(HomeRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
