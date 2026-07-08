package com.utb.inventoryapp

import android.app.Application
import com.utb.inventoryapp.data.local.AppDatabase
import com.utb.inventoryapp.data.repository.InventoryRepository
import com.utb.inventoryapp.session.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Dispatchers

class InventoryApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val database: AppDatabase by lazy { AppDatabase.getInstance(this, applicationScope) }

    val repository: InventoryRepository by lazy { InventoryRepository(database) }

    val sessionManager: SessionManager by lazy { SessionManager(this) }
}
