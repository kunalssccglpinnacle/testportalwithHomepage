//package com.ssccgl.pinnacle.testportal.testPortal.Viewmodel
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import com.ssccgl.pinnacle.testportal.network.ApiService
//import com.ssccgl.pinnacle.testportal.network.ExamPostRequest
//import com.ssccgl.pinnacle.testportal.network.ExamPostResponse
////import com.ssccgl.pinnacle.testportal.network.NetworkService
//import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//class NewTestsWebViewModel : ViewModel() {
//
//    private val _examPostResponse = MutableStateFlow<List<ExamPostResponse>?>(null)
//    val examPostResponse: StateFlow<List<ExamPostResponse>?> = _examPostResponse
//
//    fun fetchExamPostData() {
//        viewModelScope.launch {
//            val request = listOf(
//                ExamPostRequest(
//                    email_id = "harishmodi@129@gmail.com",
//                    exam_post_tier_id = "1",
//                    exam_id = "2",
//                    tier_id = "1"
//                )
//            )
//            try {
//
//                val response = RetrofitInstance.api.getExamPostData(request)
//
//               // val response = NetworkService.api.getExamPostData(request)
//                _examPostResponse.value = response
//                Log.d("NewTestsWebViewModel", "API response: $response")
//            } catch (e: Exception) {
//                Log.e("NewTestsWebViewModel", "Error fetching data", e)
//            }
//        }
//    }
//}


package com.ssccgl.pinnacle.testportal.testPortal.Viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.ssccgl.pinnacle.testportal.network.ApiService
import com.ssccgl.pinnacle.testportal.network.ExamPostRequest
import com.ssccgl.pinnacle.testportal.network.ExamPostResponse
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import com.ssccgl.pinnacle.testportal.viewmodel.IndividualExamPostViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class NewTestsWebViewModel(
    private val individualExamPostViewModel: IndividualExamPostViewModel
) : ViewModel() {

    private val _examPostResponse = MutableStateFlow<List<ExamPostResponse>?>(null)
    val examPostResponse: StateFlow<List<ExamPostResponse>?> = _examPostResponse

    fun fetchExamPostData() {
        viewModelScope.launch {
            individualExamPostViewModel.individualExamPosts.collect { individualExamPosts ->
                individualExamPosts.firstOrNull()?.let { post ->
                    val request = listOf(
                        ExamPostRequest(
                            email_id = "harishmodi@129@gmail.com",
                            exam_post_tier_id = post.id.toString(),
                            exam_id = post.exam_id.toString(),
                            tier_id = post.id.toString()
                        )
                    )
                    try {
                        val response = RetrofitInstance.api.getExamPostData(request)
                        _examPostResponse.value = response
                        Log.d("NewTestsWebViewModel", "API response: $response")
                    } catch (e: Exception) {
                        Log.e("NewTestsWebViewModel", "Error fetching data", e)
                    }
                }
            }
        }
    }
}

