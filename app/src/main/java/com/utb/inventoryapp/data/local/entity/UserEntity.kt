package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

object Role {
    const val ADMIN = "ADMIN"
    const val PIC = "PIC"
    const val KEPALA_GUDANG = "KEPALA_GUDANG"
}

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val password: String,
    val namaLengkap: String,
    val role: String
)
