


//
//package com.ssccgl.pinnacle.testportal.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.ssccgl.pinnacle.testportal.repository.TestRepository
//
//class MainViewModelFactory(
//    private val paperCode: String,
//    private val emailId: String,
//    private val examModeId: String,
//    private val testSeriesId: String,
//    private val repository: TestRepository
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return MainViewModel(paperCode, emailId, examModeId, testSeriesId, repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//

package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssccgl.pinnacle.testportal.repository.TestRepository

class MainViewModelFactory(
    private val paperCode: String,
    private val emailId: String,
    private val examModeId: String,
    private val testSeriesId: String,
    private val repository: TestRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(paperCode, emailId, examModeId, testSeriesId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
