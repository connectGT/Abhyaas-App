package com.example.abhyaas.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isOtpSent: Boolean = false,
    val phoneNumber: String = ""
)

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var verificationId: String? = null

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        onCodeSent: () -> Unit,
        onError: (String) -> Unit
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, phoneNumber = phoneNumber)
        val fullPhone = "+91$phoneNumber"
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithCredential(credential, onError = onError)
            }
            override fun onVerificationFailed(e: com.google.firebase.FirebaseException) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
                onError(e.message ?: "Verification failed")
            }
            override fun onCodeSent(vId: String, token: PhoneAuthProvider.ForceResendingToken) {
                verificationId = vId
                _uiState.value = _uiState.value.copy(isLoading = false, isOtpSent = true)
                onCodeSent()
            }
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(fullPhone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(
        otp: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val vId = verificationId ?: run { onError("Session expired. Please resend OTP."); return }
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        val credential = PhoneAuthProvider.getCredential(vId, otp)
        signInWithCredential(credential, onSuccess, onError)
    }

    private fun signInWithCredential(
        credential: PhoneAuthCredential,
        onSuccess: (() -> Unit)? = null,
        onError: (String) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener { _uiState.value = _uiState.value.copy(isLoading = false); onSuccess?.invoke() }
            .addOnFailureListener { _uiState.value = _uiState.value.copy(isLoading = false, error = it.message); onError(it.message ?: "Sign-in failed") }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null
    fun signOut() = auth.signOut()
}
