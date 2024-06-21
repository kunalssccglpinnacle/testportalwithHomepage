package com.ssccgl.pinnacle.testportal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssccgl.pinnacle.testportal.data.HomeItem
import com.ssccgl.pinnacle.testportal.data.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(emptyList()))
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchHomeItems()
    }

    private fun fetchHomeItems() {
        viewModelScope.launch {
            val items = homeRepository.getHomeItems()
            _uiState.value = HomeUiState(items)
        }
    }
}

data class HomeUiState(
    val items: List<HomeItem>
)
