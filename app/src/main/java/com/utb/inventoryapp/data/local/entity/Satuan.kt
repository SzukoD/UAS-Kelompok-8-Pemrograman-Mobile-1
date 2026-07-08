package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satuan")
data class Satuan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nama: String
)
