package com.utb.inventoryapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.UserEntity
import com.utb.inventoryapp.data.repository.InventoryRepository
import kotlinx.coroutines.launch

sealed class LoginResult {
    object Loading : LoginResult()
    data class Success(val user: UserEntity) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

class LoginViewModel(private val repository: InventoryRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginResult.value = LoginResult.Error("Username dan password wajib diisi")
            return
        }
        _loginResult.value = LoginResult.Loading
        viewModelScope.launch {
            val user = repository.login(username.trim(), password)
            if (user != null) {
                repository.catatRiwayat(user.id, "Login ke aplikasi")
                _loginResult.value = LoginResult.Success(user)
            } else {
                _loginResult.value = LoginResult.Error("Username atau password salah")
            }
        }
    }
}
