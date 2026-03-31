package com.retardero.lockin.login.presentation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.retardero.lockin.login.domain.AuthState
import com.retardero.lockin.login.domain.LoginViewModel
import com.retardero.lockin.app.repositories.AuthRepository
import com.retardero.lockin.destinations.LockListScreenDestination
import com.retardero.lockin.lockList.presentation.LockListScreen

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@Destination(start = true)
@Composable
fun LoginScreen(navigator: DestinationsNavigator) {
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }

    val viewModel: LoginViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = AuthRepository(context = context)
                return LoginViewModel(repository) as T
            }
        }
    )

    val state = viewModel.uiState

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Error -> {
                Text("Error: ${state.message}", color = Color.Red)
                Button(onClick = { activity?.let { viewModel.onSignInClick(it) } }) {
                    Text("Try Again")
                }
            }
            is AuthState.Success -> {
                Text("Welcome, ${state.user?.displayName}")
                navigator.navigate(LockListScreenDestination)
            }
            else -> {
                Button(onClick = {
                    activity?.let {
                        viewModel.onSignInClick(it)
                    }
                }) {
                    Text("Sign in with Google")
                }
            }
        }
    }
}