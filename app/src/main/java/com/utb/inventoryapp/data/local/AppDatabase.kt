package com.utb.inventoryapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utb.inventoryapp.data.local.dao.*
import com.utb.inventoryapp.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Kategori::class,
        Satuan::class,
        JenisBarang::class,
        Lokasi::class,
        Supplier::class,
        UserEntity::class,
        Barang::class,
        Peminjaman::class,
        Pengembalian::class,
        PermintaanSparepart::class,
        BarangRusak::class,
        BarangHilang::class,
        Riwayat::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun kategoriDao(): KategoriDao
    abstract fun satuanDao(): SatuanDao
    abstract fun jenisBarangDao(): JenisBarangDao
    abstract fun lokasiDao(): LokasiDao
    abstract fun supplierDao(): SupplierDao
    abstract fun userDao(): UserDao
    abstract fun barangDao(): BarangDao
    abstract fun peminjamanDao(): PeminjamanDao
    abstract fun pengembalianDao(): PengembalianDao
    abstract fun permintaanSparepartDao(): PermintaanSparepartDao
    abstract fun barangRusakDao(): BarangRusakDao
    abstract fun barangHilangDao(): BarangHilangDao
    abstract fun riwayatDao(): RiwayatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inventory_app.db"
                )
                    .addCallback(SeedCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SeedCallback(private val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    seedDummyData(database)
                }
            }
        }

        private suspend fun seedDummyData(db: AppDatabase) {
            // Users
            val userIds = mutableMapOf<String, Long>()
            userIds["admin"] = db.userDao().insert(
                UserEntity(username = "admin", password = "admin123", namaLengkap = "Admin Utama", role = Role.ADMIN)
            )
            userIds["pic1"] = db.userDao().insert(
                UserEntity(username = "pic1", password = "pic123", namaLengkap = "Budi Santoso", role = Role.PIC)
            )
            userIds["pic2"] = db.userDao().insert(
                UserEntity(username = "pic2", password = "pic123", namaLengkap = "Siti Aminah", role = Role.PIC)
            )
            userIds["gudang1"] = db.userDao().insert(
                UserEntity(username = "gudang1", password = "gudang123", namaLengkap = "Herman Kepala Gudang", role = Role.KEPALA_GUDANG)
            )

            // Kategori
            val kategoriIds = listOf("Elektronik", "Alat Berat", "Alat Tulis Kantor", "Sparepart").map {
                db.kategoriDao().insert(Kategori(nama = it))
            }

            // Satuan
            db.satuanDao().insert(Satuan(nama = "Unit"))
            db.satuanDao().insert(Satuan(nama = "Pcs"))
            db.satuanDao().insert(Satuan(nama = "Box"))
            val satuanUnit = 1L

            // Jenis Barang
            db.jenisBarangDao().insert(JenisBarang(nama = "Consumable"))
            val jenisAsset = db.jenisBarangDao().insert(JenisBarang(nama = "Aset Tetap"))

            // Supplier
            val supplierId = db.supplierDao().insert(
                Supplier(nama = "PT Sumber Makmur", kontak = "021-5551234", alamat = "Jl. Industri No. 10, Bandung")
            )
            db.supplierDao().insert(
                Supplier(nama = "CV Teknik Jaya", kontak = "021-5556789", alamat = "Jl. Merdeka No. 5, Bandung")
            )

            // Lokasi
            val lokasiA = db.lokasiDao().insert(Lokasi(nama = "Gudang A", keterangan = "Gudang utama lantai 1"))
            db.lokasiDao().insert(Lokasi(nama = "Gudang B", keterangan = "Gudang cadangan lantai 2"))

            // Barang
            val barang1 = db.barangDao().insert(
                Barang(
                    kode = "BRG-001", nama = "Laptop Dell Latitude", kategoriId = kategoriIds[0],
                    jenisBarangId = jenisAsset, satuanId = satuanUnit, supplierId = supplierId,
                    lokasiId = lokasiA, stok = 10, kondisi = KondisiBarang.BAIK,
                    deskripsi = "Laptop untuk keperluan kantor"
                )
            )
            db.barangDao().insert(
                Barang(
                    kode = "BRG-002", nama = "Proyektor Epson", kategoriId = kategoriIds[0],
                    jenisBarangId = jenisAsset, satuanId = satuanUnit, supplierId = supplierId,
                    lokasiId = lokasiA, stok = 5, kondisi = KondisiBarang.BAIK,
                    deskripsi = "Proyektor untuk ruang meeting"
                )
            )
            db.barangDao().insert(
                Barang(
                    kode = "BRG-003", nama = "Bor Listrik", kategoriId = kategoriIds[1],
                    jenisBarangId = jenisAsset, satuanId = satuanUnit, supplierId = supplierId,
                    lokasiId = lokasiA, stok = 8, kondisi = KondisiBarang.BAIK,
                    deskripsi = "Bor listrik untuk perawatan gudang"
                )
            )
            db.barangDao().insert(
                Barang(
                    kode = "BRG-004", nama = "Kertas A4", kategoriId = kategoriIds[2],
                    jenisBarangId = 1, satuanId = satuanUnit, supplierId = supplierId,
                    lokasiId = lokasiA, stok = 50, kondisi = KondisiBarang.BAIK,
                    deskripsi = "Kertas cetak ukuran A4"
                )
            )
            db.barangDao().insert(
                Barang(
                    kode = "BRG-005", nama = "Filter Oli", kategoriId = kategoriIds[3],
                    jenisBarangId = 1, satuanId = satuanUnit, supplierId = supplierId,
                    lokasiId = lokasiA, stok = 20, kondisi = KondisiBarang.BAIK,
                    deskripsi = "Sparepart filter oli mesin"
                )
            )

            val picId = userIds["pic1"] ?: 1L
            val now = System.currentTimeMillis()

            // Contoh transaksi peminjaman
            db.peminjamanDao().insert(
                Peminjaman(
                    barangId = barang1, picUserId = picId, jumlah = 1,
                    tanggalPinjam = now, tanggalRencanaKembali = now + 3 * 24 * 3600 * 1000L,
                    status = StatusTransaksi.PENDING, keterangan = "Dipinjam untuk presentasi client"
                )
            )

            db.riwayatDao().insert(
                Riwayat(userId = picId, aktivitas = "Login pertama kali", tanggal = now)
            )
        }
    }
}
