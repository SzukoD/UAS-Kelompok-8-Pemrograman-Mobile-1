package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jenis_barang")
data class JenisBarang(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String
)
