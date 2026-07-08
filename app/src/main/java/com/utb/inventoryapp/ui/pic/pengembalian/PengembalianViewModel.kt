package com.utb.inventoryapp.ui.pic.pengembalian

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.KondisiBarang
import com.utb.inventoryapp.data.local.entity.Pengembalian
import com.utb.inventoryapp.data.local.entity.Peminjaman
import com.utb.inventoryapp.data.local.entity.StatusTransaksi
import com.utb.inventoryapp.data.repository.InventoryRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class PeminjamanOption(val peminjaman: Peminjaman, val label: String) {
    override fun toString(): String = label
}

data class PengembalianRow(val pengembalian: Pengembalian, val barangNama: String)

class PengembalianViewModel(private val repository: InventoryRepository, private val userId: Long) : ViewModel() {

    /** Peminjaman yang sudah disetujui dan belum dikembalikan, milik user ini */
    val peminjamanAktif: LiveData<List<PeminjamanOption>> = combine(
        repository.getPeminjamanByUser(userId),
        repository.getAllBarang()
    ) { list, barangList ->
        val barangMap = barangList.associateBy { it.id }
        list.filter { it.status == StatusTransaksi.DISETUJUI }
            .map { PeminjamanOption(it, "${barangMap[it.barangId]?.nama ?: "-"} (x${it.jumlah})") }
    }.asLiveData()

    val riwayatPengembalian: LiveData<List<PengembalianRow>> = combine(
        repository.getAllPengembalian(),
        repository.getAllPeminjaman(),
        repository.getAllBarang()
    ) { pengembalianList, peminjamanList, barangList ->
        val peminjamanMap = peminjamanList.associateBy { it.id }
        val barangMap = barangList.associateBy { it.id }
        pengembalianList
            .filter { peminjamanMap[it.peminjamanId]?.picUserId == userId }
            .map {
                val namaBarang = peminjamanMap[it.peminjamanId]?.let { p -> barangMap[p.barangId]?.nama } ?: "-"
                PengembalianRow(it, namaBarang)
            }
    }.asLiveData()

    val kondisiOptions = listOf(KondisiBarang.BAIK, KondisiBarang.RUSAK, KondisiBarang.HILANG)

    fun kembalikan(peminjamanId: Long, kondisi: String, keterangan: String, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.savePengembalian(
                Pengembalian(
                    peminjamanId = peminjamanId,
                    tanggalKembali = System.currentTimeMillis(),
                    kondisiKembali = kondisi,
                    keterangan = keterangan
                )
            )
            repository.catatRiwayat(userId, "Mengembalikan alat", keterangan)
            onDone()
        }
    }
}
