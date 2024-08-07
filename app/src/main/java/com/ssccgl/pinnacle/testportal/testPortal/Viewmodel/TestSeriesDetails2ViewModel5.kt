package com.ssccgl.pinnacle.testportal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ssccgl.pinnacle.testportal.network.TestSeriesAccessRequest
import com.ssccgl.pinnacle.testportal.network.TestSeriesAccessResponse
import com.ssccgl.pinnacle.testportal.network.TestSeriesDetails2Request
import com.ssccgl.pinnacle.testportal.network.TestSeriesDetails2Response
import com.ssccgl.pinnacle.testportal.repository.TestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestSeriesDetails2ViewModel(private val repository: TestRepository) : ViewModel() {

    private val _testSeriesDetails = MutableStateFlow<TestSeriesDetails2Response?>(null)
    val testSeriesDetails: StateFlow<TestSeriesDetails2Response?> = _testSeriesDetails



    fun fetchTestSeriesDetails(testSeriesId: String,emailId: String) {
        viewModelScope.launch {
            try {
                val request = TestSeriesDetails2Request(emailId , testSeriesId)
                val response = repository.fetchTestSeriesDetails2(request)
                _testSeriesDetails.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error appropriately
            }
        }
    }



    private val _userStatus = MutableStateFlow<Int?>(null)
    val userStatus: StateFlow<Int?> get() = _userStatus

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun checkTestSeriesAccess(request: List<TestSeriesAccessRequest>) {
        viewModelScope.launch {
            try {
                val response = repository.checkTestSeriesAccess(request)
                _userStatus.value = response.user_status
                _errorMessage.value = null
            } catch (e: Exception) {
               _userStatus.value = 0
                _errorMessage.value = e.message
            }
        }
    }
}






class TestSeriesDetails2ViewModelFactory(private val repository: TestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestSeriesDetails2ViewModel::class.java)) {
            return TestSeriesDetails2ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
