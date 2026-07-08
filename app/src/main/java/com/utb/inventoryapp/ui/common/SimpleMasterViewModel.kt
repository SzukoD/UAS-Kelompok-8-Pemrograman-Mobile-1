package com.utb.inventoryapp.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.JenisBarang
import com.utb.inventoryapp.data.local.entity.Kategori
import com.utb.inventoryapp.data.local.entity.Lokasi
import com.utb.inventoryapp.data.local.entity.Satuan
import com.utb.inventoryapp.data.repository.InventoryRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SimpleMasterViewModel(
    private val repository: InventoryRepository,
    private val type: MasterType
) : ViewModel() {

    val items: LiveData<List<SimpleMasterItem>> = when (type) {
        MasterType.KATEGORI -> repository.getAllKategori().map { list -> list.map { SimpleMasterItem(it.id, it.nama) } }
        MasterType.SATUAN -> repository.getAllSatuan().map { list -> list.map { SimpleMasterItem(it.id, it.nama) } }
        MasterType.JENIS_BARANG -> repository.getAllJenisBarang().map { list -> list.map { SimpleMasterItem(it.id, it.nama) } }
        MasterType.LOKASI -> repository.getAllLokasi().map { list -> list.map { SimpleMasterItem(it.id, it.nama, it.keterangan) } }
    }.asLiveData()

    fun save(id: Long, nama: String, keterangan: String) {
        viewModelScope.launch {
            when (type) {
                MasterType.KATEGORI -> repository.saveKategori(Kategori(id = id, nama = nama))
                MasterType.SATUAN -> repository.saveSatuan(Satuan(id = id, nama = nama))
                MasterType.JENIS_BARANG -> repository.saveJenisBarang(JenisBarang(id = id, nama = nama))
                MasterType.LOKASI -> repository.saveLokasi(Lokasi(id = id, nama = nama, keterangan = keterangan))
            }
        }
    }

    fun delete(item: SimpleMasterItem) {
        viewModelScope.launch {
            when (type) {
                MasterType.KATEGORI -> repository.deleteKategori(Kategori(id = item.id, nama = item.nama))
                MasterType.SATUAN -> repository.deleteSatuan(Satuan(id = item.id, nama = item.nama))
                MasterType.JENIS_BARANG -> repository.deleteJenisBarang(JenisBarang(id = item.id, nama = item.nama))
                MasterType.LOKASI -> repository.deleteLokasi(Lokasi(id = item.id, nama = item.nama, keterangan = item.keterangan))
            }
        }
    }
}
