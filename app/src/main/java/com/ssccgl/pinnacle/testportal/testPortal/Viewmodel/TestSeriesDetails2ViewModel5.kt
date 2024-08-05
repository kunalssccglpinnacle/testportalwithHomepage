package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
}

class TestSeriesDetails2ViewModelFactory(private val repository: TestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestSeriesDetails2ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestSeriesDetails2ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
