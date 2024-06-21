package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Test(
    val name: String,
    val category: String,
    val description: String
)

class TestPortalViewModel : ViewModel() {

    private val _tests = MutableStateFlow<List<Test>>(emptyList())
    val tests: StateFlow<List<Test>> = _tests

    private val _selectedCategory = MutableStateFlow("SSC")
    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        loadTests()
    }

    private fun loadTests() {
        // Simulate loading data from a repository or network
        viewModelScope.launch {
            val testList = listOf(
                Test("SSC CGL Tier 1", "SSC", "Description for SSC CGL Tier 1"),
                Test("SSC CGL Tier 2", "SSC", "Description for SSC CGL Tier 2"),
                Test("SSC CHSL Tier 1", "SSC", "Description for SSC CHSL Tier 1"),
                Test("SSC CHSL Tier 2", "SSC", "Description for SSC CHSL Tier 2"),
                Test("Delhi Police Exam", "Delhi Police", "Description for Delhi Police Exam"),
                Test("Railway Exam", "Railway", "Description for Railway Exam")
            )
            _tests.value = testList
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun getTestsByCategory(category: String): List<Test> {
        return _tests.value.filter { it.category == category }
    }
}
