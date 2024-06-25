package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ssccgl.pinnacle.testportal.network.ExamPost
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExamPostViewModel : ViewModel() {
    private val _examPosts = MutableStateFlow<List<ExamPost>>(emptyList())
    val examPosts: StateFlow<List<ExamPost>> = _examPosts

    init {
        fetchExamPosts()
    }

    fun fetchExamPosts() {
        viewModelScope.launch {
            val request = mapOf("email_id" to "kunalsharma@ssccglpinnacle.com", "exam_post_tier_id" to "1")
            val response = try {
                RetrofitInstance.api.fetchExamPosts(request)
            } catch (e: IOException) {
                // Handle IOException
                return@launch
            } catch (e: HttpException) {
                // Handle HttpException
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                _examPosts.value = response.body()!!
            }
        }
    }
}
