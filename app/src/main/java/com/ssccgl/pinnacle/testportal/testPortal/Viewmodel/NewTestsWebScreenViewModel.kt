//package com.ssccgl.pinnacle.testportal.testPortal.Viewmodel
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.ssccgl.pinnacle.testportal.network.NewTestsWebRequest
//import com.ssccgl.pinnacle.testportal.network.NewTestsWebResponse
//import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import retrofit2.HttpException
//
//class NewTestsWebViewModel : ViewModel() {
//
//    private val _newTestsWebData = MutableStateFlow<List<NewTestsWebResponse>>(emptyList())
//    val newTestsWebData: StateFlow<List<NewTestsWebResponse>> get() = _newTestsWebData.asStateFlow()
//
//    private val _totalTests = MutableStateFlow(0)
//    val totalTests: StateFlow<Int> get() = _totalTests.asStateFlow()
//
//    private val _chapterTests = MutableStateFlow(0)
//    val chapterTests: StateFlow<Int> get() = _chapterTests.asStateFlow()
//
//    private val _sectionalTests = MutableStateFlow(0)
//    val sectionalTests: StateFlow<Int> get() = _sectionalTests.asStateFlow()
//
//    private val _previousYearTests = MutableStateFlow(0)
//    val previousYearTests: StateFlow<Int> get() = _previousYearTests.asStateFlow()
//
//    fun fetchNewTestsWebData(emailId: String, examPostId: String, examId: String, tierId: String) {
//        viewModelScope.launch {
//            try {
//                val request = listOf(
//                    NewTestsWebRequest(
//                        email_id = emailId,
//                        exam_post_tier_id = examPostId,
//                        exam_id = examId,
//                        tier_id = tierId
//                    )
//                )
//                Log.d("NewTestsWebViewModel", "Fetching data with request: $request")
//                val response = RetrofitInstance.api.getNewTestsWeb(request)
//                _newTestsWebData.value = response
//
//                _totalTests.value = response.sumOf { it.TestType.sumOf { testType -> testType.TotalTests } }
//
//                var chapterTests = 0
//                var sectionalTests = 0
//                var previousYearTests = 0
//
//                response.forEach { newTestResponse ->
//                    newTestResponse.TestType.forEach { testType ->
//                        when (testType.test_type) {
//                            "Chapter Test" -> chapterTests += testType.TotalTests
//                            "Sectional Test" -> sectionalTests += testType.TotalTests
//                            "Previous Year Test" -> previousYearTests += testType.TotalTests
//                        }
//                    }
//                }
//
//                _chapterTests.value = chapterTests
//                _sectionalTests.value = sectionalTests
//                _previousYearTests.value = previousYearTests
//
//                Log.d("NewTestsWebViewModel", "API Response: $response")
//            } catch (e: HttpException) {
//                _newTestsWebData.value = emptyList()
//                _totalTests.value = 0
//                _chapterTests.value = 0
//                _sectionalTests.value = 0
//                _previousYearTests.value = 0
//                Log.e("NewTestsWebViewModel", "HTTP Error: ${e.code()} - ${e.message()}", e)
//            } catch (e: Exception) {
//                _newTestsWebData.value = emptyList()
//                _totalTests.value = 0
//                _chapterTests.value = 0
//                _sectionalTests.value = 0
//                _previousYearTests.value = 0
//                Log.e("NewTestsWebViewModel", "API Error: ${e.message}", e)
//            }
//        }
//    }
//}
package com.ssccgl.pinnacle.testportal.testPortal.Viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssccgl.pinnacle.testportal.network.NewTestsWebRequest
import com.ssccgl.pinnacle.testportal.network.NewTestsWebResponse
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewTestsWebViewModel : ViewModel() {

    private val _newTestsWebData = MutableStateFlow<List<NewTestsWebResponse>>(emptyList())
    val newTestsWebData: StateFlow<List<NewTestsWebResponse>> get() = _newTestsWebData.asStateFlow()

    private val _totalTests = MutableStateFlow(0)
    val totalTests: StateFlow<Int> get() = _totalTests.asStateFlow()

    private val _chapterTests = MutableStateFlow(0)
    val chapterTests: StateFlow<Int> get() = _chapterTests.asStateFlow()

    private val _sectionalTests = MutableStateFlow(0)
    val sectionalTests: StateFlow<Int> get() = _sectionalTests.asStateFlow()

    private val _previousYearTests = MutableStateFlow(0)
    val previousYearTests: StateFlow<Int> get() = _previousYearTests.asStateFlow()

    private val _examModeId = MutableStateFlow<String>("")
    val examModeId: StateFlow<String> get() = _examModeId.asStateFlow()

    private val _examPostId = MutableStateFlow<String>("")
    val examPostId: StateFlow<String> get() = _examPostId.asStateFlow()

    fun fetchNewTestsWebData(emailId: String, examPostId: String, examId: String, tierId: String) {
        viewModelScope.launch {
            try {
                val request = listOf(
                    NewTestsWebRequest(
                        email_id = emailId,
                        exam_post_tier_id = if (examPostId.isNotBlank()) examPostId else "1",
                        exam_id = if (examId.isNotBlank()) examId else "1",
                        tier_id = if (tierId.isNotBlank()) tierId else "1"
                    )
                )
                Log.d("NewTestsWebViewModel", "Fetching data with request: $request")
                val response = RetrofitInstance.api.getNewTestsWeb(request)
                _newTestsWebData.value = response

                _totalTests.value = response.sumOf { it.TestType.sumOf { testType -> testType.TotalTests } }

                var chapterTests = 0
                var sectionalTests = 0
                var previousYearTests = 0

                response.forEach { newTestResponse ->
                    newTestResponse.TestType.forEach { testType ->
                        when (testType.test_type) {
                            "Chapter Test" -> chapterTests += testType.TotalTests
                            "Sectional Test" -> sectionalTests += testType.TotalTests
                            "Previous Year Test" -> previousYearTests += testType.TotalTests
                        }
                    }
                }

                _chapterTests.value = chapterTests
                _sectionalTests.value = sectionalTests
                _previousYearTests.value = previousYearTests

                if (response.isNotEmpty()) {
                    _examModeId.value = (response[0].TestType[0].exam_mode_id ?: "").toString()
                    _examPostId.value = examPostId
                }

                Log.d("NewTestsWebViewModel", "API Response: $response")
            } catch (e: HttpException) {
                _newTestsWebData.value = emptyList()
                _totalTests.value = 0
                _chapterTests.value = 0
                _sectionalTests.value = 0
                _previousYearTests.value = 0
                Log.e("NewTestsWebViewModel", "HTTP Error: ${e.code()} - ${e.message()}", e)
            } catch (e: Exception) {
                _newTestsWebData.value = emptyList()
                _totalTests.value = 0
                _chapterTests.value = 0
                _sectionalTests.value = 0
                _previousYearTests.value = 0
                Log.e("NewTestsWebViewModel", "API Error: ${e.message}", e)
            }
        }
    }
}
