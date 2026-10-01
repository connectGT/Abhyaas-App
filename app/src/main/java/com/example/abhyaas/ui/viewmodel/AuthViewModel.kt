package com.example.abhyaas.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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
    val phoneNumber: String = "",
    val currentUser: FirebaseUser? = null
)

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var verificationId: String? = null

    private val _uiState = MutableStateFlow(AuthUiState(currentUser = auth.currentUser))
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _uiState.value = _uiState.value.copy(currentUser = firebaseAuth.currentUser)
        }
    }

    // ==========================================
    // Email / Password Auth
    // ==========================================
    fun signUpWithEmail(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _uiState.value = _uiState.value.copy(isLoading = false)
                onSuccess()
            }
            .addOnFailureListener {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
                onError(it.message ?: "Sign-up failed")
            }
    }

    fun signInWithEmail(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _uiState.value = _uiState.value.copy(isLoading = false)
                onSuccess()
            }
            .addOnFailureListener {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
                onError(it.message ?: "Sign-in failed")
            }
    }

    // ==========================================
    // Google Sign-In Auth
    // ==========================================
    fun getGoogleSignInIntent(context: Context): Intent {
        // Web Client ID from google-services.json (client_type: 3)
        val serverClientId = "334361444733-80luu51rq4m0lku0chcfg78q230je8h2.apps.googleusercontent.com"
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestEmail()
            .build()
        val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
        return googleSignInClient.signInIntent
    }

    fun handleGoogleSignInResult(data: Intent?, onSuccess: () -> Unit, onError: (String) -> Unit) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(com.google.android.gms.common.api.ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        onSuccess()
                    }
                    .addOnFailureListener {
                        _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
                        onError(it.message ?: "Google Sign-In failed")
                    }
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Google ID token is null")
                onError("Google ID token is null")
            }
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            onError(e.message ?: "Google Sign-In failed")
        }
    }

    // ==========================================
    // Phone OTP Auth (Existing)
    // ==========================================
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
            .addOnSuccessListener { 
                _uiState.value = _uiState.value.copy(isLoading = false)
                onSuccess?.invoke() 
            }
            .addOnFailureListener { 
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
                onError(it.message ?: "Sign-in failed") 
            }
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null
    fun signOut() = auth.signOut()
    fun getCurrentUser() = auth.currentUser
}
