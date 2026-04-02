package com.retardero.lockin.details.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import kotlinx.coroutines.launch
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition",
    "MissingPermission"
)
@Destination()
@Composable
fun DetailsScreen(navigator: DestinationsNavigator, viewModel: DetailsViewModel = viewModel(), lockId : String) {
    val lock by viewModel.lock.collectAsState()
    val logs by viewModel.logs.collectAsState()

    var showModifyLockDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }

        if (granted){
            viewModel.connectToDevice()
        } else {
            print("ACCEPT THE F*cking permissions")
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
            viewModel.fetchLock(lockId)
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
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
                    viewModel.changeLockState()
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
            lock = lock
        )
    }
}
