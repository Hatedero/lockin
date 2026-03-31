package com.retardero.lockin.details.domain

import androidx.lifecycle.ViewModel
import com.retardero.lockin.app.data.Lock
import com.retardero.lockin.app.data.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel: ViewModel() {
    private val lockState: MutableStateFlow<Lock> = MutableStateFlow(Lock(-1,"","",false))
    val lock: StateFlow<Lock> = lockState.asStateFlow()

    private val logsState: MutableStateFlow<List<Log>> = MutableStateFlow(emptyList())
    val logs: StateFlow<List<Log>> = logsState.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchLock(id : Int) {
        lockState.value = Lock(0,"Lock 1", "IN 1.05", true)
        logsState.value = listOf<Log>(
            Log("admin", "open", 0),
            Log("student", "close", 0),
            Log("student", "open", 0),
            Log("admin", "close", 0),
            Log("admin", "open", 0),
            Log("student", "close", 0),
            Log("student", "open", 0),
            Log("admin", "close", 0),
            Log("admin", "open", 0),
            Log("student", "close", 0),
            Log("student", "open", 0),
            Log("admin", "close", 0)
        )
    }

    fun changeLockState() {
        lockState.value = lockState.value.copy(status = !lockState.value.status)
        println(if (lockState.value.status) "OPEN LOCK" else "CLOSE LOCK")
    }
}