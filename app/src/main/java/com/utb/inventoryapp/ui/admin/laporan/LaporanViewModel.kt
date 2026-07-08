package com.utb.inventoryapp.ui.admin.laporan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utb.inventoryapp.data.repository.InventoryRepository
import com.utb.inventoryapp.util.kondisiLabel
import com.utb.inventoryapp.util.statusLabel
import com.utb.inventoryapp.util.toFormattedDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

enum class LaporanType(val title: String) {
    BARANG("Laporan Barang"),
    PEMINJAMAN("Laporan Peminjaman"),
    PENGEMBALIAN("Laporan Pengembalian"),
    AKTIVITAS("Laporan Aktivitas")
}

class LaporanViewModel(private val repository: InventoryRepository) : ViewModel() {

    private val _reportText = MutableLiveData<String>()
    val reportText: LiveData<String> = _reportText

    fun loadReport(type: LaporanType) {
        viewModelScope.launch {
            val sb = StringBuilder()
            sb.appendLine(type.title)
            sb.appendLine("=".repeat(40))
            sb.appendLine()
            when (type) {
                LaporanType.BARANG -> {
                    val barangList = repository.getAllBarang().first()
                    if (barangList.isEmpty()) sb.appendLine("Belum ada data barang.")
                    barangList.forEach {
                        sb.appendLine("${it.kode}  ${it.nama}")
                        sb.appendLine("  Stok: ${it.stok} | Kondisi: ${kondisiLabel(it.kondisi)}")
                        sb.appendLine()
                    }
                }
                LaporanType.PEMINJAMAN -> {
                    val peminjamanList = repository.getAllPeminjaman().first()
                    val barangMap = repository.getAllBarang().first().associateBy { it.id }
                    val userMap = repository.getAllUser().first().associateBy { it.id }
                    if (peminjamanList.isEmpty()) sb.appendLine("Belum ada data peminjaman.")
                    peminjamanList.forEach {
                        sb.appendLine("${barangMap[it.barangId]?.nama ?: "-"} (x${it.jumlah})")
                        sb.appendLine("  PIC: ${userMap[it.picUserId]?.namaLengkap ?: "-"}")
                        sb.appendLine("  Tanggal Pinjam: ${it.tanggalPinjam.toFormattedDate()}")
                        sb.appendLine("  Status: ${statusLabel(it.status)}")
                        sb.appendLine()
                    }
                }
                LaporanType.PENGEMBALIAN -> {
                    val pengembalianList = repository.getAllPengembalian().first()
                    val peminjamanMap = repository.getAllPeminjaman().first().associateBy { it.id }
                    val barangMap = repository.getAllBarang().first().associateBy { it.id }
                    if (pengembalianList.isEmpty()) sb.appendLine("Belum ada data pengembalian.")
                    pengembalianList.forEach {
                        val peminjaman = peminjamanMap[it.peminjamanId]
                        val namaBarang = peminjaman?.let { p -> barangMap[p.barangId]?.nama } ?: "-"
                        sb.appendLine(namaBarang)
                        sb.appendLine("  Tanggal Kembali: ${it.tanggalKembali.toFormattedDate()}")
                        sb.appendLine("  Kondisi: ${kondisiLabel(it.kondisiKembali)}")
                        if (it.keterangan.isNotBlank()) sb.appendLine("  Keterangan: ${it.keterangan}")
                        sb.appendLine()
                    }
                }
                LaporanType.AKTIVITAS -> {
                    val riwayatList = repository.getAllRiwayat().first()
                    val userMap = repository.getAllUser().first().associateBy { it.id }
                    if (riwayatList.isEmpty()) sb.appendLine("Belum ada data aktivitas.")
                    riwayatList.forEach {
                        sb.appendLine("${it.tanggal.toFormattedDate("dd MMM yyyy HH:mm")} - ${userMap[it.userId]?.namaLengkap ?: "-"}")
                        sb.appendLine("  ${it.aktivitas}")
                        sb.appendLine()
                    }
                }
            }
            _reportText.value = sb.toString()
        }
    }
}
