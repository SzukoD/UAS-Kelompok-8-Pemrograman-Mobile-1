package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

object StatusTransaksi {
    const val PENDING = "PENDING"
    const val DISETUJUI = "DISETUJUI"
    const val DITOLAK = "DITOLAK"
    const val SELESAI = "SELESAI"
}

@Entity(tableName = "peminjaman")
data class Peminjaman(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val barangId: Long,
    val picUserId: Long,
    val jumlah: Int,
    val tanggalPinjam: Long,
    val tanggalRencanaKembali: Long,
    val status: String = StatusTransaksi.PENDING,
    val keterangan: String = ""
)
