package com.utb.inventoryapp.ui.admin.barang

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.*
import com.utb.inventoryapp.data.repository.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class RefOption(val id: Long, val nama: String) {
    override fun toString(): String = nama
}

class BarangViewModel(private val repository: InventoryRepository) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    val barangList: LiveData<List<Barang>> = searchQuery.flatMapLatest { q ->
        if (q.isBlank()) repository.getAllBarang() else repository.searchBarang(q)
    }.asLiveData()

    val kategoriOptions: LiveData<List<RefOption>> =
        repository.getAllKategori().map { list -> list.map { RefOption(it.id, it.nama) } }.asLiveData()
    val jenisOptions: LiveData<List<RefOption>> =
        repository.getAllJenisBarang().map { list -> list.map { RefOption(it.id, it.nama) } }.asLiveData()
    val satuanOptions: LiveData<List<RefOption>> =
        repository.getAllSatuan().map { list -> list.map { RefOption(it.id, it.nama) } }.asLiveData()
    val supplierOptions: LiveData<List<RefOption>> =
        repository.getAllSupplier().map { list -> list.map { RefOption(it.id, it.nama) } }.asLiveData()
    val lokasiOptions: LiveData<List<RefOption>> =
        repository.getAllLokasi().map { list -> list.map { RefOption(it.id, it.nama) } }.asLiveData()

    fun setSearchQuery(q: String) {
        searchQuery.value = q
    }

    fun save(
        id: Long, kode: String, nama: String, kategoriId: Long, jenisBarangId: Long,
        satuanId: Long, supplierId: Long, lokasiId: Long, stok: Int, kondisi: String, deskripsi: String,
        onDone: () -> Unit
    ) {
        viewModelScope.launch {
            repository.saveBarang(
                Barang(
                    id = id, kode = kode, nama = nama, kategoriId = kategoriId, jenisBarangId = jenisBarangId,
                    satuanId = satuanId, supplierId = supplierId, lokasiId = lokasiId, stok = stok,
                    kondisi = kondisi, deskripsi = deskripsi
                )
            )
            onDone()
        }
    }

    fun delete(item: Barang) {
        viewModelScope.launch { repository.deleteBarang(item) }
    }
}
