package com.retardero.lockin.login.domain

import android.content.Context
import android.credentials.GetCredentialRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class LoginViewModel: ViewModel() {
    suspend fun signInWithGoogle(context: Context): FirebaseUser? {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("1064628142464-sv1h0vnlgtpgocbgcnl2eqq5rbi4vsvo.apps.googleusercontent.com")
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context = context, request = request)
            val credential = result.credential

            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)

                val authResult = Firebase.auth.signInWithCredential(firebaseCredential).await()
                authResult.user
            } else null
        } catch (e: Exception) {
            null
        }
    }

    fun saveUserToFirestore(user: FirebaseUser) {
        val db = Firebase.firestore
        val userMap = hashMapOf(
            "uid" to user.uid,
            "name" to user.displayName,
            "email" to user.email,
            "lastLogin" to com.google.firebase.Timestamp.now()
        )

        db.collection("users").document(user.uid)
            .set(userMap)
            .addOnSuccessListener { println("User profile saved!") }
    }
}