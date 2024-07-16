//package com.ssccgl.pinnacle.testportal.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import com.ssccgl.pinnacle.testportal.network.NewTestsWebRequest
//import com.ssccgl.pinnacle.testportal.network.TestSeriesRequest
//import com.ssccgl.pinnacle.testportal.network.TestSeriesResponse
//import com.ssccgl.pinnacle.testportal.repository.TestRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class TestSeriesViewModel(private val repository: TestRepository) : ViewModel() {
//
//    private val _testSeries = MutableStateFlow<List<TestSeriesResponse>>(emptyList())
//    val testSeries: StateFlow<List<TestSeriesResponse>> = _testSeries
//
//    fun fetchTestSeriesBasedOnNewTestsWebResponse(emailId: String, examPostId: String, examId: String, tierId: String) {
//        viewModelScope.launch {
//            try {
//                val newTestsWebRequests = listOf(NewTestsWebRequest(emailId, examPostId, examId, tierId))
//                val newTestsWebResponses = repository.getNewTestsWeb(newTestsWebRequests)
//                val testSeriesData = mutableListOf<TestSeriesResponse>()
//                newTestsWebResponses?.forEach { response ->
//                    response.TestType?.forEach { testType ->
//                        val testSeriesRequest = TestSeriesRequest(emailId, testType.exam_mode_id, examPostId)
//                        val testSeriesResponse = repository.fetchTestSeries(testSeriesRequest)
//                        testSeriesResponse?.let { testSeriesData.addAll(it) }
//                    }
//                }
//                _testSeries.value = testSeriesData
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // Handle error appropriately
//                _testSeries.value = emptyList() // Set to empty list on error
//            }
//        }
//    }
//}
//
//class TestSeriesViewModelFactory(private val repository: TestRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(TestSeriesViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return TestSeriesViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ssccgl.pinnacle.testportal.network.TestSeriesRequest
import com.ssccgl.pinnacle.testportal.network.TestSeriesResponse
import com.ssccgl.pinnacle.testportal.repository.TestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestSeriesViewModel(private val repository: TestRepository) : ViewModel() {

    private val _testSeries = MutableStateFlow<List<TestSeriesResponse>>(emptyList())
    val testSeries: StateFlow<List<TestSeriesResponse>> = _testSeries

    fun fetchTestSeriesBasedOnNewTestsWebResponse(emailId: String, examPostId: String, examModeId: String) {
        viewModelScope.launch {
            try {
                val testSeriesRequest = TestSeriesRequest(emailId, examModeId, examPostId)
                val testSeriesResponse = repository.fetchTestSeries(testSeriesRequest)
                _testSeries.value = testSeriesResponse ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                _testSeries.value = emptyList() // Set to empty list on error
            }
        }
    }
}

class TestSeriesViewModelFactory(private val repository: TestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestSeriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestSeriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
