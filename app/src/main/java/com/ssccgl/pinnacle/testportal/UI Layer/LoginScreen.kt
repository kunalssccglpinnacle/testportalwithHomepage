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
                    fontWeight = FontWeight.Bold
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
                    if (!otpSentState) {
                        Text(text = "Please enter your mobile number to register", fontSize = 16.sp)

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
                                onValueChange = {
                                    mobileNumber = it
                                    isMobileNumberValid = it.length == 10 && it.all { char -> char.isDigit() }
                                },
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
                            onClick = {
                                if (isMobileNumberValid) {
                                    loginViewModel.sendOtp(mobileNumber)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text(text = "Get OTP")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(text = "or register with email ID", fontSize = 16.sp)

                        TextField(
                            value = email,
                            onValueChange = {
                                email = it
                                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                            },
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
                            onClick = {
                                if (isEmailValid) {
                                    loginViewModel.sendEmailVerification(email)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text(text = "Verify Email")
                        }

                        Button(
                            onClick = { /* Handle email registration */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text(text = "Submit")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { /* Handle Google Sign-In */ },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Continue with Google", color = Color.Black)
                        }
                    } else {
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(text = "Please enter the OTP sent to your mobile number", fontSize = 16.sp)

                        TextField(
                            value = otp,
                            onValueChange = { otp = it },
                            placeholder = { Text(text = "Enter OTP") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                loginViewModel.verifyOtp(mobileNumber, otp)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            Text(text = "Verify OTP")
                        }
                    }
                } else {
                    Text(text = "Enter your email or mobile number", fontSize = 16.sp)

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(text = "Email or mobile number") },
                        keyboardOptions = KeyboardOptions.Default,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Password", fontSize = 16.sp)

                    TextField(
                        value = password,
                        onValueChange = { password = it },
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
                        onClick = {
                            loginViewModel.login(email, password)
                        },
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

                // Display the API response message as an error on the screen
                if (apiResponseMessage.isNotEmpty() && (!otpVerifiedState || !otpSentState || !emailVerifiedState)) {
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

    // Show Snackbar when OTP is sent
    if (otpSentState && !otpVerifiedState) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(apiResponseMessage)
            }
        }
    }

    // Show Snackbar when OTP is verified
    if (otpVerifiedState) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(apiResponseMessage)
                loginViewModel.resetOtpVerifiedState()
                // Show the dialog for email verification
                loginViewModel.showEmailVerificationDialog()
            }
        }
    }

    // Show Snackbar when Email is verified
    if (emailVerifiedState) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(apiResponseMessage)
                loginViewModel.resetEmailVerifiedState()
            }
        }
    }

    // Show Snackbar when Login is successful
    if (loginState) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(apiResponseMessage)
                // Navigate to the next screen after successful login
                navController.navigate("dashboard")
            }
        }
    }

    // Show Toast for debugging
    LaunchedEffect(apiResponseMessage) {
        if (apiResponseMessage.isNotEmpty()) {
            Toast.makeText(context, apiResponseMessage, Toast.LENGTH_LONG).show()
        }
    }

    // Email verification dialog
    if (loginViewModel.showEmailVerificationDialog.value) {
        AlertDialog(
            onDismissRequest = { loginViewModel.hideEmailVerificationDialog() },
            title = { Text(text = "Sign-up incomplete!") },
            text = {
                Column {
                    Text(text = "Please enter your Name & email address to proceed further.")
                    TextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        placeholder = { Text(text = "Full Name") },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    )
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(text = "email id") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        mobileOtpResponseData?.let {
                            loginViewModel.verifyEmailAfterMobileVerification(
                                it._id,
                                email,
                                fullName
                            )
                        }
                    }
                ) {
                    Text("Submit")
                }
            }
        )
    }
}
