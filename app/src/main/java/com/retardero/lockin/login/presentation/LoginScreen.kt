package com.retardero.lockin.login.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.retardero.lockin.login.domain.LoginViewModel

@Destination(start = true)
@Composable
fun LoginScreen(navigator: DestinationsNavigator, viewModel: LoginViewModel = viewModel()) {

}
