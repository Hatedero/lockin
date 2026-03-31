package com.retardero.lockin.app.data

data class Log(
    val doer: String,
    val action: String,
    val lockId: Int
) {
    override fun toString(): String {
        return "${doer} : ${action}"
    }
}
