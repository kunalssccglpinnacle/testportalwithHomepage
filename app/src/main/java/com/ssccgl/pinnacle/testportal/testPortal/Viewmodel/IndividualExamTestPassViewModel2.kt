
package com.ssccgl.pinnacle.testportal.testPortal.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssccgl.pinnacle.testportal.network.IndividualExamTestPass
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IndividualExamTestPassViewModel2 : ViewModel() {
    private val _individualExamTestPasses = MutableStateFlow<List<IndividualExamTestPass>>(emptyList())
    val individualExamTestPasses: StateFlow<List<IndividualExamTestPass>> get() = _individualExamTestPasses.asStateFlow()

    fun fetchIndividualExamTestPasses(productId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getIndividualExamTestPass(mapOf("product_id" to productId))
                _individualExamTestPasses.value = response
            } catch (e: Exception) {
                _individualExamTestPasses.value = emptyList() // or some error state
            }
        }
    }
}
