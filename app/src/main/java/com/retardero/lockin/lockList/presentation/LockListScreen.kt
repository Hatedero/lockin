package com.retardero.lockin.lockList.presentation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.retardero.lockin.login.domain.LoginViewModel

@Destination()
@Composable
fun LockListScreen(navigator: DestinationsNavigator, viewModel: LoginViewModel = viewModel()) {

}
