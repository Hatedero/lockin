package com.retardero.lockin.details.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.retardero.lockin.login.domain.LoginViewModel

@Destination()
@Composable
fun DetailsScreen(navigator: DestinationsNavigator, viewModel: LoginViewModel = viewModel()) {

}
