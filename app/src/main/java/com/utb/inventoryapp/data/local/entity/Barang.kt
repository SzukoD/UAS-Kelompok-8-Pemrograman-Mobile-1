package com.utb.inventoryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

object KondisiBarang {
    const val BAIK = "BAIK"
    const val RUSAK = "RUSAK"
    const val HILANG = "HILANG"
}

@Entity(tableName = "barang")
data class Barang(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val kode: String,
    val nama: String,
    val kategoriId: Long,
    val jenisBarangId: Long,
    val satuanId: Long,
    val supplierId: Long,
    val lokasiId: Long,
    val stok: Int,
    val kondisi: String = KondisiBarang.BAIK,
    val deskripsi: String = ""
)
