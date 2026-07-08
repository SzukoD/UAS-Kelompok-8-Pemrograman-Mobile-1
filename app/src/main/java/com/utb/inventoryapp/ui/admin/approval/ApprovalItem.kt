package com.utb.inventoryapp.ui.admin.approval

enum class ApprovalJenis { PEMINJAMAN, SPAREPART }

data class ApprovalItem(
    val id: Long,
    val jenis: ApprovalJenis,
    val barangNama: String,
    val picNama: String,
    val jumlah: Int,
    val tanggal: Long
)
