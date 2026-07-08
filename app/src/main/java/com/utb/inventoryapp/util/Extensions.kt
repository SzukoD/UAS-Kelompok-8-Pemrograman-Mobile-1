package com.utb.inventoryapp.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.utb.inventoryapp.InventoryApplication
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.inventoryApp(): InventoryApplication = applicationContext as InventoryApplication

fun Fragment.inventoryApp(): InventoryApplication = requireContext().inventoryApp()

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Long.toFormattedDate(pattern: String = "dd MMM yyyy"): String {
    if (this <= 0L) return "-"
    val sdf = SimpleDateFormat(pattern, Locale("in", "ID"))
    return sdf.format(this)
}

fun statusLabel(status: String): String = when (status) {
    "PENDING" -> "Menunggu"
    "DISETUJUI" -> "Disetujui"
    "DITOLAK" -> "Ditolak"
    "SELESAI" -> "Selesai"
    else -> status
}

fun kondisiLabel(kondisi: String): String = when (kondisi) {
    "BAIK" -> "Baik"
    "RUSAK" -> "Rusak"
    "HILANG" -> "Hilang"
    else -> kondisi
}

fun roleLabel(role: String): String = when (role) {
    "ADMIN" -> "Admin Inventory"
    "PIC" -> "PIC"
    "KEPALA_GUDANG" -> "Kepala Gudang"
    else -> role
}
