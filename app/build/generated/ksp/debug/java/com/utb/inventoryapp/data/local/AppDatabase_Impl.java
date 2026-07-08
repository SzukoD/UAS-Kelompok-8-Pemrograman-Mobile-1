package com.utb.inventoryapp.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.utb.inventoryapp.data.local.dao.BarangDao;
import com.utb.inventoryapp.data.local.dao.BarangDao_Impl;
import com.utb.inventoryapp.data.local.dao.BarangHilangDao;
import com.utb.inventoryapp.data.local.dao.BarangHilangDao_Impl;
import com.utb.inventoryapp.data.local.dao.BarangRusakDao;
import com.utb.inventoryapp.data.local.dao.BarangRusakDao_Impl;
import com.utb.inventoryapp.data.local.dao.JenisBarangDao;
import com.utb.inventoryapp.data.local.dao.JenisBarangDao_Impl;
import com.utb.inventoryapp.data.local.dao.KategoriDao;
import com.utb.inventoryapp.data.local.dao.KategoriDao_Impl;
import com.utb.inventoryapp.data.local.dao.LokasiDao;
import com.utb.inventoryapp.data.local.dao.LokasiDao_Impl;
import com.utb.inventoryapp.data.local.dao.PeminjamanDao;
import com.utb.inventoryapp.data.local.dao.PeminjamanDao_Impl;
import com.utb.inventoryapp.data.local.dao.PengembalianDao;
import com.utb.inventoryapp.data.local.dao.PengembalianDao_Impl;
import com.utb.inventoryapp.data.local.dao.PermintaanSparepartDao;
import com.utb.inventoryapp.data.local.dao.PermintaanSparepartDao_Impl;
import com.utb.inventoryapp.data.local.dao.RiwayatDao;
import com.utb.inventoryapp.data.local.dao.RiwayatDao_Impl;
import com.utb.inventoryapp.data.local.dao.SatuanDao;
import com.utb.inventoryapp.data.local.dao.SatuanDao_Impl;
import com.utb.inventoryapp.data.local.dao.SupplierDao;
import com.utb.inventoryapp.data.local.dao.SupplierDao_Impl;
import com.utb.inventoryapp.data.local.dao.UserDao;
import com.utb.inventoryapp.data.local.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile KategoriDao _kategoriDao;

  private volatile SatuanDao _satuanDao;

  private volatile JenisBarangDao _jenisBarangDao;

  private volatile LokasiDao _lokasiDao;

  private volatile SupplierDao _supplierDao;

  private volatile UserDao _userDao;

  private volatile BarangDao _barangDao;

  private volatile PeminjamanDao _peminjamanDao;

  private volatile PengembalianDao _pengembalianDao;

  private volatile PermintaanSparepartDao _permintaanSparepartDao;

  private volatile BarangRusakDao _barangRusakDao;

  private volatile BarangHilangDao _barangHilangDao;

  private volatile RiwayatDao _riwayatDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `kategori` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nama` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `satuan` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nama` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `jenis_barang` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nama` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `lokasi` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nama` TEXT NOT NULL, `keterangan` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `supplier` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nama` TEXT NOT NULL, `kontak` TEXT NOT NULL, `alamat` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT NOT NULL, `password` TEXT NOT NULL, `namaLengkap` TEXT NOT NULL, `role` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `barang` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `kode` TEXT NOT NULL, `nama` TEXT NOT NULL, `kategoriId` INTEGER NOT NULL, `jenisBarangId` INTEGER NOT NULL, `satuanId` INTEGER NOT NULL, `supplierId` INTEGER NOT NULL, `lokasiId` INTEGER NOT NULL, `stok` INTEGER NOT NULL, `kondisi` TEXT NOT NULL, `deskripsi` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `peminjaman` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `barangId` INTEGER NOT NULL, `picUserId` INTEGER NOT NULL, `jumlah` INTEGER NOT NULL, `tanggalPinjam` INTEGER NOT NULL, `tanggalRencanaKembali` INTEGER NOT NULL, `status` TEXT NOT NULL, `keterangan` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `pengembalian` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `peminjamanId` INTEGER NOT NULL, `tanggalKembali` INTEGER NOT NULL, `kondisiKembali` TEXT NOT NULL, `keterangan` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `permintaan_sparepart` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `barangId` INTEGER NOT NULL, `picUserId` INTEGER NOT NULL, `jumlah` INTEGER NOT NULL, `tanggal` INTEGER NOT NULL, `status` TEXT NOT NULL, `keterangan` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `barang_rusak` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `barangId` INTEGER NOT NULL, `jumlah` INTEGER NOT NULL, `tanggal` INTEGER NOT NULL, `keterangan` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `barang_hilang` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `barangId` INTEGER NOT NULL, `jumlah` INTEGER NOT NULL, `tanggal` INTEGER NOT NULL, `keterangan` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `riwayat` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `userId` INTEGER NOT NULL, `aktivitas` TEXT NOT NULL, `tanggal` INTEGER NOT NULL, `keterangan` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '37fed9f5675b0f9ebe4161e2ebcc67d5')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `kategori`");
        db.execSQL("DROP TABLE IF EXISTS `satuan`");
        db.execSQL("DROP TABLE IF EXISTS `jenis_barang`");
        db.execSQL("DROP TABLE IF EXISTS `lokasi`");
        db.execSQL("DROP TABLE IF EXISTS `supplier`");
        db.execSQL("DROP TABLE IF EXISTS `user`");
        db.execSQL("DROP TABLE IF EXISTS `barang`");
        db.execSQL("DROP TABLE IF EXISTS `peminjaman`");
        db.execSQL("DROP TABLE IF EXISTS `pengembalian`");
        db.execSQL("DROP TABLE IF EXISTS `permintaan_sparepart`");
        db.execSQL("DROP TABLE IF EXISTS `barang_rusak`");
        db.execSQL("DROP TABLE IF EXISTS `barang_hilang`");
        db.execSQL("DROP TABLE IF EXISTS `riwayat`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsKategori = new HashMap<String, TableInfo.Column>(2);
        _columnsKategori.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsKategori.put("nama", new TableInfo.Column("nama", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysKategori = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesKategori = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoKategori = new TableInfo("kategori", _columnsKategori, _foreignKeysKategori, _indicesKategori);
        final TableInfo _existingKategori = TableInfo.read(db, "kategori");
        if (!_infoKategori.equals(_existingKategori)) {
          return new RoomOpenHelper.ValidationResult(false, "kategori(com.utb.inventoryapp.data.local.entity.Kategori).\n"
                  + " Expected:\n" + _infoKategori + "\n"
                  + " Found:\n" + _existingKategori);
        }
        final HashMap<String, TableInfo.Column> _columnsSatuan = new HashMap<String, TableInfo.Column>(2);
        _columnsSatuan.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSatuan.put("nama", new TableInfo.Column("nama", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSatuan = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSatuan = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSatuan = new TableInfo("satuan", _columnsSatuan, _foreignKeysSatuan, _indicesSatuan);
        final TableInfo _existingSatuan = TableInfo.read(db, "satuan");
        if (!_infoSatuan.equals(_existingSatuan)) {
          return new RoomOpenHelper.ValidationResult(false, "satuan(com.utb.inventoryapp.data.local.entity.Satuan).\n"
                  + " Expected:\n" + _infoSatuan + "\n"
                  + " Found:\n" + _existingSatuan);
        }
        final HashMap<String, TableInfo.Column> _columnsJenisBarang = new HashMap<String, TableInfo.Column>(2);
        _columnsJenisBarang.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJenisBarang.put("nama", new TableInfo.Column("nama", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysJenisBarang = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesJenisBarang = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoJenisBarang = new TableInfo("jenis_barang", _columnsJenisBarang, _foreignKeysJenisBarang, _indicesJenisBarang);
        final TableInfo _existingJenisBarang = TableInfo.read(db, "jenis_barang");
        if (!_infoJenisBarang.equals(_existingJenisBarang)) {
          return new RoomOpenHelper.ValidationResult(false, "jenis_barang(com.utb.inventoryapp.data.local.entity.JenisBarang).\n"
                  + " Expected:\n" + _infoJenisBarang + "\n"
                  + " Found:\n" + _existingJenisBarang);
        }
        final HashMap<String, TableInfo.Column> _columnsLokasi = new HashMap<String, TableInfo.Column>(3);
        _columnsLokasi.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLokasi.put("nama", new TableInfo.Column("nama", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLokasi.put("keterangan", new TableInfo.Column("keterangan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLokasi = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLokasi = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLokasi = new TableInfo("lokasi", _columnsLokasi, _foreignKeysLokasi, _indicesLokasi);
        final TableInfo _existingLokasi = TableInfo.read(db, "lokasi");
        if (!_infoLokasi.equals(_existingLokasi)) {
          return new RoomOpenHelper.ValidationResult(false, "lokasi(com.utb.inventoryapp.data.local.entity.Lokasi).\n"
                  + " Expected:\n" + _infoLokasi + "\n"
                  + " Found:\n" + _existingLokasi);
        }
        final HashMap<String, TableInfo.Column> _columnsSupplier = new HashMap<String, TableInfo.Column>(4);
        _columnsSupplier.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupplier.put("nama", new TableInfo.Column("nama", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupplier.put("kontak", new TableInfo.Column("kontak", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupplier.put("alamat", new TableInfo.Column("alamat", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSupplier = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSupplier = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSupplier = new TableInfo("supplier", _columnsSupplier, _foreignKeysSupplier, _indicesSupplier);
        final TableInfo _existingSupplier = TableInfo.read(db, "supplier");
        if (!_infoSupplier.equals(_existingSupplier)) {
          return new RoomOpenHelper.ValidationResult(false, "supplier(com.utb.inventoryapp.data.local.entity.Supplier).\n"
                  + " Expected:\n" + _infoSupplier + "\n"
                  + " Found:\n" + _existingSupplier);
        }
        final HashMap<String, TableInfo.Column> _columnsUser = new HashMap<String, TableInfo.Column>(5);
        _columnsUser.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("username", new TableInfo.Column("username", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("namaLengkap", new TableInfo.Column("namaLengkap", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUser = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUser = new TableInfo("user", _columnsUser, _foreignKeysUser, _indicesUser);
        final TableInfo _existingUser = TableInfo.read(db, "user");
        if (!_infoUser.equals(_existingUser)) {
          return new RoomOpenHelper.ValidationResult(false, "user(com.utb.inventoryapp.data.local.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUser + "\n"
                  + " Found:\n" + _existingUser);
        }
        final HashMap<String, TableInfo.Column> _columnsBarang = new HashMap<String, TableInfo.Column>(11);
        _columnsBarang.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("kode", new TableInfo.Column("kode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("nama", new TableInfo.Column("nama", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("kategoriId", new TableInfo.Column("kategoriId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("jenisBarangId", new TableInfo.Column("jenisBarangId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("satuanId", new TableInfo.Column("satuanId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("supplierId", new TableInfo.Column("supplierId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("lokasiId", new TableInfo.Column("lokasiId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("stok", new TableInfo.Column("stok", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("kondisi", new TableInfo.Column("kondisi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarang.put("deskripsi", new TableInfo.Column("deskripsi", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBarang = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBarang = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBarang = new TableInfo("barang", _columnsBarang, _foreignKeysBarang, _indicesBarang);
        final TableInfo _existingBarang = TableInfo.read(db, "barang");
        if (!_infoBarang.equals(_existingBarang)) {
          return new RoomOpenHelper.ValidationResult(false, "barang(com.utb.inventoryapp.data.local.entity.Barang).\n"
                  + " Expected:\n" + _infoBarang + "\n"
                  + " Found:\n" + _existingBarang);
        }
        final HashMap<String, TableInfo.Column> _columnsPeminjaman = new HashMap<String, TableInfo.Column>(8);
        _columnsPeminjaman.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeminjaman.put("barangId", new TableInfo.Column("barangId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeminjaman.put("picUserId", new TableInfo.Column("picUserId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeminjaman.put("jumlah", new TableInfo.Column("jumlah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeminjaman.put("tanggalPinjam", new TableInfo.Column("tanggalPinjam", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeminjaman.put("tanggalRencanaKembali", new TableInfo.Column("tanggalRencanaKembali", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeminjaman.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPeminjaman.put("keterangan", new TableInfo.Column("keterangan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPeminjaman = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPeminjaman = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPeminjaman = new TableInfo("peminjaman", _columnsPeminjaman, _foreignKeysPeminjaman, _indicesPeminjaman);
        final TableInfo _existingPeminjaman = TableInfo.read(db, "peminjaman");
        if (!_infoPeminjaman.equals(_existingPeminjaman)) {
          return new RoomOpenHelper.ValidationResult(false, "peminjaman(com.utb.inventoryapp.data.local.entity.Peminjaman).\n"
                  + " Expected:\n" + _infoPeminjaman + "\n"
                  + " Found:\n" + _existingPeminjaman);
        }
        final HashMap<String, TableInfo.Column> _columnsPengembalian = new HashMap<String, TableInfo.Column>(5);
        _columnsPengembalian.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengembalian.put("peminjamanId", new TableInfo.Column("peminjamanId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengembalian.put("tanggalKembali", new TableInfo.Column("tanggalKembali", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengembalian.put("kondisiKembali", new TableInfo.Column("kondisiKembali", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPengembalian.put("keterangan", new TableInfo.Column("keterangan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPengembalian = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPengembalian = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPengembalian = new TableInfo("pengembalian", _columnsPengembalian, _foreignKeysPengembalian, _indicesPengembalian);
        final TableInfo _existingPengembalian = TableInfo.read(db, "pengembalian");
        if (!_infoPengembalian.equals(_existingPengembalian)) {
          return new RoomOpenHelper.ValidationResult(false, "pengembalian(com.utb.inventoryapp.data.local.entity.Pengembalian).\n"
                  + " Expected:\n" + _infoPengembalian + "\n"
                  + " Found:\n" + _existingPengembalian);
        }
        final HashMap<String, TableInfo.Column> _columnsPermintaanSparepart = new HashMap<String, TableInfo.Column>(7);
        _columnsPermintaanSparepart.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPermintaanSparepart.put("barangId", new TableInfo.Column("barangId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPermintaanSparepart.put("picUserId", new TableInfo.Column("picUserId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPermintaanSparepart.put("jumlah", new TableInfo.Column("jumlah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPermintaanSparepart.put("tanggal", new TableInfo.Column("tanggal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPermintaanSparepart.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPermintaanSparepart.put("keterangan", new TableInfo.Column("keterangan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPermintaanSparepart = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPermintaanSparepart = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPermintaanSparepart = new TableInfo("permintaan_sparepart", _columnsPermintaanSparepart, _foreignKeysPermintaanSparepart, _indicesPermintaanSparepart);
        final TableInfo _existingPermintaanSparepart = TableInfo.read(db, "permintaan_sparepart");
        if (!_infoPermintaanSparepart.equals(_existingPermintaanSparepart)) {
          return new RoomOpenHelper.ValidationResult(false, "permintaan_sparepart(com.utb.inventoryapp.data.local.entity.PermintaanSparepart).\n"
                  + " Expected:\n" + _infoPermintaanSparepart + "\n"
                  + " Found:\n" + _existingPermintaanSparepart);
        }
        final HashMap<String, TableInfo.Column> _columnsBarangRusak = new HashMap<String, TableInfo.Column>(5);
        _columnsBarangRusak.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarangRusak.put("barangId", new TableInfo.Column("barangId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarangRusak.put("jumlah", new TableInfo.Column("jumlah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarangRusak.put("tanggal", new TableInfo.Column("tanggal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarangRusak.put("keterangan", new TableInfo.Column("keterangan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBarangRusak = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBarangRusak = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBarangRusak = new TableInfo("barang_rusak", _columnsBarangRusak, _foreignKeysBarangRusak, _indicesBarangRusak);
        final TableInfo _existingBarangRusak = TableInfo.read(db, "barang_rusak");
        if (!_infoBarangRusak.equals(_existingBarangRusak)) {
          return new RoomOpenHelper.ValidationResult(false, "barang_rusak(com.utb.inventoryapp.data.local.entity.BarangRusak).\n"
                  + " Expected:\n" + _infoBarangRusak + "\n"
                  + " Found:\n" + _existingBarangRusak);
        }
        final HashMap<String, TableInfo.Column> _columnsBarangHilang = new HashMap<String, TableInfo.Column>(5);
        _columnsBarangHilang.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarangHilang.put("barangId", new TableInfo.Column("barangId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarangHilang.put("jumlah", new TableInfo.Column("jumlah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarangHilang.put("tanggal", new TableInfo.Column("tanggal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBarangHilang.put("keterangan", new TableInfo.Column("keterangan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBarangHilang = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBarangHilang = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBarangHilang = new TableInfo("barang_hilang", _columnsBarangHilang, _foreignKeysBarangHilang, _indicesBarangHilang);
        final TableInfo _existingBarangHilang = TableInfo.read(db, "barang_hilang");
        if (!_infoBarangHilang.equals(_existingBarangHilang)) {
          return new RoomOpenHelper.ValidationResult(false, "barang_hilang(com.utb.inventoryapp.data.local.entity.BarangHilang).\n"
                  + " Expected:\n" + _infoBarangHilang + "\n"
                  + " Found:\n" + _existingBarangHilang);
        }
        final HashMap<String, TableInfo.Column> _columnsRiwayat = new HashMap<String, TableInfo.Column>(5);
        _columnsRiwayat.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRiwayat.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRiwayat.put("aktivitas", new TableInfo.Column("aktivitas", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRiwayat.put("tanggal", new TableInfo.Column("tanggal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRiwayat.put("keterangan", new TableInfo.Column("keterangan", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRiwayat = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRiwayat = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRiwayat = new TableInfo("riwayat", _columnsRiwayat, _foreignKeysRiwayat, _indicesRiwayat);
        final TableInfo _existingRiwayat = TableInfo.read(db, "riwayat");
        if (!_infoRiwayat.equals(_existingRiwayat)) {
          return new RoomOpenHelper.ValidationResult(false, "riwayat(com.utb.inventoryapp.data.local.entity.Riwayat).\n"
                  + " Expected:\n" + _infoRiwayat + "\n"
                  + " Found:\n" + _existingRiwayat);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "37fed9f5675b0f9ebe4161e2ebcc67d5", "6483f308f9de2fed8eb2435dcb3b88c0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "kategori","satuan","jenis_barang","lokasi","supplier","user","barang","peminjaman","pengembalian","permintaan_sparepart","barang_rusak","barang_hilang","riwayat");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `kategori`");
      _db.execSQL("DELETE FROM `satuan`");
      _db.execSQL("DELETE FROM `jenis_barang`");
      _db.execSQL("DELETE FROM `lokasi`");
      _db.execSQL("DELETE FROM `supplier`");
      _db.execSQL("DELETE FROM `user`");
      _db.execSQL("DELETE FROM `barang`");
      _db.execSQL("DELETE FROM `peminjaman`");
      _db.execSQL("DELETE FROM `pengembalian`");
      _db.execSQL("DELETE FROM `permintaan_sparepart`");
      _db.execSQL("DELETE FROM `barang_rusak`");
      _db.execSQL("DELETE FROM `barang_hilang`");
      _db.execSQL("DELETE FROM `riwayat`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(KategoriDao.class, KategoriDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SatuanDao.class, SatuanDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(JenisBarangDao.class, JenisBarangDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LokasiDao.class, LokasiDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SupplierDao.class, SupplierDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BarangDao.class, BarangDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PeminjamanDao.class, PeminjamanDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PengembalianDao.class, PengembalianDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PermintaanSparepartDao.class, PermintaanSparepartDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BarangRusakDao.class, BarangRusakDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BarangHilangDao.class, BarangHilangDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RiwayatDao.class, RiwayatDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public KategoriDao kategoriDao() {
    if (_kategoriDao != null) {
      return _kategoriDao;
    } else {
      synchronized(this) {
        if(_kategoriDao == null) {
          _kategoriDao = new KategoriDao_Impl(this);
        }
        return _kategoriDao;
      }
    }
  }

  @Override
  public SatuanDao satuanDao() {
    if (_satuanDao != null) {
      return _satuanDao;
    } else {
      synchronized(this) {
        if(_satuanDao == null) {
          _satuanDao = new SatuanDao_Impl(this);
        }
        return _satuanDao;
      }
    }
  }

  @Override
  public JenisBarangDao jenisBarangDao() {
    if (_jenisBarangDao != null) {
      return _jenisBarangDao;
    } else {
      synchronized(this) {
        if(_jenisBarangDao == null) {
          _jenisBarangDao = new JenisBarangDao_Impl(this);
        }
        return _jenisBarangDao;
      }
    }
  }

  @Override
  public LokasiDao lokasiDao() {
    if (_lokasiDao != null) {
      return _lokasiDao;
    } else {
      synchronized(this) {
        if(_lokasiDao == null) {
          _lokasiDao = new LokasiDao_Impl(this);
        }
        return _lokasiDao;
      }
    }
  }

  @Override
  public SupplierDao supplierDao() {
    if (_supplierDao != null) {
      return _supplierDao;
    } else {
      synchronized(this) {
        if(_supplierDao == null) {
          _supplierDao = new SupplierDao_Impl(this);
        }
        return _supplierDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public BarangDao barangDao() {
    if (_barangDao != null) {
      return _barangDao;
    } else {
      synchronized(this) {
        if(_barangDao == null) {
          _barangDao = new BarangDao_Impl(this);
        }
        return _barangDao;
      }
    }
  }

  @Override
  public PeminjamanDao peminjamanDao() {
    if (_peminjamanDao != null) {
      return _peminjamanDao;
    } else {
      synchronized(this) {
        if(_peminjamanDao == null) {
          _peminjamanDao = new PeminjamanDao_Impl(this);
        }
        return _peminjamanDao;
      }
    }
  }

  @Override
  public PengembalianDao pengembalianDao() {
    if (_pengembalianDao != null) {
      return _pengembalianDao;
    } else {
      synchronized(this) {
        if(_pengembalianDao == null) {
          _pengembalianDao = new PengembalianDao_Impl(this);
        }
        return _pengembalianDao;
      }
    }
  }

  @Override
  public PermintaanSparepartDao permintaanSparepartDao() {
    if (_permintaanSparepartDao != null) {
      return _permintaanSparepartDao;
    } else {
      synchronized(this) {
        if(_permintaanSparepartDao == null) {
          _permintaanSparepartDao = new PermintaanSparepartDao_Impl(this);
        }
        return _permintaanSparepartDao;
      }
    }
  }

  @Override
  public BarangRusakDao barangRusakDao() {
    if (_barangRusakDao != null) {
      return _barangRusakDao;
    } else {
      synchronized(this) {
        if(_barangRusakDao == null) {
          _barangRusakDao = new BarangRusakDao_Impl(this);
        }
        return _barangRusakDao;
      }
    }
  }

  @Override
  public BarangHilangDao barangHilangDao() {
    if (_barangHilangDao != null) {
      return _barangHilangDao;
    } else {
      synchronized(this) {
        if(_barangHilangDao == null) {
          _barangHilangDao = new BarangHilangDao_Impl(this);
        }
        return _barangHilangDao;
      }
    }
  }

  @Override
  public RiwayatDao riwayatDao() {
    if (_riwayatDao != null) {
      return _riwayatDao;
    } else {
      synchronized(this) {
        if(_riwayatDao == null) {
          _riwayatDao = new RiwayatDao_Impl(this);
        }
        return _riwayatDao;
      }
    }
  }
}
