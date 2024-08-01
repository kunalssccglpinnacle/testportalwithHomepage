//
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
//    lateinit var mobileOtpResponseData: Data
//
//    fun sendOtp(mobileNumber: String) {
//        viewModelScope.launch {
//            try {
//                Log.d("LoginViewModel", "Sending OTP to $mobileNumber")
//                val response: MobileOtpResponse = RetrofitInstance.api.sendOtp(MobileOtpRequest(mobileNumber))
//                Log.d("LoginViewModel", "OTP sent successfully: ${response.message}")
//                _apiResponseMessage.value = response.message
//                mobileOtpResponseData = response.data
//                _otpSentState.value = true
//            } catch (e: HttpException) {
//                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
//                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
//                _otpSentState.value = false
//            } catch (e: Exception) {
//                Log.e("LoginViewModel", "Exception: ${e.message}")
//                _apiResponseMessage.value = "Exception: ${e.message}"
//                _otpSentState.value = false
//            }
//        }
//    }
//
//    fun verifyOtp(mobileNumber: String, otp: String) {
//        viewModelScope.launch {
//            try {
//                Log.d("LoginViewModel", "Verifying OTP for $mobileNumber")
//                val response: OtpVerificationResponse = RetrofitInstance.api.verifyOtp(OtpVerificationRequest(otp, mobileNumber))
//                Log.d("LoginViewModel", "OTP verification response: ${response.message}")
//                _apiResponseMessage.value = response.message
//                _otpVerifiedState.value = response.status == "success"
//                if (_otpVerifiedState.value) {
//                    _showEmailVerificationDialog.value = true
//                }
//            } catch (e: HttpException) {
//                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
//                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
//                _otpVerifiedState.value = false
//            } catch (e: Exception) {
//                Log.e("LoginViewModel", "Exception: ${e.message}")
//                _apiResponseMessage.value = "Exception: ${e.message}"
//                _otpVerifiedState.value = false
//            }
//        }
//    }
//
//    fun sendEmailVerification(email: String) {
//        viewModelScope.launch {
//            try {
//                Log.d("LoginViewModel", "Sending email verification to $email")
//                val response: EmailVerificationResponse = RetrofitInstance.api.sendEmailVerification(EmailVerificationRequest(email))
//                Log.d("LoginViewModel", "Email verification sent successfully: ${response.message}")
//                _apiResponseMessage.value = response.message
//                _emailVerifiedState.value = response.status == "Success"
//            } catch (e: HttpException) {
//                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
//                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
//                _emailVerifiedState.value = false
//            } catch (e: Exception) {
//                Log.e("LoginViewModel", "Exception: ${e.message}")
//                _apiResponseMessage.value = "Exception: ${e.message}"
//                _emailVerifiedState.value = false
//            }
//        }
//    }
//
//    fun verifyEmailAfterMobileVerification(_id: String, email: String, fullName: String) {
//        viewModelScope.launch {
//            try {
//                Log.d("LoginViewModel", "Verifying email after mobile verification")
//                val response = RetrofitInstance.api.emailVerificationAfterMobile(EmailVerificationAfterMobileRequest(_id, email, fullName))
//                Log.d("LoginViewModel", "Email verification response: ${response.message}")
//                _apiResponseMessage.value = response.message
//                _emailVerifiedState.value = response.status == "Success"
//                _showEmailVerificationDialog.value = false
//            } catch (e: HttpException) {
//                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
//                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
//                _emailVerifiedState.value = false
//            } catch (e: Exception) {
//                Log.e("LoginViewModel", "Exception: ${e.message}")
//                _apiResponseMessage.value = "Exception: ${e.message}"
//                _emailVerifiedState.value = false
//            }
//        }
//    }
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
//            } catch (e: HttpException) {
//                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
//                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
//                _loginState.value = false
//            } catch (e: Exception) {
//                Log.e("LoginViewModel", "Exception: ${e.message}")
//                _apiResponseMessage.value = "Exception: ${e.message}"
//                _loginState.value = false
//            }
//        }
//    }
//
//    fun resetOtpSentState() {
//        _otpSentState.value = false
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


package com.ssccgl.pinnacle.testportal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    lateinit var mobileOtpResponseData: Data

    fun sendOtp(mobileNumber: String) {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Sending OTP to $mobileNumber")
                val response: MobileOtpResponse = RetrofitInstance.api.sendOtp(MobileOtpRequest(mobileNumber))
                Log.d("LoginViewModel", "OTP sent successfully: ${response.message}")
                _apiResponseMessage.value = response.message
                mobileOtpResponseData = response.data
                _otpSentState.value = true
            } catch (e: HttpException) {
                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
                _otpSentState.value = false
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}")
                _apiResponseMessage.value = "Exception: ${e.message}"
                _otpSentState.value = false
            }
        }
    }

    fun verifyOtp(mobileNumber: String, otp: String) {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Verifying OTP for $mobileNumber")
                val response: OtpVerificationResponse = RetrofitInstance.api.verifyOtp(OtpVerificationRequest(otp, mobileNumber))
                Log.d("LoginViewModel", "OTP verification response: ${response.message}")
                _apiResponseMessage.value = response.message
                _otpVerifiedState.value = response.status == "success"
                if (_otpVerifiedState.value) {
                    _showEmailVerificationDialog.value = true
                }
            } catch (e: HttpException) {
                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
                _otpVerifiedState.value = false
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}")
                _apiResponseMessage.value = "Exception: ${e.message}"
                _otpVerifiedState.value = false
            }
        }
    }

    fun sendEmailVerification(email: String) {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Sending email verification to $email")
                val response: EmailVerificationResponse = RetrofitInstance.api.sendEmailVerification(EmailVerificationRequest(email))
                Log.d("LoginViewModel", "Email verification sent successfully: ${response.message}")
                _apiResponseMessage.value = response.message
                _emailVerifiedState.value = response.status == "Success"
            } catch (e: HttpException) {
                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
                _emailVerifiedState.value = false
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}")
                _apiResponseMessage.value = "Exception: ${e.message}"
                _emailVerifiedState.value = false
            }
        }
    }

    fun verifyEmailAfterMobileVerification(_id: String, email: String, fullName: String) {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Verifying email after mobile verification")
                val response = RetrofitInstance.api.emailVerificationAfterMobile(EmailVerificationAfterMobileRequest(_id, email, fullName))
                Log.d("LoginViewModel", "Email verification response: ${response.message}")
                _apiResponseMessage.value = response.message
                _emailVerifiedState.value = response.status == "Success"
                _showEmailVerificationDialog.value = false
            } catch (e: HttpException) {
                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
                _emailVerifiedState.value = false
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}")
                _apiResponseMessage.value = "Exception: ${e.message}"
                _emailVerifiedState.value = false
            }
        }
    }

    fun login(emailOrMobile: String, password: String) {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Logging in with $emailOrMobile")
                val response: LoginResponse = if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailOrMobile).matches()) {
                    RetrofitInstance.api.loginWithEmail(LoginRequest(emailOrMobile, password))
                } else {
                    RetrofitInstance.api.loginWithMobile(MobileLoginRequest(emailOrMobile, password))
                }
                Log.d("LoginViewModel", "Login response: ${response.message}")
                _apiResponseMessage.value = response.message
                _loginState.value = response.status == "success"
            } catch (e: HttpException) {
                Log.e("LoginViewModel", "HTTP exception: ${e.message()}")
                _apiResponseMessage.value = "HTTP exception: ${e.message()}"
                _loginState.value = false
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception: ${e.message}")
                _apiResponseMessage.value = "Exception: ${e.message}"
                _loginState.value = false
            }
        }
    }

    fun resetOtpSentState() {
        _otpSentState.value = false
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
