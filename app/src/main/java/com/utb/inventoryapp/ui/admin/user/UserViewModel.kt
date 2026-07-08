package com.utb.inventoryapp.ui.admin.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.UserEntity
import com.utb.inventoryapp.data.repository.InventoryRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: InventoryRepository) : ViewModel() {
    val items: LiveData<List<UserEntity>> = repository.getAllUser().asLiveData()

    fun save(id: Long, username: String, password: String, namaLengkap: String, role: String) {
        viewModelScope.launch {
            repository.saveUser(UserEntity(id = id, username = username, password = password, namaLengkap = namaLengkap, role = role))
        }
    }

    fun delete(item: UserEntity) {
        viewModelScope.launch { repository.deleteUser(item) }
    }
}
