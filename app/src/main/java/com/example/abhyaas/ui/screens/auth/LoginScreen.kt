package com.example.abhyaas.ui.screens.auth

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B111A))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (uiState.isOtpSent) "Enter OTP" else "Login",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            if (!uiState.isOtpSent) {
                var phoneNumber by remember { mutableStateOf("") }
                var localError by remember { mutableStateOf<String?>(null) }

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { 
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            phoneNumber = it
                            localError = null
                        }
                    },
                    label = { Text("Phone Number", color = Color.Gray) },
                    prefix = { Text("+91 ", color = Color.White) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2563EB),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (localError != null) {
                    Text(text = localError!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp).align(Alignment.Start))
                }
                
                if (uiState.error != null) {
                    Text(text = uiState.error!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp).align(Alignment.Start))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (phoneNumber.length == 10) {
                            viewModel.sendOtp(
                                phoneNumber = phoneNumber,
                                activity = context as Activity,
                                onCodeSent = {},
                                onError = { localError = it }
                            )
                        } else {
                            localError = "Please enter a valid 10-digit phone number"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Send OTP", color = Color.White, fontSize = 16.sp)
                    }
                }
            } else {
                var otp by remember { mutableStateOf("") }
                var localError by remember { mutableStateOf<String?>(null) }
                var timeLeft by remember { mutableStateOf(60) }

                LaunchedEffect(timeLeft) {
                    if (timeLeft > 0) {
                        delay(1000L)
                        timeLeft--
                    }
                }
                
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    for (i in 0 until 6) {
                        OutlinedTextField(
                            value = if (i < otp.length) otp[i].toString() else "",
                            onValueChange = { 
                                if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                    if (it.isNotEmpty() && otp.length < 6) {
                                        otp += it
                                    } else if (it.isEmpty() && otp.isNotEmpty()) {
                                        otp = otp.dropLast(1)
                                    }
                                    localError = null
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2563EB),
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .aspectRatio(1f),
                            singleLine = true
                        )
                    }
                }
                
                // Fallback direct entry for OTP if above doesn't work perfectly
                OutlinedTextField(
                    value = otp,
                    onValueChange = { 
                        if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                            otp = it
                            localError = null
                        }
                    },
                    label = { Text("Or enter full 6-digit OTP here", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2563EB),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (localError != null) {
                    Text(text = localError!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp).align(Alignment.Start))
                }
                
                if (uiState.error != null) {
                    Text(text = uiState.error!!, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp).align(Alignment.Start))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (otp.length == 6) {
                            viewModel.verifyOtp(
                                otp = otp,
                                onSuccess = onLoginSuccess,
                                onError = { localError = it }
                            )
                        } else {
                            localError = "Please enter a valid 6-digit OTP"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Verify", color = Color.White, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                TextButton(
                    onClick = {
                        if (timeLeft == 0) {
                            timeLeft = 60
                            viewModel.sendOtp(
                                phoneNumber = uiState.phoneNumber,
                                activity = context as Activity,
                                onCodeSent = {},
                                onError = { localError = it }
                            )
                        }
                    },
                    enabled = timeLeft == 0 && !uiState.isLoading
                ) {
                    Text(
                        text = if (timeLeft > 0) "Resend OTP in ${timeLeft}s" else "Resend OTP",
                        color = if (timeLeft > 0) Color.Gray else Color(0xFF2563EB)
                    )
                }
            }
        }
    }
}
