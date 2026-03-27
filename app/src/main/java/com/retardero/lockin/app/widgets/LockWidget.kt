package com.retardero.lockin.app.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.retardero.lockin.app.data.Lock

@Composable
fun LockWidget(lock: Lock) {
    Row (
        modifier = Modifier.clip(RoundedCornerShape(10))
            .fillMaxWidth()
            .height(120.dp)
            .background(brush = Brush.linearGradient(
                colors = listOf(Color(0xFF43DFCD), Color(0xFF2962DB)),
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f)
            )),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text(text = lock.name, style = TextStyle(fontSize = 30.sp))
            Text(lock.location)
        }
        Box(
            modifier = Modifier.clip(CircleShape)
                .fillMaxHeight(0.25f)
                .aspectRatio(1f, true)
                .background(if (lock.status) Color(0xFFFF0000) else Color(0xFF00FF00))
                .border(width = 1.dp, color = Color(0xFFFFFFFF), shape = CircleShape)
        )
    }
}