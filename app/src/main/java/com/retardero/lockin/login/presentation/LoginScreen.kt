package com.retardero.lockin.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.retardero.lockin.login.domain.LoginViewModel
import kotlinx.coroutines.launch

@Destination(start = true)
@Composable
fun LoginScreen(navigator: DestinationsNavigator, viewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user == null) {
            Button(onClick = {
                scope.launch {
                    val signedInUser = viewModel.signInWithGoogle(context)
                    if (signedInUser != null) {
                        user = signedInUser
                        viewModel.saveUserToFirestore(signedInUser)
                    }
                }
            }) {
                Text("Sign in with Google")
            }
        } else {
            Text("Welcome, ${user?.displayName}")
            Button(onClick = {
                Firebase.auth.signOut()
                user = null
            }) {
                Text("Logout")
            }
        }
    }
}
