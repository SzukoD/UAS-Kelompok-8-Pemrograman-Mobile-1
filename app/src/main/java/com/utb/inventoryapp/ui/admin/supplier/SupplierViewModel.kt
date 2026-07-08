package com.utb.inventoryapp.ui.admin.supplier

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.Supplier
import com.utb.inventoryapp.data.repository.InventoryRepository
import kotlinx.coroutines.launch

class SupplierViewModel(private val repository: InventoryRepository) : ViewModel() {
    val items: LiveData<List<Supplier>> = repository.getAllSupplier().asLiveData()

    fun save(id: Long, nama: String, kontak: String, alamat: String) {
        viewModelScope.launch {
            repository.saveSupplier(Supplier(id = id, nama = nama, kontak = kontak, alamat = alamat))
        }
    }

    fun delete(item: Supplier) {
        viewModelScope.launch { repository.deleteSupplier(item) }
    }
}
