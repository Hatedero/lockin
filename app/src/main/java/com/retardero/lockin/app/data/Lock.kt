package com.retardero.lockin.app.data

data class Lock(
    val id: String = "",
    val name: String = "",
    val location: String = "",
    val status: Boolean = false,
    val password: String = ""
)
