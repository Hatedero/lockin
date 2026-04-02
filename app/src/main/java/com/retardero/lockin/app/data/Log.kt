package com.retardero.lockin.app.data

import android.annotation.SuppressLint
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.coroutines.coroutineContext

data class Log(
    val accountId: String = "",
    val action: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val lockId: String
) {
    override fun toString(): String {
        return "User - ${getDateTime(timestamp)} : ${action}"
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
