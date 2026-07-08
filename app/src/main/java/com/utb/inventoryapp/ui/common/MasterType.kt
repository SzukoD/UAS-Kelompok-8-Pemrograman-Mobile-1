package com.utb.inventoryapp.ui.common

enum class MasterType(val title: String, val hasKeterangan: Boolean) {
    KATEGORI("Kelola Kategori", false),
    SATUAN("Kelola Satuan", false),
    JENIS_BARANG("Kelola Jenis Barang", false),
    LOKASI("Kelola Lokasi", true)
}

data class SimpleMasterItem(
    val id: Long,
    val nama: String,
    val keterangan: String = ""
)
