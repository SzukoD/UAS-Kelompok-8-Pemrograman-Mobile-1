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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utb.inventoryapp.data.local.entity.Peminjaman;
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
public final class PeminjamanDao_Impl implements PeminjamanDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Peminjaman> __insertionAdapterOfPeminjaman;

  private final EntityDeletionOrUpdateAdapter<Peminjaman> __deletionAdapterOfPeminjaman;

  private final EntityDeletionOrUpdateAdapter<Peminjaman> __updateAdapterOfPeminjaman;

  public PeminjamanDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPeminjaman = new EntityInsertionAdapter<Peminjaman>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `peminjaman` (`id`,`barangId`,`picUserId`,`jumlah`,`tanggalPinjam`,`tanggalRencanaKembali`,`status`,`keterangan`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Peminjaman entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBarangId());
        statement.bindLong(3, entity.getPicUserId());
        statement.bindLong(4, entity.getJumlah());
        statement.bindLong(5, entity.getTanggalPinjam());
        statement.bindLong(6, entity.getTanggalRencanaKembali());
        statement.bindString(7, entity.getStatus());
        statement.bindString(8, entity.getKeterangan());
      }
    };
    this.__deletionAdapterOfPeminjaman = new EntityDeletionOrUpdateAdapter<Peminjaman>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `peminjaman` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Peminjaman entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPeminjaman = new EntityDeletionOrUpdateAdapter<Peminjaman>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `peminjaman` SET `id` = ?,`barangId` = ?,`picUserId` = ?,`jumlah` = ?,`tanggalPinjam` = ?,`tanggalRencanaKembali` = ?,`status` = ?,`keterangan` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Peminjaman entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBarangId());
        statement.bindLong(3, entity.getPicUserId());
        statement.bindLong(4, entity.getJumlah());
        statement.bindLong(5, entity.getTanggalPinjam());
        statement.bindLong(6, entity.getTanggalRencanaKembali());
        statement.bindString(7, entity.getStatus());
        statement.bindString(8, entity.getKeterangan());
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Peminjaman item, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPeminjaman.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Peminjaman item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPeminjaman.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Peminjaman item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPeminjaman.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Peminjaman>> getAll() {
    final String _sql = "SELECT * FROM peminjaman ORDER BY tanggalPinjam DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"peminjaman"}, new Callable<List<Peminjaman>>() {
      @Override
      @NonNull
      public List<Peminjaman> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfPicUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "picUserId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggalPinjam = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalPinjam");
          final int _cursorIndexOfTanggalRencanaKembali = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalRencanaKembali");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<Peminjaman> _result = new ArrayList<Peminjaman>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Peminjaman _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final long _tmpPicUserId;
            _tmpPicUserId = _cursor.getLong(_cursorIndexOfPicUserId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggalPinjam;
            _tmpTanggalPinjam = _cursor.getLong(_cursorIndexOfTanggalPinjam);
            final long _tmpTanggalRencanaKembali;
            _tmpTanggalRencanaKembali = _cursor.getLong(_cursorIndexOfTanggalRencanaKembali);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new Peminjaman(_tmpId,_tmpBarangId,_tmpPicUserId,_tmpJumlah,_tmpTanggalPinjam,_tmpTanggalRencanaKembali,_tmpStatus,_tmpKeterangan);
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
  public Flow<List<Peminjaman>> getByUser(final long userId) {
    final String _sql = "SELECT * FROM peminjaman WHERE picUserId = ? ORDER BY tanggalPinjam DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"peminjaman"}, new Callable<List<Peminjaman>>() {
      @Override
      @NonNull
      public List<Peminjaman> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfPicUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "picUserId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggalPinjam = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalPinjam");
          final int _cursorIndexOfTanggalRencanaKembali = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalRencanaKembali");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<Peminjaman> _result = new ArrayList<Peminjaman>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Peminjaman _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final long _tmpPicUserId;
            _tmpPicUserId = _cursor.getLong(_cursorIndexOfPicUserId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggalPinjam;
            _tmpTanggalPinjam = _cursor.getLong(_cursorIndexOfTanggalPinjam);
            final long _tmpTanggalRencanaKembali;
            _tmpTanggalRencanaKembali = _cursor.getLong(_cursorIndexOfTanggalRencanaKembali);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new Peminjaman(_tmpId,_tmpBarangId,_tmpPicUserId,_tmpJumlah,_tmpTanggalPinjam,_tmpTanggalRencanaKembali,_tmpStatus,_tmpKeterangan);
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
  public Flow<List<Peminjaman>> getByStatus(final String status) {
    final String _sql = "SELECT * FROM peminjaman WHERE status = ? ORDER BY tanggalPinjam DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, status);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"peminjaman"}, new Callable<List<Peminjaman>>() {
      @Override
      @NonNull
      public List<Peminjaman> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfPicUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "picUserId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggalPinjam = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalPinjam");
          final int _cursorIndexOfTanggalRencanaKembali = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalRencanaKembali");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<Peminjaman> _result = new ArrayList<Peminjaman>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Peminjaman _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final long _tmpPicUserId;
            _tmpPicUserId = _cursor.getLong(_cursorIndexOfPicUserId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggalPinjam;
            _tmpTanggalPinjam = _cursor.getLong(_cursorIndexOfTanggalPinjam);
            final long _tmpTanggalRencanaKembali;
            _tmpTanggalRencanaKembali = _cursor.getLong(_cursorIndexOfTanggalRencanaKembali);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new Peminjaman(_tmpId,_tmpBarangId,_tmpPicUserId,_tmpJumlah,_tmpTanggalPinjam,_tmpTanggalRencanaKembali,_tmpStatus,_tmpKeterangan);
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
  public Object getById(final long id, final Continuation<? super Peminjaman> $completion) {
    final String _sql = "SELECT * FROM peminjaman WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Peminjaman>() {
      @Override
      @Nullable
      public Peminjaman call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfPicUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "picUserId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggalPinjam = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalPinjam");
          final int _cursorIndexOfTanggalRencanaKembali = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalRencanaKembali");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final Peminjaman _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final long _tmpPicUserId;
            _tmpPicUserId = _cursor.getLong(_cursorIndexOfPicUserId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggalPinjam;
            _tmpTanggalPinjam = _cursor.getLong(_cursorIndexOfTanggalPinjam);
            final long _tmpTanggalRencanaKembali;
            _tmpTanggalRencanaKembali = _cursor.getLong(_cursorIndexOfTanggalRencanaKembali);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _result = new Peminjaman(_tmpId,_tmpBarangId,_tmpPicUserId,_tmpJumlah,_tmpTanggalPinjam,_tmpTanggalRencanaKembali,_tmpStatus,_tmpKeterangan);
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
  public Flow<Integer> countPending() {
    final String _sql = "SELECT COUNT(*) FROM peminjaman WHERE status = 'PENDING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"peminjaman"}, new Callable<Integer>() {
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
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM peminjaman";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
