package com.retardero.lockin.details.domain

import androidx.lifecycle.ViewModel
import com.retardero.lockin.app.data.Lock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel: ViewModel() {
    private val lockState: MutableStateFlow<Lock> = MutableStateFlow(Lock("","",false))
    val lock: StateFlow<Lock> = lockState.asStateFlow()
    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchLock() {
        lockState.value = Lock("Lock 1", "IN 1.05", true)
    }
}