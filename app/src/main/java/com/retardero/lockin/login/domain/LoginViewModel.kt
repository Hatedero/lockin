package com.retardero.lockin.login.domain

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.retardero.lockin.app.repositories.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository): ViewModel() {
    var uiState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    fun onSignInClick(activity: Activity) {
        uiState = AuthState.Loading
        viewModelScope.launch {
            val result = repository.signInWithGoogle(activity)

            uiState = if (result.isSuccess) {
                AuthState.Success(result.getOrNull()?.user)
            } else {
                val error = result.exceptionOrNull()
                error?.printStackTrace()

                AuthState.Error(error?.message ?: "Unknown Error")
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val message: String) : AuthState()
}