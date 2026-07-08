package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "riwayat")
data class Riwayat(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val aktivitas: String,
    val tanggal: Long,
    val keterangan: String = ""
)
