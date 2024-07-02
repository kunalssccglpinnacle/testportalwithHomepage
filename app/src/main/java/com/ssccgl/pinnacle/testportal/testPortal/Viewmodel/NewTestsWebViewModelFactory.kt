

package com.ssccgl.pinnacle.testportal.testPortal.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.ssccgl.pinnacle.testportal.viewmodel.IndividualExamPostViewModel

class NewTestsWebViewModelFactory(
    private val individualExamPostViewModel: IndividualExamPostViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(NewTestsWebViewModel::class.java)) {
            return NewTestsWebViewModel(individualExamPostViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
