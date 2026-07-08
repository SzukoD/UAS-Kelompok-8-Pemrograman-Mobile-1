package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengembalian")
data class Pengembalian(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val peminjamanId: Long,
    val tanggalKembali: Long,
    val kondisiKembali: String = KondisiBarang.BAIK,
    val keterangan: String = ""
)
