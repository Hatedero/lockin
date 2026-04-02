package com.retardero.lockin.lockList.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.retardero.lockin.app.data.Lock
import com.retardero.lockin.app.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

class LockListViewModel:ViewModel() {
    private val locksState:MutableStateFlow<List<Lock>> = MutableStateFlow(emptyList())
    val locks: StateFlow<List<Lock>> = locksState.asStateFlow()
    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val db = Firebase.firestore
    private val locksCollection = db.collection("locks")

    suspend fun fetchAllLocks() {
        try {
            val snapshot = locksCollection.get().await()

            locksState.value = emptyList()

            snapshot.documents.mapNotNull { doc ->
                println(doc.data)
                locksState.value = locksState.value.plus(doc.toObject(Lock::class.java)!!)
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    suspend fun saveLock(lock: Lock) : Boolean {
        return try {
            locksCollection.document(lock.id).set(lock).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}