package com.retardero.lockin.details.presentation.widgets

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.retardero.lockin.app.data.Lock
import com.retardero.lockin.app.data.Log

@Composable
fun History(logs : List<Log>, lock: Lock) {
    Column (
        modifier = Modifier.clip(RoundedCornerShape(10.dp))
            .fillMaxSize()
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        Text("History for : ${lock.name}", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp))
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn (
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            if (logs.size > 1)
                item {
                    Text("No logs to date.")
                }
            else
            logs.forEach { log ->
                if(lock.id.equals(log.lockId))
                    item {
                        Row (
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .padding(2.dp)
                        ) {
                            Text(log.toString(), color = Color.DarkGray)
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    }
            }
        }
    }
}