
package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.*
import com.ssccgl.pinnacle.testportal.network.AttemptedRequest
import com.ssccgl.pinnacle.testportal.network.AttemptedResponse
import com.ssccgl.pinnacle.testportal.network.SolutionRequest
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import com.ssccgl.pinnacle.testportal.network.SolutionResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow

class SolutionViewModel : ViewModel() {

    private val _solutionData = MutableStateFlow<List<SolutionResponse>>(emptyList())
    val solutionData: LiveData<List<SolutionResponse>> get() = _solutionData.asLiveData()

    private val _error = MutableStateFlow<String?>(null)
    val error: LiveData<String?> get() = _error.asLiveData()

    private val _currentQuestionId = MutableStateFlow(1)
    val currentQuestionId: LiveData<Int> get() = _currentQuestionId.asLiveData()

    private val _selectedOption = MutableStateFlow("")
    val selectedOption: LiveData<String> get() = _selectedOption.asLiveData()

    private val selectedOptions = mutableMapOf<Int, String>()

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: LiveData<Int> = _selectedTabIndex.asLiveData()

    private val _attemptedResponse = MutableStateFlow<AttemptedResponse?>(null)
    val attemptedResponse: StateFlow<AttemptedResponse?> = _attemptedResponse


    fun fetchSolutionData(request: SolutionRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.fetchSolutionData(request)
                _solutionData.value = response

                response.flatMap { it.details }.forEach { detail ->
                    selectedOptions[detail.qid] = detail.choose_answer.takeIf { it.isNotBlank() } ?: ""
                }

                response.firstOrNull()?.details?.firstOrNull()?.qid?.let {
                    _currentQuestionId.value = it
                    setSelectedOption(it)
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun fetchAttemptedData( request: AttemptedRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.fetchAttempted(request)
                _attemptedResponse.value = response.firstOrNull() // Assuming the response is a list and you need the first item
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun moveToSection(index: Int) {
        val selectedSubject = _solutionData.value.flatMap {it.subjects} [index]
        val newQuestionId = _solutionData.value.flatMap {it.details}
            .find {it.subject_id == selectedSubject.sb_id && it.qid == selectedSubject.ppr_id}?.qid ?: 1
        moveToQuestion(newQuestionId)
        _selectedTabIndex.value = index
    }

    private fun setSelectedOption(questionId: Int) {
        _selectedOption.value = selectedOptions[questionId] ?: ""
    }

    fun updateSelectedOption(option: String) {
        val currentQuestionId = _currentQuestionId.value
        selectedOptions[currentQuestionId] = option
        _selectedOption.value = option
    }

    fun moveToPreviousQuestion() {
        val previousQuestionId = (_currentQuestionId.value - 1).coerceAtLeast(1)
        _currentQuestionId.value = previousQuestionId
        setSelectedOption(previousQuestionId)
    }

    fun moveToNextQuestion() {
        val nextQuestionId = _currentQuestionId.value + 1
        if (nextQuestionId <= (_solutionData.value.flatMap { it.details }.maxOfOrNull { it.qid }
                ?: 0)) {
            _currentQuestionId.value = nextQuestionId
            setSelectedOption(nextQuestionId)
        }
    }

    fun moveToQuestion(questionId: Int){
        _currentQuestionId.value = questionId
    }
}


