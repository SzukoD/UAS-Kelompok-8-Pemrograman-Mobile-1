package com.utb.inventoryapp.ui.pic.sparepart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.PermintaanSparepart
import com.utb.inventoryapp.data.local.entity.StatusTransaksi
import com.utb.inventoryapp.data.repository.InventoryRepository
import com.utb.inventoryapp.ui.admin.barang.RefOption
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class SparepartRow(val permintaan: PermintaanSparepart, val barangNama: String)

class PermintaanSparepartViewModel(private val repository: InventoryRepository, private val userId: Long) : ViewModel() {

    val items: LiveData<List<SparepartRow>> = combine(
        repository.getPermintaanSparepartByUser(userId),
        repository.getAllBarang()
    ) { list, barangList ->
        val barangMap = barangList.associateBy { it.id }
        list.map { SparepartRow(it, barangMap[it.barangId]?.nama ?: "-") }
    }.asLiveData()

    val barangOptions: LiveData<List<RefOption>> =
        repository.getAllBarang().map { list -> list.map { RefOption(it.id, "${it.kode} - ${it.nama}") } }.asLiveData()

    fun createPermintaan(barangId: Long, jumlah: Int, keterangan: String, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.savePermintaanSparepart(
                PermintaanSparepart(
                    barangId = barangId,
                    picUserId = userId,
                    jumlah = jumlah,
                    tanggal = System.currentTimeMillis(),
                    status = StatusTransaksi.PENDING,
                    keterangan = keterangan
                )
            )
            repository.catatRiwayat(userId, "Mengajukan permintaan sparepart", keterangan)
            onDone()
        }
    }
}
