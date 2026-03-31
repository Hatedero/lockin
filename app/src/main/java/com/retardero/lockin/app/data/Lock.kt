package com.retardero.lockin.app.data

data class Lock(
    val id: Int,
    val name: String,
    val location: String,
    val status: Boolean,
    val password: String
)
