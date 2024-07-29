package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.*
import com.ssccgl.pinnacle.testportal.network.AttemptedRequest
import com.ssccgl.pinnacle.testportal.network.AttemptedResponse
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import kotlinx.coroutines.launch

class ResultViewModel(
    private val paperCode: String,
    private val emailId: String,
    private val examModeId: String,
    private val testSeriesId: String
) : ViewModel() {

    private val _result = MutableLiveData<List<AttemptedResponse>>()
    val result: LiveData<List<AttemptedResponse>> = _result

    init {
        fetchResult()
    }

    private fun fetchResult() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.fetchAttempted(
                    AttemptedRequest(
                        paper_code = paperCode,
                        email_id = emailId,
                        exam_mode_id = examModeId,
                        test_series_id = testSeriesId
                    )
                )
                _result.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

class ResultViewModelFactory(
    private val paperCode: String,
    private val emailId: String,
    private val examModeId: String,
    private val testSeriesId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(paperCode, emailId, examModeId, testSeriesId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
