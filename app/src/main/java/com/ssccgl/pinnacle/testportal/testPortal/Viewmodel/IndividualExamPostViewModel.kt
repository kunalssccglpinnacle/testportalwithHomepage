package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.ssccgl.pinnacle.testportal.network.IndividualExamPost
import com.ssccgl.pinnacle.testportal.network.NetworkService

class IndividualExamPostViewModel : ViewModel() {
    private val _individualExamPosts = MutableStateFlow<List<IndividualExamPost>>(emptyList())
    val individualExamPosts: StateFlow<List<IndividualExamPost>> = _individualExamPosts

    fun fetchIndividualExamPostData(examPostId: Int) {
        viewModelScope.launch {
            try {
                val response = NetworkService.api.getIndividualExamPostData(mapOf("exam_post_id" to examPostId))
                _individualExamPosts.value = response
            } catch (e: Exception) {
                // Handle the error
            }
        }
    }
}
