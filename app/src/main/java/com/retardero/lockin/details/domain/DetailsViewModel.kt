package com.retardero.lockin.details.domain

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.Timestamp
import com.retardero.lockin.app.data.Lock
import com.retardero.lockin.app.data.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class DetailsViewModel(application: Application): AndroidViewModel(application) {
    private val SERVICE_UUID = UUID.fromString("19B10000-E8F2-537E-4F6C-D104768A1214")
    private val SWITCH_UUID = UUID.fromString("19B10001-E8F2-537E-4F6C-D104768A1214")

    private var bluetoothGatt: BluetoothGatt? = null
    private var switchCharacteristic: BluetoothGattCharacteristic? = null

    private val bluetoothManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter


    private val lockState: MutableStateFlow<Lock> = MutableStateFlow(Lock(-1,"","",false, ""))
    val lock: StateFlow<Lock> = lockState.asStateFlow()

    private val logsState: MutableStateFlow<List<Log>> = MutableStateFlow(emptyList())
    val logs: StateFlow<List<Log>> = logsState.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val gattCallback = object : BluetoothGattCallback() {

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            val service = gatt.getService(SERVICE_UUID)
            switchCharacteristic = service?.getCharacteristic(SWITCH_UUID)
        }
    }

    fun fetchLock(id : Int) {
        lockState.value = Lock(0,"Lock 1", "IN 1.05", true, "")
        logsState.value = listOf<Log>(
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
            Log("admin", "open", Timestamp.now(),0),
        )
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun changeLockState() {
        lockState.value = lockState.value.copy(status = !lockState.value.status)
        val characteristic = switchCharacteristic ?: return

        characteristic.value = byteArrayOf(if (lockState.value.status) 1 else 0)

        bluetoothGatt?.writeCharacteristic(characteristic)
        println(if (lockState.value.status) "OPEN LOCK" else "CLOSE LOCK")
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun connectToDevice() {
        val adapter = bluetoothAdapter ?: return

        val scanner = adapter.bluetoothLeScanner

        val scanCallback = object : ScanCallback() {
            @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                val device = result.device

                if (device.name == "LOCKIN") {
                    scanner.stopScan(this)

                    bluetoothGatt = device.connectGatt(
                        getApplication(),
                        false,
                        gattCallback
                    )
                }
            }
        }

        scanner.startScan(scanCallback)
    }
}