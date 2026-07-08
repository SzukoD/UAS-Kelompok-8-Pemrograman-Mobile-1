package com.utb.inventoryapp.ui.pic.peminjaman

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.local.entity.Peminjaman
import com.utb.inventoryapp.data.local.entity.StatusTransaksi
import com.utb.inventoryapp.data.repository.InventoryRepository
import com.utb.inventoryapp.ui.admin.barang.RefOption
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first // Tambahkan import ini
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class PeminjamanRow(val peminjaman: Peminjaman, val barangNama: String)

class PeminjamanViewModel(private val repository: InventoryRepository, private val userId: Long) : ViewModel() {

    val items: LiveData<List<PeminjamanRow>> = combine(
        repository.getPeminjamanByUser(userId),
        repository.getAllBarang()
    ) { peminjamanList, barangList ->
        val barangMap = barangList.associateBy { it.id }
        peminjamanList.map { PeminjamanRow(it, barangMap[it.barangId]?.nama ?: "-") }
    }.asLiveData()

    val barangOptions: LiveData<List<RefOption>> =
        repository.getAllBarang().map { list -> list.filter { it.stok > 0 }.map { RefOption(it.id, "${it.kode} - ${it.nama} (stok ${it.stok})") } }.asLiveData()

    // Ubah parameter onDone menjadi onResult agar bisa mengirim status dan pesan error
    fun createPeminjaman(barangId: Long, jumlah: Int, tanggalRencanaKembali: Long, keterangan: String, onResult: (success: Boolean, message: String) -> Unit) {
        viewModelScope.launch {
            val peminjaman = Peminjaman(
                barangId = barangId,
                picUserId = userId,
                jumlah = jumlah,
                tanggalPinjam = System.currentTimeMillis(),
                tanggalRencanaKembali = tanggalRencanaKembali,
                status = StatusTransaksi.PENDING,
                keterangan = keterangan
            )

            // Panggil fungsi repository dan tampung hasilnya
            val resultId = repository.savePeminjaman(peminjaman)

            // Jika repository mengembalikan -1L, berarti stok tidak cukup
            if (resultId == -1L) {
                onResult(false, "Gagal! Jumlah pinjam melebihi stok yang tersedia.")
            } else {
                // Jika sukses, catat riwayat dan berikan callback sukses
                repository.catatRiwayat(userId, "Mengajukan peminjaman alat", keterangan)
                onResult(true, "Peminjaman berhasil diajukan")
            }
        }
    }
}