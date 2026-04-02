package com.retardero.lockin.details.presentation.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.retardero.lockin.app.data.Lock

@Composable
fun LockModificationMenu(
    lock: Lock,
    onDismiss: () -> Unit,
    onSave: (Lock) -> Unit,
    onDelete: (Lock) -> Unit
) {
    var name by remember { mutableStateOf(lock.name) }
    var location by remember { mutableStateOf(lock.location) }
    var password by remember { mutableStateOf(lock.password) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Modify Lock") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Lock Name") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    onClick = {
                        onDelete(lock)
                        onDismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text("Delete")
                }

                Button(
                    onClick = {
                        val updatedLock = lock.copy(
                            name = name,
                            location = location,
                            password = password
                        )
                        onSave(updatedLock)
                        onDismiss()
                    },
                    enabled = name.isNotBlank()
                ) {
                    Text("Save")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}