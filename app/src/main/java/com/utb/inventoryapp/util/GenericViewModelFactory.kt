package com.utb.inventoryapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GenericViewModelFactory<T : ViewModel>(private val creator: () -> T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        return creator() as VM
    }
}
