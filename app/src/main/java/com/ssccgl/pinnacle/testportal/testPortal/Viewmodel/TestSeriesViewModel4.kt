//package com.ssccgl.pinnacle.testportal.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import com.ssccgl.pinnacle.testportal.network.NewTestsWebResponse
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
//    fun fetchTestSeriesBasedOnNewTestsWebResponse(
//        emailId: String,
//        response: List<NewTestsWebResponse>
//    ) {
//        viewModelScope.launch {
//            try {
//                val requests = mapNewTestsWebResponseToFetchTestSeriesRequest(emailId, response)
//                val testSeriesResponses = requests.flatMap { request ->
//                    repository.fetchTestSeries(request) ?: emptyList()
//                }
//                _testSeries.value = testSeriesResponses
//            } catch (e: Exception) {
//                e.printStackTrace()
//                _testSeries.value = emptyList()
//            }
//        }
//    }
//
//    private fun mapNewTestsWebResponseToFetchTestSeriesRequest(
//        emailId: String,
//        response: List<NewTestsWebResponse>
//    ): List<TestSeriesRequest> {
//        return response.flatMap { newTestResponse ->
//            newTestResponse.TestType.map { testType ->
//                TestSeriesRequest(
//                    email_id = emailId,
//                    exam_mode_id = testType.exam_mode_id?.toString() ?: "",
//                    exam_post_id = newTestResponse.ExamPostTier?.toString() ?: "" // Use tier_id as exam_post_id
//                )
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

    fun fetchTestSeries(emailId: String, tierId: String, examModeId: Int) {
        viewModelScope.launch {
            try {
                val request = TestSeriesRequest(
                    email_id = emailId,
                    exam_mode_id = examModeId.toString(),
                    exam_post_id = tierId
                )
                val testSeriesResponses = repository.fetchTestSeries(request) ?: emptyList()
                _testSeries.value = testSeriesResponses
            } catch (e: Exception) {
                e.printStackTrace()
                _testSeries.value = emptyList()
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
