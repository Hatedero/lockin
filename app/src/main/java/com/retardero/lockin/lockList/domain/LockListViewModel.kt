package com.retardero.lockin.lockList.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retardero.lockin.app.data.Lock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LockListViewModel:ViewModel() {
    private val locksState:MutableStateFlow<List<Lock>> = MutableStateFlow(emptyList())
    val locks: StateFlow<List<Lock>> = locksState.asStateFlow()
    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchLocks() {
        locksState.value = listOf<Lock>(Lock("Lock 1", "IN 1.05", true),
            Lock("Lock 2", "IN 1.06", false),
            Lock("Lock 3", "2.02", true))
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
}