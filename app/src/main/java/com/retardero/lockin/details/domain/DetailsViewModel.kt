package com.retardero.lockin.details.domain

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.retardero.lockin.app.data.Lock
import com.retardero.lockin.app.data.Log
import com.retardero.lockin.app.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class DetailsViewModel: ViewModel() {
    private val lockState: MutableStateFlow<Lock> = MutableStateFlow(Lock("","","",false, ""))
    val lock: StateFlow<Lock> = lockState.asStateFlow()

    private val logsState: MutableStateFlow<List<Log>> = MutableStateFlow(emptyList())
    val logs: StateFlow<List<Log>> = logsState.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val db = Firebase.firestore
    private val locksCollection = db.collection("locks")

    suspend fun fetchLock(lockId : String) {
        try {
            val snapshot = locksCollection.document(lockId).get().await()

            if (snapshot.exists()) {
                val lock = snapshot.toObject(Lock::class.java)
                if (lock != null) {
                    lockState.value = lock
                    println("Fetched lock: ${lock.name}")
                }
            } else {
                println("No such document found with ID: $lockId")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun modifyLockInfo(lock: Lock) : Boolean {
        return try {
            locksCollection.document(lock.id).set(lock).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun deleteLock(lockid: String) : Boolean {
        return try {
            locksCollection.document(lockid).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun getAccountName(log : Log): String {
        val db = Firebase.firestore
        val usersCollection = db.collection("users")

        var name = ""
        try {
            val snapshot = usersCollection.document(log.accountId).get().await()
            name = snapshot.toObject(User::class.java)?.name ?: ""

        } catch (e: Exception) {
            return ""
        }
        return name
    }

    fun changeLockState() {
        lockState.value = lockState.value.copy(status = !lockState.value.status)
        println(if (lockState.value.status) "OPEN LOCK" else "CLOSE LOCK")
    }
}