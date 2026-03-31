package com.retardero.lockin.lockList.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.retardero.lockin.app.data.Lock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LockListViewModel:ViewModel() {
    private val locksState:MutableStateFlow<List<Lock>> = MutableStateFlow(emptyList())
    val locks: StateFlow<List<Lock>> = locksState.asStateFlow()
    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val db = Firebase.firestore

    val random = Random(10)

    fun fetchLocks() {
        locksState.value = listOf<Lock>(Lock(0,"Lock 1", "IN 1.05", true, ""),
            Lock(1,"Lock 2", "IN 1.06", false, ""),
            Lock(2,"Lock 3", "2.02", true, ""))
        /*viewModelScope.launch {
            val response = CardRacterRepository.getAllMultiCategoryCards()

            when(response) {
                is Resource.Success -> {
                    println("SUCESS")
                    cardsState.value =  response.data
                }
                is Resource.Error -> {
                    println("ERROR")
                    _error.value = response.error
                }
            }
        }*/
    }

    fun addLock() {
        var prev = locksState.value.toMutableList()
        prev.add(Lock(locksState.value.size, ("Lock" + random.nextInt().toString()), "No place defined", random.nextBoolean(), ""))
        locksState.value = prev
    }
}