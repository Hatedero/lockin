package com.retardero.lockin.details.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.retardero.lockin.app.widgets.LockWidget
import com.retardero.lockin.app.widgets.TopBar
import com.retardero.lockin.details.domain.DetailsViewModel
import com.retardero.lockin.details.presentation.widgets.History
import com.retardero.lockin.details.presentation.widgets.LockModificationMenu
import com.retardero.lockin.lockList.presentation.widget.LockCreationMenu
import com.retardero.lockin.login.domain.LoginViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Destination()
@Composable
fun DetailsScreen(navigator: DestinationsNavigator, viewModel: DetailsViewModel = viewModel(), lockId : String) {
    val lock by viewModel.lock.collectAsState()
    val logs by viewModel.logs.collectAsState()

    var showModifyLockDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchLock(lockId)
    }

    Scaffold (
        topBar = {
            TopBar(
                popBack = { navigator.popBackStack() },
                settings = { showModifyLockDialog=true }
            )
        },

    ) {
        Column (
            modifier = Modifier.fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(38.dp))
            LockWidget(
                lock = lock,
                onClick = {
                    coroutineScope.launch {
                        viewModel.changeLockState()
                        viewModel.fetchLock(lockId)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            History(logs, lock)
        }
    }
    if (showModifyLockDialog) {
        LockModificationMenu(
            onDismiss = { showModifyLockDialog = false },
            onSave = { newLock ->
                coroutineScope.launch {
                    viewModel.modifyLockInfo(newLock)
                    viewModel.fetchLock(lockId)
                }
            },
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteLock(lockId)
                }
                navigator.popBackStack()
            },
            lock = lock
        )
    }
}
