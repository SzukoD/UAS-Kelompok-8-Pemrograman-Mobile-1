package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplier")
data class Supplier(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String,
    val kontak: String = "",
    val alamat: String = ""
)
