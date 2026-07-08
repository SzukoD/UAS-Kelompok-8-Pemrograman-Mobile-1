package com.utb.inventoryapp.data.repository

import com.utb.inventoryapp.data.local.AppDatabase
import com.utb.inventoryapp.data.local.entity.*
import kotlinx.coroutines.flow.Flow

/**
 * Single repository facade over Room DAOs. Kept simple on purpose (one class instead of
 * one-repository-per-entity) so the ViewModel layer has one obvious entry point for data access.
 */
class InventoryRepository(private val db: AppDatabase) {

    // ---------- Auth ----------
    suspend fun login(username: String, password: String): UserEntity? =
        db.userDao().login(username, password)

    suspend fun getUserById(id: Long): UserEntity? = db.userDao().getById(id)

    // ---------- Master: Kategori ----------
    fun getAllKategori(): Flow<List<Kategori>> = db.kategoriDao().getAll()
    suspend fun saveKategori(item: Kategori): Long = db.kategoriDao().insert(item)
    suspend fun updateKategori(item: Kategori) = db.kategoriDao().update(item)
    suspend fun deleteKategori(item: Kategori) = db.kategoriDao().delete(item)

    // ---------- Master: Satuan ----------
    fun getAllSatuan(): Flow<List<Satuan>> = db.satuanDao().getAll()
    suspend fun saveSatuan(item: Satuan): Long = db.satuanDao().insert(item)
    suspend fun updateSatuan(item: Satuan) = db.satuanDao().update(item)
    suspend fun deleteSatuan(item: Satuan) = db.satuanDao().delete(item)

    // ---------- Master: Jenis Barang ----------
    fun getAllJenisBarang(): Flow<List<JenisBarang>> = db.jenisBarangDao().getAll()
    suspend fun saveJenisBarang(item: JenisBarang): Long = db.jenisBarangDao().insert(item)
    suspend fun updateJenisBarang(item: JenisBarang) = db.jenisBarangDao().update(item)
    suspend fun deleteJenisBarang(item: JenisBarang) = db.jenisBarangDao().delete(item)

    // ---------- Master: Lokasi ----------
    fun getAllLokasi(): Flow<List<Lokasi>> = db.lokasiDao().getAll()
    suspend fun saveLokasi(item: Lokasi): Long = db.lokasiDao().insert(item)
    suspend fun updateLokasi(item: Lokasi) = db.lokasiDao().update(item)
    suspend fun deleteLokasi(item: Lokasi) = db.lokasiDao().delete(item)

    // ---------- Master: Supplier ----------
    fun getAllSupplier(): Flow<List<Supplier>> = db.supplierDao().getAll()
    suspend fun saveSupplier(item: Supplier): Long = db.supplierDao().insert(item)
    suspend fun updateSupplier(item: Supplier) = db.supplierDao().update(item)
    suspend fun deleteSupplier(item: Supplier) = db.supplierDao().delete(item)

    // ---------- Master: User / Pengguna ----------
    fun getAllUser(): Flow<List<UserEntity>> = db.userDao().getAll()
    suspend fun saveUser(item: UserEntity): Long = db.userDao().insert(item)
    suspend fun updateUser(item: UserEntity) = db.userDao().update(item)
    suspend fun deleteUser(item: UserEntity) = db.userDao().delete(item)

    // ---------- Barang ----------
    fun getAllBarang(): Flow<List<Barang>> = db.barangDao().getAll()
    fun searchBarang(query: String): Flow<List<Barang>> = db.barangDao().search(query)
    suspend fun getBarangById(id: Long): Barang? = db.barangDao().getById(id)
    suspend fun saveBarang(item: Barang): Long = db.barangDao().insert(item)
    suspend fun updateBarang(item: Barang) = db.barangDao().update(item)
    suspend fun deleteBarang(item: Barang) = db.barangDao().delete(item)
    fun countBarangRusak(): Flow<Int> = db.barangDao().countRusak()
    fun countBarangHilang(): Flow<Int> = db.barangDao().countHilang()

    // ---------- Peminjaman ----------
    fun getAllPeminjaman(): Flow<List<Peminjaman>> = db.peminjamanDao().getAll()
    fun getPeminjamanByUser(userId: Long): Flow<List<Peminjaman>> = db.peminjamanDao().getByUser(userId)
    fun getPeminjamanByStatus(status: String): Flow<List<Peminjaman>> = db.peminjamanDao().getByStatus(status)
    suspend fun getPeminjamanById(id: Long): Peminjaman? = db.peminjamanDao().getById(id)
    suspend fun savePeminjaman(item: Peminjaman): Long {
        // 1. Ambil data barang secara real-time berdasarkan ID barang yang dipinjam
        val barang = db.barangDao().getById(item.barangId)

        // 2. Validasi: Jika barang ditemukan dan jumlah pinjam melebihi stok, kembalikan -1L (gagal)
        if (barang != null && item.jumlah > barang.stok) {
            return -1L
        }

        // 3. Jika stok aman, baru masukkan ke database
        return db.peminjamanDao().insert(item)
    }
    suspend fun updatePeminjaman(item: Peminjaman) = db.peminjamanDao().update(item)
    fun countPeminjamanPending(): Flow<Int> = db.peminjamanDao().countPending()

    suspend fun approvePeminjaman(id: Long, disetujui: Boolean) {
        val item = db.peminjamanDao().getById(id) ?: return
        val newStatus = if (disetujui) StatusTransaksi.DISETUJUI else StatusTransaksi.DITOLAK
        db.peminjamanDao().update(item.copy(status = newStatus))
        if (disetujui) {
            db.barangDao().kurangiStok(item.barangId, item.jumlah)
        }
    }

    // ---------- Pengembalian ----------
    fun getAllPengembalian(): Flow<List<Pengembalian>> = db.pengembalianDao().getAll()
    suspend fun savePengembalian(item: Pengembalian): Long {
        val id = db.pengembalianDao().insert(item)
        val peminjaman = db.peminjamanDao().getById(item.peminjamanId)
        if (peminjaman != null) {
            db.peminjamanDao().update(peminjaman.copy(status = StatusTransaksi.SELESAI))
            if (item.kondisiKembali == KondisiBarang.BAIK) {
                db.barangDao().tambahStok(peminjaman.barangId, peminjaman.jumlah)
            } else if (item.kondisiKembali == KondisiBarang.RUSAK) {
                db.barangRusakDao().insert(
                    BarangRusak(barangId = peminjaman.barangId, jumlah = peminjaman.jumlah, tanggal = item.tanggalKembali, keterangan = item.keterangan)
                )
            } else if (item.kondisiKembali == KondisiBarang.HILANG) {
                db.barangHilangDao().insert(
                    BarangHilang(barangId = peminjaman.barangId, jumlah = peminjaman.jumlah, tanggal = item.tanggalKembali, keterangan = item.keterangan)
                )
            }
        }
        return id
    }

    // ---------- Permintaan Sparepart ----------
    fun getAllPermintaanSparepart(): Flow<List<PermintaanSparepart>> = db.permintaanSparepartDao().getAll()
    fun getPermintaanSparepartByUser(userId: Long): Flow<List<PermintaanSparepart>> = db.permintaanSparepartDao().getByUser(userId)
    fun getPermintaanSparepartByStatus(status: String): Flow<List<PermintaanSparepart>> = db.permintaanSparepartDao().getByStatus(status)
    suspend fun getPermintaanSparepartById(id: Long): PermintaanSparepart? = db.permintaanSparepartDao().getById(id)
    suspend fun savePermintaanSparepart(item: PermintaanSparepart): Long = db.permintaanSparepartDao().insert(item)
    fun countPermintaanSparepartPending(): Flow<Int> = db.permintaanSparepartDao().countPending()

    suspend fun approvePermintaanSparepart(id: Long, disetujui: Boolean) {
        val item = db.permintaanSparepartDao().getById(id) ?: return
        val newStatus = if (disetujui) StatusTransaksi.DISETUJUI else StatusTransaksi.DITOLAK
        db.permintaanSparepartDao().update(item.copy(status = newStatus))
        if (disetujui) {
            db.barangDao().kurangiStok(item.barangId, item.jumlah)
        }
    }

    // ---------- Barang Rusak / Hilang ----------
    fun getAllBarangRusak(): Flow<List<BarangRusak>> = db.barangRusakDao().getAll()
    fun getAllBarangHilang(): Flow<List<BarangHilang>> = db.barangHilangDao().getAll()

    // ---------- Riwayat ----------
    fun getAllRiwayat(): Flow<List<Riwayat>> = db.riwayatDao().getAll()
    fun getRiwayatByUser(userId: Long): Flow<List<Riwayat>> = db.riwayatDao().getByUser(userId)
    suspend fun catatRiwayat(userId: Long, aktivitas: String, keterangan: String = "") {
        db.riwayatDao().insert(
            Riwayat(userId = userId, aktivitas = aktivitas, tanggal = System.currentTimeMillis(), keterangan = keterangan)
        )
    }

    // ---------- Dashboard counters ----------
    suspend fun countBarang(): Int = db.barangDao().count()
    suspend fun countSupplier(): Int = db.supplierDao().count()
    suspend fun countUser(): Int = db.userDao().count()
    suspend fun countPeminjaman(): Int = db.peminjamanDao().count()
}
