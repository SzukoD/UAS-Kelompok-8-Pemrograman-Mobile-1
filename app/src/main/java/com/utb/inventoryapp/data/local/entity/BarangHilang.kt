package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang_hilang")
data class BarangHilang(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val barangId: Long,
    val jumlah: Int,
    val tanggal: Long,
    val keterangan: String = ""
)
