package com.utb.inventoryapp.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utb.inventoryapp.data.local.entity.Barang;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BarangDao_Impl implements BarangDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Barang> __insertionAdapterOfBarang;

  private final EntityDeletionOrUpdateAdapter<Barang> __deletionAdapterOfBarang;

  private final EntityDeletionOrUpdateAdapter<Barang> __updateAdapterOfBarang;

  private final SharedSQLiteStatement __preparedStmtOfKurangiStok;

  private final SharedSQLiteStatement __preparedStmtOfTambahStok;

  public BarangDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBarang = new EntityInsertionAdapter<Barang>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `barang` (`id`,`kode`,`nama`,`kategoriId`,`jenisBarangId`,`satuanId`,`supplierId`,`lokasiId`,`stok`,`kondisi`,`deskripsi`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Barang entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getKode());
        statement.bindString(3, entity.getNama());
        statement.bindLong(4, entity.getKategoriId());
        statement.bindLong(5, entity.getJenisBarangId());
        statement.bindLong(6, entity.getSatuanId());
        statement.bindLong(7, entity.getSupplierId());
        statement.bindLong(8, entity.getLokasiId());
        statement.bindLong(9, entity.getStok());
        statement.bindString(10, entity.getKondisi());
        statement.bindString(11, entity.getDeskripsi());
      }
    };
    this.__deletionAdapterOfBarang = new EntityDeletionOrUpdateAdapter<Barang>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `barang` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Barang entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBarang = new EntityDeletionOrUpdateAdapter<Barang>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `barang` SET `id` = ?,`kode` = ?,`nama` = ?,`kategoriId` = ?,`jenisBarangId` = ?,`satuanId` = ?,`supplierId` = ?,`lokasiId` = ?,`stok` = ?,`kondisi` = ?,`deskripsi` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Barang entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getKode());
        statement.bindString(3, entity.getNama());
        statement.bindLong(4, entity.getKategoriId());
        statement.bindLong(5, entity.getJenisBarangId());
        statement.bindLong(6, entity.getSatuanId());
        statement.bindLong(7, entity.getSupplierId());
        statement.bindLong(8, entity.getLokasiId());
        statement.bindLong(9, entity.getStok());
        statement.bindString(10, entity.getKondisi());
        statement.bindString(11, entity.getDeskripsi());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfKurangiStok = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE barang SET stok = stok - ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfTambahStok = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE barang SET stok = stok + ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Barang item, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBarang.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Barang item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBarang.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Barang item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBarang.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object kurangiStok(final long id, final int jumlah,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfKurangiStok.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, jumlah);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfKurangiStok.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object tambahStok(final long id, final int jumlah,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfTambahStok.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, jumlah);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfTambahStok.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Barang>> getAll() {
    final String _sql = "SELECT * FROM barang ORDER BY nama ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"barang"}, new Callable<List<Barang>>() {
      @Override
      @NonNull
      public List<Barang> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfKode = CursorUtil.getColumnIndexOrThrow(_cursor, "kode");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfKategoriId = CursorUtil.getColumnIndexOrThrow(_cursor, "kategoriId");
          final int _cursorIndexOfJenisBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisBarangId");
          final int _cursorIndexOfSatuanId = CursorUtil.getColumnIndexOrThrow(_cursor, "satuanId");
          final int _cursorIndexOfSupplierId = CursorUtil.getColumnIndexOrThrow(_cursor, "supplierId");
          final int _cursorIndexOfLokasiId = CursorUtil.getColumnIndexOrThrow(_cursor, "lokasiId");
          final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
          final int _cursorIndexOfKondisi = CursorUtil.getColumnIndexOrThrow(_cursor, "kondisi");
          final int _cursorIndexOfDeskripsi = CursorUtil.getColumnIndexOrThrow(_cursor, "deskripsi");
          final List<Barang> _result = new ArrayList<Barang>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Barang _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpKode;
            _tmpKode = _cursor.getString(_cursorIndexOfKode);
            final String _tmpNama;
            _tmpNama = _cursor.getString(_cursorIndexOfNama);
            final long _tmpKategoriId;
            _tmpKategoriId = _cursor.getLong(_cursorIndexOfKategoriId);
            final long _tmpJenisBarangId;
            _tmpJenisBarangId = _cursor.getLong(_cursorIndexOfJenisBarangId);
            final long _tmpSatuanId;
            _tmpSatuanId = _cursor.getLong(_cursorIndexOfSatuanId);
            final long _tmpSupplierId;
            _tmpSupplierId = _cursor.getLong(_cursorIndexOfSupplierId);
            final long _tmpLokasiId;
            _tmpLokasiId = _cursor.getLong(_cursorIndexOfLokasiId);
            final int _tmpStok;
            _tmpStok = _cursor.getInt(_cursorIndexOfStok);
            final String _tmpKondisi;
            _tmpKondisi = _cursor.getString(_cursorIndexOfKondisi);
            final String _tmpDeskripsi;
            _tmpDeskripsi = _cursor.getString(_cursorIndexOfDeskripsi);
            _item = new Barang(_tmpId,_tmpKode,_tmpNama,_tmpKategoriId,_tmpJenisBarangId,_tmpSatuanId,_tmpSupplierId,_tmpLokasiId,_tmpStok,_tmpKondisi,_tmpDeskripsi);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Barang>> search(final String query) {
    final String _sql = "SELECT * FROM barang WHERE nama LIKE '%' || ? || '%' OR kode LIKE '%' || ? || '%' ORDER BY nama ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"barang"}, new Callable<List<Barang>>() {
      @Override
      @NonNull
      public List<Barang> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfKode = CursorUtil.getColumnIndexOrThrow(_cursor, "kode");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfKategoriId = CursorUtil.getColumnIndexOrThrow(_cursor, "kategoriId");
          final int _cursorIndexOfJenisBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisBarangId");
          final int _cursorIndexOfSatuanId = CursorUtil.getColumnIndexOrThrow(_cursor, "satuanId");
          final int _cursorIndexOfSupplierId = CursorUtil.getColumnIndexOrThrow(_cursor, "supplierId");
          final int _cursorIndexOfLokasiId = CursorUtil.getColumnIndexOrThrow(_cursor, "lokasiId");
          final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
          final int _cursorIndexOfKondisi = CursorUtil.getColumnIndexOrThrow(_cursor, "kondisi");
          final int _cursorIndexOfDeskripsi = CursorUtil.getColumnIndexOrThrow(_cursor, "deskripsi");
          final List<Barang> _result = new ArrayList<Barang>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Barang _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpKode;
            _tmpKode = _cursor.getString(_cursorIndexOfKode);
            final String _tmpNama;
            _tmpNama = _cursor.getString(_cursorIndexOfNama);
            final long _tmpKategoriId;
            _tmpKategoriId = _cursor.getLong(_cursorIndexOfKategoriId);
            final long _tmpJenisBarangId;
            _tmpJenisBarangId = _cursor.getLong(_cursorIndexOfJenisBarangId);
            final long _tmpSatuanId;
            _tmpSatuanId = _cursor.getLong(_cursorIndexOfSatuanId);
            final long _tmpSupplierId;
            _tmpSupplierId = _cursor.getLong(_cursorIndexOfSupplierId);
            final long _tmpLokasiId;
            _tmpLokasiId = _cursor.getLong(_cursorIndexOfLokasiId);
            final int _tmpStok;
            _tmpStok = _cursor.getInt(_cursorIndexOfStok);
            final String _tmpKondisi;
            _tmpKondisi = _cursor.getString(_cursorIndexOfKondisi);
            final String _tmpDeskripsi;
            _tmpDeskripsi = _cursor.getString(_cursorIndexOfDeskripsi);
            _item = new Barang(_tmpId,_tmpKode,_tmpNama,_tmpKategoriId,_tmpJenisBarangId,_tmpSatuanId,_tmpSupplierId,_tmpLokasiId,_tmpStok,_tmpKondisi,_tmpDeskripsi);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final long id, final Continuation<? super Barang> $completion) {
    final String _sql = "SELECT * FROM barang WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Barang>() {
      @Override
      @Nullable
      public Barang call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfKode = CursorUtil.getColumnIndexOrThrow(_cursor, "kode");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfKategoriId = CursorUtil.getColumnIndexOrThrow(_cursor, "kategoriId");
          final int _cursorIndexOfJenisBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "jenisBarangId");
          final int _cursorIndexOfSatuanId = CursorUtil.getColumnIndexOrThrow(_cursor, "satuanId");
          final int _cursorIndexOfSupplierId = CursorUtil.getColumnIndexOrThrow(_cursor, "supplierId");
          final int _cursorIndexOfLokasiId = CursorUtil.getColumnIndexOrThrow(_cursor, "lokasiId");
          final int _cursorIndexOfStok = CursorUtil.getColumnIndexOrThrow(_cursor, "stok");
          final int _cursorIndexOfKondisi = CursorUtil.getColumnIndexOrThrow(_cursor, "kondisi");
          final int _cursorIndexOfDeskripsi = CursorUtil.getColumnIndexOrThrow(_cursor, "deskripsi");
          final Barang _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpKode;
            _tmpKode = _cursor.getString(_cursorIndexOfKode);
            final String _tmpNama;
            _tmpNama = _cursor.getString(_cursorIndexOfNama);
            final long _tmpKategoriId;
            _tmpKategoriId = _cursor.getLong(_cursorIndexOfKategoriId);
            final long _tmpJenisBarangId;
            _tmpJenisBarangId = _cursor.getLong(_cursorIndexOfJenisBarangId);
            final long _tmpSatuanId;
            _tmpSatuanId = _cursor.getLong(_cursorIndexOfSatuanId);
            final long _tmpSupplierId;
            _tmpSupplierId = _cursor.getLong(_cursorIndexOfSupplierId);
            final long _tmpLokasiId;
            _tmpLokasiId = _cursor.getLong(_cursorIndexOfLokasiId);
            final int _tmpStok;
            _tmpStok = _cursor.getInt(_cursorIndexOfStok);
            final String _tmpKondisi;
            _tmpKondisi = _cursor.getString(_cursorIndexOfKondisi);
            final String _tmpDeskripsi;
            _tmpDeskripsi = _cursor.getString(_cursorIndexOfDeskripsi);
            _result = new Barang(_tmpId,_tmpKode,_tmpNama,_tmpKategoriId,_tmpJenisBarangId,_tmpSatuanId,_tmpSupplierId,_tmpLokasiId,_tmpStok,_tmpKondisi,_tmpDeskripsi);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM barang";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> countRusak() {
    final String _sql = "SELECT COUNT(*) FROM barang WHERE kondisi = 'RUSAK'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"barang"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> countHilang() {
    final String _sql = "SELECT COUNT(*) FROM barang WHERE kondisi = 'HILANG'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"barang"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
