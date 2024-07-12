
package com.ssccgl.pinnacle.testportal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import com.ssccgl.pinnacle.testportal.network.TestPass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestPassViewModel : ViewModel() {

    private val _testPasses = MutableStateFlow<List<TestPass>>(emptyList())
    val testPasses: StateFlow<List<TestPass>> = _testPasses

    init {
        fetchTestPasses()
    }

    private fun fetchTestPasses() {
        viewModelScope.launch {
            try {
                val testPassList = RetrofitInstance.api.getTestPasses()
                _testPasses.value = testPassList
                Log.d("TestPassViewModel", "Fetched test passes: $testPassList")
            } catch (e: Exception) {
                Log.e("TestPassViewModel", "Error fetching test passes", e)
            }
        }
    }
}
