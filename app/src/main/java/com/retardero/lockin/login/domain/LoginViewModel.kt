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

class LoginViewModel(private val repository: AuthRepository): ViewModel() {
    var uiState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    val db = Firebase.firestore

    fun onSignInClick(activity: Activity) {
        uiState = AuthState.Loading
        viewModelScope.launch {
            val result = repository.signInWithGoogle(activity)

            if (result.isSuccess) {
                println("YAHOU")
                if(getUser(FirebaseAuth.getInstance().currentUser?.uid ?: "-1"))
                    uiState = AuthState.Success(result.getOrNull()?.user)
                else
                    createUser(User(FirebaseAuth.getInstance().currentUser?.uid ?: "-1", "Michelin"))
            } else {
                val error = result.exceptionOrNull()
                error?.printStackTrace()

                uiState = AuthState.Error(error?.message ?: "Unknown Error")
            }
        }
    }

    fun getUser(UID : String) : Boolean {
        if(UID.equals("-1"))
            return false
        println("GETTING USER FROM FIREBASE")
        db.collection("user")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("Firestore", "${document.id} => ${document.data}")
                }
            }
        return false
    }

    fun createUser(user : User) {
        if(user.UID.equals("-1"))
            return
        println("CREATING USER IN FIREBASE")
        db.collection("user")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Firestore", "Document added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding document", e)
            }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: FirebaseUser?) : AuthState()
    data class Error(val message: String) : AuthState()
}