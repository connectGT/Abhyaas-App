package com.example.abhyaas.ui.screens.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.abhyaas.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Email", "Google")

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
                text = "Login",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            PrimaryTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> EmailTab(uiState, viewModel, onLoginSuccess)
                1 -> GoogleTab(uiState, viewModel, onLoginSuccess, context)
            }
        }
    }
}

@Composable
fun EmailTab(
    uiState: com.example.abhyaas.ui.viewmodel.AuthUiState,
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    var isRegisterMode by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    if (isRegisterMode) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it; localError = null },
            label = { Text("Name", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2563EB), unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    OutlinedTextField(
        value = email,
        onValueChange = { email = it; localError = null },
        label = { Text("Email", color = Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF2563EB), unfocusedBorderColor = Color.Gray,
            focusedTextColor = Color.White, unfocusedTextColor = Color.White
        )
    )
    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
        value = password,
        onValueChange = { password = it; localError = null },
        label = { Text("Password", color = Color.Gray) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF2563EB), unfocusedBorderColor = Color.Gray,
            focusedTextColor = Color.White, unfocusedTextColor = Color.White
        )
    )
    Spacer(modifier = Modifier.height(8.dp))

    if (isRegisterMode) {
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it; localError = null },
            label = { Text("Confirm Password", color = Color.Gray) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2563EB), unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    localError?.let {
        Text(text = it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp).fillMaxWidth())
    }
    uiState.error?.let {
        Text(text = it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp).fillMaxWidth())
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = {
            if (email.isBlank() || password.isBlank()) {
                localError = "Fields cannot be empty"
                return@Button
            }
            if (isRegisterMode) {
                if (password != confirmPassword) {
                    localError = "Passwords do not match"
                    return@Button
                }
                viewModel.signUpWithEmail(email, password, onSuccess = {
                    // Ideally we'd save the name to Firestore here via a side-effect, 
                    // but we focus on auth success for now
                    onLoginSuccess()
                }, onError = { localError = it })
            } else {
                viewModel.signInWithEmail(email, password, onSuccess = onLoginSuccess, onError = { localError = it })
            }
        },
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
        enabled = !uiState.isLoading
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
        } else {
            Text(if (isRegisterMode) "Create Account" else "Sign In", color = Color.White, fontSize = 16.sp)
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    TextButton(onClick = { isRegisterMode = !isRegisterMode; localError = null }) {
        Text(if (isRegisterMode) "Already have an account? Sign In" else "New user? Sign Up", color = Color(0xFF2563EB))
    }
}

@Composable
fun GoogleTab(
    uiState: com.example.abhyaas.ui.viewmodel.AuthUiState,
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    context: android.content.Context
) {
    var localError by remember { mutableStateOf<String?>(null) }
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.handleGoogleSignInResult(
            data = result.data,
            onSuccess = onLoginSuccess,
            onError = { localError = it }
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        localError?.let {
            Text(text = it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(bottom = 16.dp))
        }
        uiState.error?.let {
            Text(text = it, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(bottom = 16.dp))
        }

        Button(
            onClick = {
                val intent = viewModel.getGoogleSignInIntent(context)
                launcher.launch(intent)
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
            } else {
                Text("Continue with Google", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
