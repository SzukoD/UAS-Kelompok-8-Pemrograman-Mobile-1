package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "permintaan_sparepart")
data class PermintaanSparepart(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val barangId: Long,
    val picUserId: Long,
    val jumlah: Int,
    val tanggal: Long,
    val status: String = StatusTransaksi.PENDING,
    val keterangan: String = ""
)
