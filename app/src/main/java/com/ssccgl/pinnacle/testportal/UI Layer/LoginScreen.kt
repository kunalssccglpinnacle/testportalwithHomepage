

package com.ssccgl.pinnacle.testportal.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ssccgl.pinnacle.testportal.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    var mobileNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var isMobileNumberValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val otpSentState by loginViewModel.otpSentState.collectAsState()
    val otpVerifiedState by loginViewModel.otpVerifiedState.collectAsState()
    val emailVerifiedState by loginViewModel.emailVerifiedState.collectAsState()
    val apiResponseMessage by loginViewModel.apiResponseMessage.collectAsState()
    val loginState by loginViewModel.loginState.collectAsState()
    val mobileOtpResponseData by loginViewModel.mobileOtpResponseData.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp)
            ) {
                Text(
                    text = "Get started with Pinnacle",
                    style = MaterialTheme.typography.h6,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.height(56.dp)
                ) {
                    Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }) {
                        Text(text = "Register", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }) {
                        Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (selectedTabIndex == 0) {
                    RegisterSection(
                        mobileNumber = mobileNumber,
                        isMobileNumberValid = isMobileNumberValid,
                        onMobileNumberChange = { mobileNumber = it },
                        email = email,
                        isEmailValid = isEmailValid,
                        onEmailChange = { email = it },
                        onSendOtpClick = {
                            if (isMobileNumberValid) {
                                loginViewModel.sendOtp(mobileNumber)
                            }
                        },
                        onVerifyEmailClick = {
                            if (isEmailValid) {
                                loginViewModel.sendEmailVerification(email)
                            }
                        }
                    )

                    if (otpSentState) {
                        VerifyOtpSection(
                            mobileNumber = mobileNumber,
                            otp = otp,
                            onOtpChange = { otp = it },
                            onVerifyOtpClick = { loginViewModel.verifyOtp(mobileNumber, otp) }
                        )
                    }
                } else {
                    LoginSection(
                        email = email,
                        onEmailChange = { email = it },
                        password = password,
                        onPasswordChange = { password = it },
                        onLoginClick = { loginViewModel.login(email, password) }
                    )
                }

                if (apiResponseMessage.isNotEmpty()) {
                    Text(
                        text = apiResponseMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    )

    LaunchedEffect(apiResponseMessage) {
        if (apiResponseMessage.isNotEmpty()) {
            Toast.makeText(context, apiResponseMessage, Toast.LENGTH_LONG).show()
        }
    }

    ShowSnackBar(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        message = apiResponseMessage,
        condition = otpSentState && !otpVerifiedState
    )

    ShowSnackBar(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        message = apiResponseMessage,
        condition = otpVerifiedState,
        onDismiss = {
            loginViewModel.resetOtpVerifiedState()
            loginViewModel.showEmailVerificationDialog()
        }
    )

    ShowSnackBar(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        message = apiResponseMessage,
        condition = emailVerifiedState,
        onDismiss = { loginViewModel.resetEmailVerifiedState() }
    )

    ShowSnackBar(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        message = apiResponseMessage,
        condition = loginState,
        onDismiss = {
            navController.navigate("dashboard")
        }
    )

    if (loginViewModel.showEmailVerificationDialog.value) {
        EmailVerificationDialog(
            fullName = fullName,
            onFullNameChange = { fullName = it },
            email = email,
            onEmailChange = { email = it },
            onConfirmClick = {
                mobileOtpResponseData?.let {
                    loginViewModel.verifyEmailAfterMobileVerification(it._id, email, fullName)
                }
            },
            onDismiss = { loginViewModel.hideEmailVerificationDialog() }
        )
    }
}

@Composable
fun RegisterSection(
    mobileNumber: String,
    isMobileNumberValid: Boolean,
    onMobileNumberChange: (String) -> Unit,
    email: String,
    isEmailValid: Boolean,
    onEmailChange: (String) -> Unit,
    onSendOtpClick: () -> Unit,
    onVerifyEmailClick: () -> Unit
) {
    Column {
        Text(
            text = "Please enter your mobile number to register",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = "+91",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.width(64.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = mobileNumber,
                onValueChange = onMobileNumberChange,
                placeholder = { Text(text = "Enter your 10 digit mobile number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = !isMobileNumberValid,
                modifier = Modifier.weight(1f)
            )
        }

        if (!isMobileNumberValid) {
            Text(
                text = "Please enter a valid 10 digit mobile number.",
                color = Color.Red,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Button(
            onClick = onSendOtpClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Get OTP")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "or register with email ID",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text(text = "Enter your email id") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = !isEmailValid,
            modifier = Modifier.fillMaxWidth()
        )

        if (!isEmailValid) {
            Text(
                text = "Please enter a valid email address.",
                color = Color.Red,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Button(
            onClick = onVerifyEmailClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Verify Email")
        }
    }
}

@Composable
fun VerifyOtpSection(
    mobileNumber: String,
    otp: String,
    onOtpChange: (String) -> Unit,
    onVerifyOtpClick: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Please enter the OTP sent to your mobile number",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = otp,
            onValueChange = onOtpChange,
            placeholder = { Text(text = "Enter OTP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onVerifyOtpClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Verify OTP")
        }
    }
}

@Composable
fun LoginSection(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column {
        Text(
            text = "Enter your email or mobile number",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text(text = "Email or mobile number") },
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Password",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { /* Handle forgot password */ }) {
            Text(text = "Forgot Password?", color = Color.Blue)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Google Sign-In */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Continue with Google", color = Color.Black)
        }
    }
}

@Composable
fun ShowSnackBar(
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    message: String,
    condition: Boolean,
    onDismiss: (() -> Unit)? = null
) {
    if (condition) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message)
                onDismiss?.invoke()
            }
        }
    }
}

@Composable
fun EmailVerificationDialog(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Sign-up incomplete!") },
        text = {
            Column {
                Text(text = "Please enter your Name & email address to proceed further.")
                TextField(
                    value = fullName,
                    onValueChange = onFullNameChange,
                    placeholder = { Text(text = "Full Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                TextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = { Text(text = "email id") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirmClick()
                onDismiss() // Close the dialog
            }) {
                Text("Submit")
            }
        }
    )
}
