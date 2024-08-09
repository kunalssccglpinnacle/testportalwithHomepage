
//package com.ssccgl.pinnacle.testportal.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import retrofit2.HttpException
//import com.ssccgl.pinnacle.testportal.network.*
//
//class LoginViewModel : ViewModel() {
//    private val _otpSentState = MutableStateFlow(false)
//    val otpSentState: StateFlow<Boolean> get() = _otpSentState
//    private val _otpVerifiedState = MutableStateFlow(false)
//    val otpVerifiedState: StateFlow<Boolean> get() = _otpVerifiedState
//    private val _apiResponseMessage = MutableStateFlow("")
//    val apiResponseMessage: StateFlow<String> get() = _apiResponseMessage
//    private val _emailVerifiedState = MutableStateFlow(false)
//    val emailVerifiedState: StateFlow<Boolean> get() = _emailVerifiedState
//    private val _showEmailVerificationDialog = MutableStateFlow(false)
//    val showEmailVerificationDialog: StateFlow<Boolean> get() = _showEmailVerificationDialog
//    private val _loginState = MutableStateFlow(false)
//    val loginState: StateFlow<Boolean> get() = _loginState
//
//    private val _loginData = MutableStateFlow<LoginData?>(null)
//    val loginData: StateFlow<LoginData?> get() = _loginData
//
//    private val _mobileOtpResponseData = MutableStateFlow<Data?>(null)
//    val mobileOtpResponseData: StateFlow<Data?> get() = _mobileOtpResponseData
//
//    fun login(emailOrMobile: String, password: String) {
//        viewModelScope.launch {
//            try {
//                Log.d("LoginViewModel", "Logging in with $emailOrMobile")
//                val response: LoginResponse = if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailOrMobile).matches()) {
//                    RetrofitInstance.api.loginWithEmail(LoginRequest(emailOrMobile, password))
//                } else {
//                    RetrofitInstance.api.loginWithMobile(MobileLoginRequest(emailOrMobile, password))
//                }
//                Log.d("LoginViewModel", "Login response: ${response.message}")
//                _apiResponseMessage.value = response.message
//                _loginState.value = response.status == "success"
//                if (_loginState.value) {
//                    _loginData.value = response.data
//                }
//            } catch (e: HttpException) {
//                handleHttpException(e)
//            } catch (e: Exception) {
//                handleGenericException(e)
//            }
//        }
//    }
//
//    fun sendOtp(mobileNumber: String) {
//        viewModelScope.launch {
//            try {
//                val response = RetrofitInstance.api.sendOtp(MobileOtpRequest(mobileNumber))
//                _apiResponseMessage.value = response.message
//                _otpSentState.value = response.status == "Success"
//                _mobileOtpResponseData.value = response.data
//            } catch (e: Exception) {
//                handleGenericException(e)
//                _otpSentState.value = false
//            }
//        }
//    }
//
//    fun verifyOtp(mobileNumber: String, otp: String) {
//        viewModelScope.launch {
//            try {
//                val response = RetrofitInstance.api.verifyOtp(OtpVerificationRequest(otp, mobileNumber))
//                _apiResponseMessage.value = response.message
//                _otpVerifiedState.value = response.status == "success"
//            } catch (e: Exception) {
//                handleGenericException(e)
//                _otpVerifiedState.value = false
//            }
//        }
//    }
//
//    fun sendEmailVerification(email: String) {
//        viewModelScope.launch {
//            try {
//                val response = RetrofitInstance.api.sendEmailVerification(EmailVerificationRequest(email))
//                _apiResponseMessage.value = response.message
//                _emailVerifiedState.value = response.status == "success"
//            } catch (e: Exception) {
//                handleGenericException(e)
//                _emailVerifiedState.value = false
//            }
//        }
//    }
//
//    fun verifyEmailAfterMobileVerification(id: String, email: String, fullName: String) {
//        viewModelScope.launch {
//            try {
//                Log.d("LoginViewModel", "Verifying email after mobile verification with id: $id, email: $email, fullName: $fullName")
//                val response = RetrofitInstance.api.emailVerificationAfterMobile(EmailVerificationAfterMobileRequest(id, email, fullName))
//                Log.d("LoginViewModel", "Email verification response: ${response.message}")
//                _apiResponseMessage.value = response.message
//                _emailVerifiedState.value = response.status == "success"
//                if (_emailVerifiedState.value) {
//                    _showEmailVerificationDialog.value = false
//                }
//            } catch (e: HttpException) {
//                handleHttpException(e)
//            } catch (e: Exception) {
//                handleGenericException(e)
//                _emailVerifiedState.value = false
//            }
//        }
//    }
//
//    private fun handleHttpException(e: HttpException) {
//        Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
//        _apiResponseMessage.value = "HTTP exception: ${e.message()}"
//    }
//
//    private fun handleGenericException(e: Exception) {
//        Log.e("LoginViewModel", "Exception: ${e.message}")
//        _apiResponseMessage.value = "Exception: ${e.message}"
//    }
//
//    fun resetOtpVerifiedState() {
//        _otpVerifiedState.value = false
//    }
//
//    fun resetEmailVerifiedState() {
//        _emailVerifiedState.value = false
//    }
//
//    fun showEmailVerificationDialog() {
//        _showEmailVerificationDialog.value = true
//    }
//
//    fun hideEmailVerificationDialog() {
//        _showEmailVerificationDialog.value = false
//    }
//}
//




// LoginViewModel.kt
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssccgl.pinnacle.testportal.UI.SecureStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import com.ssccgl.pinnacle.testportal.network.*

class LoginViewModel : ViewModel() {
    private val _otpSentState = MutableStateFlow(false)
    val otpSentState: StateFlow<Boolean> get() = _otpSentState
    private val _otpVerifiedState = MutableStateFlow(false)
    val otpVerifiedState: StateFlow<Boolean> get() = _otpVerifiedState
    private val _apiResponseMessage = MutableStateFlow("")
    val apiResponseMessage: StateFlow<String> get() = _apiResponseMessage
    private val _emailVerifiedState = MutableStateFlow(false)
    val emailVerifiedState: StateFlow<Boolean> get() = _emailVerifiedState
    private val _showEmailVerificationDialog = MutableStateFlow(false)
    val showEmailVerificationDialog: StateFlow<Boolean> get() = _showEmailVerificationDialog
    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> get() = _loginState

    private val _loginData = MutableStateFlow<LoginData?>(null)
    val loginData: StateFlow<LoginData?> get() = _loginData

    private val _mobileOtpResponseData = MutableStateFlow<Data?>(null)
    val mobileOtpResponseData: StateFlow<Data?> get() = _mobileOtpResponseData

    fun login(emailOrMobile: String, password: String, context: Context? = null) {
        viewModelScope.launch {
            try {
                val response: LoginResponse = if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailOrMobile).matches()) {
                    RetrofitInstance.api.loginWithEmail(LoginRequest(emailOrMobile, password))
                } else {
                    RetrofitInstance.api.loginWithMobile(MobileLoginRequest(emailOrMobile, password))
                }
                _apiResponseMessage.value = response.message
                _loginState.value = response.status == "success"
                if (_loginState.value) {
                    _loginData.value = response.data
                    context?.let {
                        SecureStorage.saveCredentials(it, emailOrMobile, password)
                    }
                }
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                handleGenericException(e)
            }
        }
    }

    fun autoLogin(context: Context) {
        val (emailOrMobile, password) = SecureStorage.getCredentials(context)
        if (!emailOrMobile.isNullOrEmpty() && !password.isNullOrEmpty()) {
            login(emailOrMobile, password)
        }
    }

    fun logout(context: Context) {
        SecureStorage.clearCredentials(context)
        _loginState.value = false
        _loginData.value = null
    }



    fun sendOtp(mobileNumber: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.sendOtp(MobileOtpRequest(mobileNumber))
                _apiResponseMessage.value = response.message
                _otpSentState.value = response.status == "Success"
                _mobileOtpResponseData.value = response.data
            } catch (e: Exception) {
                handleGenericException(e)
                _otpSentState.value = false
            }
        }
    }

    fun verifyOtp(mobileNumber: String, otp: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.verifyOtp(OtpVerificationRequest(otp, mobileNumber))
                _apiResponseMessage.value = response.message
                _otpVerifiedState.value = response.status == "success"
            } catch (e: Exception) {
                handleGenericException(e)
                _otpVerifiedState.value = false
            }
        }
    }

    fun sendEmailVerification(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.sendEmailVerification(EmailVerificationRequest(email))
                _apiResponseMessage.value = response.message
                _emailVerifiedState.value = response.status == "success"
            } catch (e: Exception) {
                handleGenericException(e)
                _emailVerifiedState.value = false
            }
        }
    }

    fun verifyEmailAfterMobileVerification(id: String, email: String, fullName: String) {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Verifying email after mobile verification with id: $id, email: $email, fullName: $fullName")
                val response = RetrofitInstance.api.emailVerificationAfterMobile(EmailVerificationAfterMobileRequest(id, email, fullName))
                Log.d("LoginViewModel", "Email verification response: ${response.message}")
                _apiResponseMessage.value = response.message
                _emailVerifiedState.value = response.status == "success"
                if (_emailVerifiedState.value) {
                    _showEmailVerificationDialog.value = false
                }
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                handleGenericException(e)
                _emailVerifiedState.value = false
            }
        }
    }

    private fun handleHttpException(e: HttpException) {
        _apiResponseMessage.value = "HTTP exception: ${e.message()}"
    }

    private fun handleGenericException(e: Exception) {
        _apiResponseMessage.value = "Exception: ${e.message}"
    }

    fun resetOtpVerifiedState() {
        _otpVerifiedState.value = false
    }

    fun resetEmailVerifiedState() {
        _emailVerifiedState.value = false
    }

    fun showEmailVerificationDialog() {
        _showEmailVerificationDialog.value = true
    }

    fun hideEmailVerificationDialog() {
        _showEmailVerificationDialog.value = false
    }
}
