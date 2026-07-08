package com.utb.inventoryapp.ui.admin.approval

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.StatusTransaksi
import com.utb.inventoryapp.data.repository.InventoryRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ApprovalViewModel(private val repository: InventoryRepository) : ViewModel() {

    val approvalItems: LiveData<List<ApprovalItem>> = combine(
        repository.getPeminjamanByStatus(StatusTransaksi.PENDING),
        repository.getPermintaanSparepartByStatus(StatusTransaksi.PENDING),
        repository.getAllBarang(),
        repository.getAllUser()
    ) { peminjamanList, sparepartList, barangList, userList ->
        val barangMap = barangList.associateBy { it.id }
        val userMap = userList.associateBy { it.id }
        val fromPeminjaman = peminjamanList.map {
            ApprovalItem(
                id = it.id,
                jenis = ApprovalJenis.PEMINJAMAN,
                barangNama = barangMap[it.barangId]?.nama ?: "-",
                picNama = userMap[it.picUserId]?.namaLengkap ?: "-",
                jumlah = it.jumlah,
                tanggal = it.tanggalPinjam
            )
        }
        val fromSparepart = sparepartList.map {
            ApprovalItem(
                id = it.id,
                jenis = ApprovalJenis.SPAREPART,
                barangNama = barangMap[it.barangId]?.nama ?: "-",
                picNama = userMap[it.picUserId]?.namaLengkap ?: "-",
                jumlah = it.jumlah,
                tanggal = it.tanggal
            )
        }
        (fromPeminjaman + fromSparepart).sortedByDescending { it.tanggal }
    }.asLiveData()

    fun approve(item: ApprovalItem, disetujui: Boolean) {
        viewModelScope.launch {
            when (item.jenis) {
                ApprovalJenis.PEMINJAMAN -> repository.approvePeminjaman(item.id, disetujui)
                ApprovalJenis.SPAREPART -> repository.approvePermintaanSparepart(item.id, disetujui)
            }
        }
    }
}
