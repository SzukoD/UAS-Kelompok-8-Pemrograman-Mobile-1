package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lokasi")
data class Lokasi(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String,
    val keterangan: String = ""
)
