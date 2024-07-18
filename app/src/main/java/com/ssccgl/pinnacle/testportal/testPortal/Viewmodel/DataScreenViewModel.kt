//package com.ssccgl.pinnacle.testportal.viewmodel
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.asLiveData
//import com.ssccgl.pinnacle.testportal.network.TestSeriesDetails2Response
//import com.ssccgl.pinnacle.testportal.repository.TestRepository
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.flow.MutableStateFlow
//import retrofit2.HttpException
//import java.io.IOException
//import java.net.SocketTimeoutException
//
//class MainViewModel(
//    private val paperCode: String,
//    private val emailId: String,
//    private val examModeId: String,
//    private val testSeriesId: String,
//    private val repository: TestRepository
//) : ViewModel() {
//
//    private val _data = MutableStateFlow<List<TestSeriesDetails2Response.IndexResponse>>(emptyList())
//    val data: LiveData<List<TestSeriesDetails2Response.IndexResponse>> = _data.asLiveData()
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: LiveData<String?> = _error.asLiveData()
//
//    private val _saveAnswerResponse = MutableStateFlow<TestSeriesDetails2Response.SaveAnswerResponse?>(null)
//    val saveAnswerResponse: LiveData<TestSeriesDetails2Response.SaveAnswerResponse?> = _saveAnswerResponse.asLiveData()
//
//    private val _currentQuestionId = MutableStateFlow(1)
//    val currentQuestionId: LiveData<Int> = _currentQuestionId.asLiveData()
//
//    private val _selectedOption = MutableStateFlow("")
//    val selectedOption: LiveData<String> = _selectedOption.asLiveData()
//
//    private val _isDataDisplayed = MutableStateFlow(false)
//    val isDataDisplayed: LiveData<Boolean> = _isDataDisplayed.asLiveData()
//
//    private val _remainingCountdown = MutableStateFlow(3600L)
//    val remainingCountdown: LiveData<Long> = _remainingCountdown.asLiveData()
//
//    private val _countdownStarted = MutableStateFlow(false)
//    val countdownStarted: LiveData<Boolean> = _countdownStarted.asLiveData()
//
//    private val _elapsedTime = MutableStateFlow(0L)
//    val elapsedTime: LiveData<Long> = _elapsedTime.asLiveData()
//
//    private val _displayTime = MutableStateFlow("00:00")
//    val displayTime: LiveData<String> = _displayTime.asLiveData()
//
//    private val _selectedTabIndex = MutableStateFlow(0)
//    val selectedTabIndex: LiveData<Int> = _selectedTabIndex.asLiveData()
//
//    val selectedOptions = mutableMapOf<Int, String>()
//    val elapsedTimeMap = mutableMapOf<Int, Long>()
//    val startTimeMap = mutableMapOf<Int, Long>()
//
//    init {
//        fetchData()
//    }
//
//    private fun fetchData() {
//        viewModelScope.launch {
//            try {
//                val request = TestSeriesDetails2Response.FetchDataRequest(
//                    paper_code = paperCode,
//                    email_id = emailId,
//                    exam_mode_id = examModeId,
//                    test_series_id = testSeriesId
//                )
//                val response = repository.fetchData(request)
//                _data.value = response ?: emptyList()
//                _error.value = null
//            } catch (e: Exception) {
//                _error.value = e.message
//            }
//        }
//    }
//
//    fun saveCurrentQuestionState(questionId: Int, option: String, elapsedTime: Long) {
//        selectedOptions[questionId] = option
//        elapsedTimeMap[questionId] = elapsedTime
//    }
//
//    fun initializeElapsedTime(questionId: Int) {
//        _elapsedTime.value = elapsedTimeMap[questionId] ?: 0L
//        startTimeMap[questionId] = System.currentTimeMillis()
//    }
//
//    fun setSelectedOption(questionId: Int) {
//        _selectedOption.value = selectedOptions[questionId] ?: ""
//    }
//
//    fun updateElapsedTime(questionId: Int) {
//        val currentTime = System.currentTimeMillis()
//        val startTime = startTimeMap[questionId] ?: currentTime
//        _elapsedTime.value = (elapsedTimeMap[questionId] ?: 0L) + (currentTime - startTime) / 1000
//        _displayTime.value = formatTime(_elapsedTime.value)
//    }
//
//    fun startCountdown() {
//        viewModelScope.launch {
//            _countdownStarted.value = true
//            while (_remainingCountdown.value > 0) {
//                delay(1000L)
//                _remainingCountdown.value--
//            }
//        }
//    }
//
//    fun moveToQuestion(questionId: Int) {
//        saveCurrentQuestionState(_currentQuestionId.value, _selectedOption.value, _elapsedTime.value) // Save current question state
//        _currentQuestionId.value = questionId
//        initializeElapsedTime(questionId)
//        setSelectedOption(questionId)
//    }
//
//    fun moveToSection(index: Int) {
//        _selectedTabIndex.value = index
//        val newQuestionId = when (index) {
//            0 -> 1
//            1 -> 26
//            2 -> 51
//            3 -> 76
//            else -> 1
//        }
//        moveToQuestion(newQuestionId)
//    }
//
//    fun updateSelectedOption(option: String) {
//        _selectedOption.value = option
//    }
//
//    fun moveToPreviousQuestion() {
//        val previousQuestionId = _currentQuestionId.value - 1
//        moveToQuestion(previousQuestionId)
//    }
//
//    fun moveToNextQuestion() {
//        val nextQuestionId = _currentQuestionId.value + 1
//        moveToQuestion(nextQuestionId)
//    }
//
//    fun setIsDataDisplayed(isDisplayed: Boolean) {
//        _isDataDisplayed.value = isDisplayed
//    }
//
//    fun saveAnswer(
//        paperId: Int,
//        option: String,
//        subject: Int,
//        currentPaperId: Int,
//        remainingTime: String,
//        singleTm: String
//    ) {
//        viewModelScope.launch {
//            try {
//                val request = TestSeriesDetails2Response.SaveAnswerRequest(
//                    paper_code = paperCode,
//                    paper_id = paperId.toString(),
//                    option = option,
//                    email_id = emailId,
//                    test_series_id = testSeriesId,
//                    exam_mode_id = examModeId,
//                    subject = subject,
//                    CurrentPaperId = currentPaperId,
//                    SaveType = "nxt",
//                    answer_status = "1",
//                    rTem = remainingTime,
//                    SingleTm = singleTm
//                )
//                val response = repository.saveAnswer(request)
//                _saveAnswerResponse.value = response
//                _error.value = null
//            } catch (e: SocketTimeoutException) {
//                _error.value = "Network timeout. Please try again later. (By saveAnswer)"
//            } catch (e: IOException) {
//                _error.value = "Network error. Please check your connection. (By saveAnswer)"
//            } catch (e: HttpException) {
//                _error.value = "Server error: ${e.message}"
//            } catch (e: Exception) {
//                _error.value = e.message ?: "Unknown error (By saveAnswer)"
//            }
//        }
//    }
//
//    fun submit() {
//        viewModelScope.launch {
//            try {
//                val request = TestSeriesDetails2Response.SubmitRequest(
//                    email_id = emailId,
//                    paper_code = paperCode,
//                    exam_mode_id = examModeId,
//                    test_series_id = testSeriesId,
//                    rTem = formatTime(_remainingCountdown.value)
//                )
//                val response = repository.submit(request)
//                // Handle response if needed
//            } catch (e: SocketTimeoutException) {
//                _error.value = "Network timeout. Please try again later. (By submit)"
//            } catch (e: IOException) {
//                _error.value = "Network error. Please check your connection. (By submit)"
//            } catch (e: HttpException) {
//                _error.value = "Server error: ${e.message}"
//            } catch (e: Exception) {
//                _error.value = e.message ?: "Unknown error (By submit)"
//            }
//        }
//    }
//
//    private fun formatTime(seconds: Long): String {
//        val minutes = seconds / 60
//        val tSecond = seconds % 60
//        return String.format("%02d:%02d", minutes, tSecond)
//    }
//}

//package com.ssccgl.pinnacle.testcheck_2

package com.ssccgl.pinnacle.testportal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.ssccgl.pinnacle.testportal.network.IndexResponse
import com.ssccgl.pinnacle.testportal.network.FetchDataRequest
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance
import com.ssccgl.pinnacle.testportal.network.SaveAnswerRequest
import com.ssccgl.pinnacle.testportal.network.SaveAnswerResponse
import com.ssccgl.pinnacle.testportal.network.SubmitRequest
import com.ssccgl.pinnacle.testportal.ui.formatTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class MainViewModel(
    private val paperCode: String,
    private val emailId: String,
    private val examModeId: String,
    private val testSeriesId: String,
//    private val repository: TestRepository
) : ViewModel() {

    private val _data = MutableStateFlow<List<IndexResponse>>(emptyList())
    val data: LiveData<List<IndexResponse>> = _data.asLiveData()

    private val _error = MutableStateFlow<String?>(null)
    val error: LiveData<String?> = _error.asLiveData()

    private val _saveAnswerResponse = MutableStateFlow<SaveAnswerResponse?>(null)
    val saveAnswerResponse: LiveData<SaveAnswerResponse?> = _saveAnswerResponse.asLiveData()

    private val _currentQuestionId = MutableStateFlow(1)
    val currentQuestionId: LiveData<Int> = _currentQuestionId.asLiveData()

    private val _selectedOption = MutableStateFlow("")
    val selectedOption: LiveData<String> = _selectedOption.asLiveData()

    private val _isDataDisplayed = MutableStateFlow(false)
    val isDataDisplayed: LiveData<Boolean> = _isDataDisplayed.asLiveData()

    private val _remainingCountdown = MutableStateFlow(3600L)
    val remainingCountdown: LiveData<Long> = _remainingCountdown.asLiveData()

    private val _countdownStarted = MutableStateFlow(false)
    val countdownStarted: LiveData<Boolean> = _countdownStarted.asLiveData()

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: LiveData<Long> = _elapsedTime.asLiveData()

    private val _displayElapsedTime = MutableStateFlow("00:00:00") // Renamed
    val displayElapsedTime: LiveData<String> = _displayElapsedTime.asLiveData()

    private val _displayCountdownTime = MutableStateFlow("00:00:00") // New
    val displayCountdownTime: LiveData<String> = _displayCountdownTime.asLiveData()

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: LiveData<Int> = _selectedTabIndex.asLiveData()

    val selectedOptions = mutableMapOf<Int, String>()
    val elapsedTimeMap = mutableMapOf<Int, Long>()
    val startTimeMap = mutableMapOf<Int, Long>()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.fetchData(
                    FetchDataRequest(
                        paper_code = paperCode,
                        email_id = emailId,
                        exam_mode_id = examModeId,
                        test_series_id = testSeriesId
                    )
                )
                _data.value = response
                // Set initial answers
                response.flatMap { it.details }.forEach { detail ->
                    selectedOptions[detail.qid] =
                        detail.answer.takeIf { it.isNotBlank() } ?: ""
                }
                // Ensure the first question ID is set correctly
                if (response.isNotEmpty() && response[0].details.isNotEmpty()) {
                    _currentQuestionId.value = response[0].details[0].qid
                    setSelectedOption(response[0].details[0].qid)
                }
                _error.value = null

                fetchPaperCodeDetails()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun fetchPaperCodeDetails() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.fetchPaperCodeDetails(
                    FetchDataRequest(
                        paper_code = paperCode,
                        email_id = emailId,
                        exam_mode_id = examModeId,
                        test_series_id = testSeriesId
                    )
                )
                val totalSeconds = response.hrs * 3600 + response.mins * 60 + response.secs
                _remainingCountdown.value = totalSeconds.toLong()
                _displayCountdownTime.value = formatTime(totalSeconds.toLong())
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }


    fun saveCurrentQuestionState(questionId: Int, option: String, elapsedTime: Long) {
        selectedOptions[questionId] = option
        elapsedTimeMap[questionId] = elapsedTime
    }

    fun initializeElapsedTime(questionId: Int) {
        _elapsedTime.value = elapsedTimeMap[questionId] ?: 0L
        startTimeMap[questionId] = System.currentTimeMillis()
    }

    fun setSelectedOption(questionId: Int) {
        _selectedOption.value = selectedOptions[questionId] ?: ""
    }

    fun updateElapsedTime(questionId: Int) {
        val currentTime = System.currentTimeMillis()
        val startTime = startTimeMap[questionId] ?: currentTime
        _elapsedTime.value = (elapsedTimeMap[questionId] ?: 0L) + (currentTime - startTime) / 1000
        _displayElapsedTime.value = formatTime(_elapsedTime.value)
    }

    fun startCountdown() {
        viewModelScope.launch {
            _countdownStarted.value = true
            while (_remainingCountdown.value > 0) {
                delay(1000L)
                _remainingCountdown.value--
                _displayCountdownTime.value = formatTime(_remainingCountdown.value)
            }
        }
    }

    fun moveToQuestion(questionId: Int) {
        saveCurrentQuestionState(_currentQuestionId.value, _selectedOption.value, _elapsedTime.value) // Save current question state
        _currentQuestionId.value = questionId
        initializeElapsedTime(questionId)
        setSelectedOption(questionId)
    }

    fun moveToSection(index: Int) {
        _selectedTabIndex.value = index
        val selectedSubject = _data.value.flatMap { it.subjects }[index]
        val newQuestionId = _data.value.flatMap { it.details }.find { it.subject_id == selectedSubject.sb_id && it.qid == selectedSubject.ppr_id }?.qid ?: 1
        moveToQuestion(newQuestionId)
    }

    fun updateSelectedOption(option: String) {
        Log.d("updateSelectedOption", "Received option: $option")
        _selectedOption.value = option
        Log.d("updateSelectedOption", "Updated _selectedOption: ${_selectedOption.value}")
    }

    fun moveToPreviousQuestion() {
        val previousQuestionId = _currentQuestionId.value - 1
        moveToQuestion(previousQuestionId)
    }

    fun moveToNextQuestion() {
        val nextQuestionId = _currentQuestionId.value + 1
        moveToQuestion(nextQuestionId)
    }

    fun setIsDataDisplayed(isDisplayed: Boolean) {
        _isDataDisplayed.value = isDisplayed
    }

    fun validateOption(option: String): String {
        return if (option in listOf("a", "b", "c", "d")) option else ""
    }

    fun saveAnswer(
        paperId: Int,
        option: String,
        subject: Int,
        currentPaperId: Int,
        remainingTime: String,
        singleTm: String,
        saveType: String,
        answerStatus: String
    ) {
        val validatedOption = validateOption(option)
        Log.d("MainViewModel", "Saving answer: paperId=$paperId, option=$option, subject=$subject, currentPaperId=$currentPaperId, remainingTime=$remainingTime, singleTm=$singleTm")
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.saveAnswer(
                    SaveAnswerRequest(
                        paper_code = paperCode,
                        paper_id = paperId.toString(),
                        option = validatedOption,
                        email_id = emailId,
                        test_series_id = testSeriesId,
                        exam_mode_id = examModeId,
                        subject = subject,
                        CurrentPaperId = currentPaperId,
                        SaveType = saveType,
                        answer_status = answerStatus,
                        rTem = remainingTime,
                        SingleTm = singleTm
                    )
                )
                _saveAnswerResponse.value = response
                _error.value = null
            } catch (e: SocketTimeoutException) {
                _error.value = "Network timeout. Please try again later. (By saveAnswer)"
                Log.e("MainViewModel", "SocketTimeoutException: ${e.message}")
            } catch (e: IOException) {
                _error.value = "Network error. Please check your connection. (By saveAnswer)"
                Log.e("MainViewModel", "IOException: ${e.message}")
            } catch (e: HttpException) {
                _error.value = "Server error: ${e.message}"
                Log.e("MainViewModel", "HttpException: ${e.message}")
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error (By saveAnswer)"
                Log.e("MainViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun submit() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.submit(
                    SubmitRequest(
                        email_id = emailId,
                        paper_code = paperCode,
                        exam_mode_id = examModeId,
                        test_series_id = testSeriesId,
                        rTem = formatTime(_remainingCountdown.value)
                    )
                )
                // Handle response if needed
            } catch (e: SocketTimeoutException) {
                _error.value = "Network timeout. Please try again later. (By submit)"
                Log.e("MainViewModel", "SocketTimeoutException: ${e.message}")
            } catch (e: IOException) {
                _error.value = "Network error. Please check your connection. (By submit)"
                Log.e("MainViewModel", "IOException: ${e.message}")
            } catch (e: HttpException) {
                _error.value = "Server error: ${e.message}"
                Log.e("MainViewModel", "HttpException: ${e.message}")
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error (By submit)"
                Log.e("MainViewModel", "Exception: ${e.message}")
            }
        }
    }
}
