package com.retardero.lockin.app.data

import android.annotation.SuppressLint
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

data class Log(
    val doer: String,
    val action: String,
    val timestamp: Timestamp,
    val lockId: Int
) {
    override fun toString(): String {
        return "${doer} - ${getDateTime(timestamp)} : ${action}"
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: Timestamp): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.seconds * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}
