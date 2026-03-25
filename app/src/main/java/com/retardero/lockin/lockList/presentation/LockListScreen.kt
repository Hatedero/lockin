package com.retardero.lockin.lockList.presentation

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.retardero.lockin.app.data.Lock
import com.retardero.lockin.app.widget.LockWidget
import com.retardero.lockin.lockList.domain.LockListViewModel
import com.retardero.lockin.login.domain.LoginViewModel
import com.retardero.lockin.ui.theme.LockinTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Destination(start = true)
@Composable
fun LockListScreen(navigator: DestinationsNavigator, viewModel: LockListViewModel = viewModel()) {
    val locks by viewModel.locks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchLocks()
        println("GETTING LOCKS")
    }

    Scaffold (
        modifier = Modifier.padding(8.dp)
    ) {
        LazyColumn (
            modifier = Modifier.clip(RoundedCornerShape(10))
                .padding(8.dp)
                .shadow(8.dp, RoundedCornerShape(10.dp))
                .background(Color(0xFFD9D9D9))
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            locks.forEach { lock ->
                item {
                    LockWidget(lock)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
