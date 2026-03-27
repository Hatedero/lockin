package com.retardero.lockin.app.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun TopBar(popBack : () -> Unit, settings : () -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = popBack
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close details"
            )
        }

        IconButton(
            onClick = settings
        ) {
            Icon(
                Icons.Default.Settings,
                contentDescription = "Open settings"
            )
        }
    }
}