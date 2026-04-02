package com.retardero.lockin.login.domain

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import com.retardero.lockin.app.data.User
import com.retardero.lockin.app.repositories.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val repository: AuthRepository): ViewModel() {
    var uiState by mutableStateOf<AuthState>(AuthState.Idle)
        private set
    
    var user by mutableStateOf<User?>(
        value = null
    )

    val db = Firebase.firestore
    private val usersCollection = db.collection("users")

    fun onSignInClick(activity: Activity) {
        uiState = AuthState.Loading
        viewModelScope.launch {
            val result = repository.signInWithGoogle(activity)

            if (result.isSuccess) {
                user = getUserProfile(FirebaseAuth.getInstance().currentUser?.uid ?: "-1")
                if(user != null)
                    uiState = AuthState.Success(result.getOrNull()?.user)
                else
                    saveUser(User(FirebaseAuth.getInstance().currentUser?.uid ?: "-1", "Michelin"))
            } else {
                val error = result.exceptionOrNull()
                error?.printStackTrace()

                uiState = AuthState.Error(error?.message ?: "Unknown Error")
            }
        }
    }

    suspend fun getUserProfile(uid: String): User? {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveUser(user: User): Boolean {
        return try {
            usersCollection.document(user.uid).set(user).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val message: String) : AuthState()
}