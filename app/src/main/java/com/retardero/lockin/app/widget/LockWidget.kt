package com.retardero.lockin.app.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.retardero.lockin.app.data.Lock
import com.retardero.lockin.lockList.domain.LockListViewModel
import java.nio.file.WatchEvent

@Composable
fun LockWidget(lock: Lock) {
    Row (
        modifier = Modifier.clip(RoundedCornerShape(10))
            .fillMaxWidth()
            .background(Color(0xFF0000BF)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text(lock.name)
            Text(lock.location)
        }
        Text(lock.status.toString())
    }
}