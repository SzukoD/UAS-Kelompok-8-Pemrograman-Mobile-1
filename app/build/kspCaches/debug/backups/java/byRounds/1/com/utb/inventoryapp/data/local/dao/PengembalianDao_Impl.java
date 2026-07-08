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
import com.utb.inventoryapp.data.local.entity.Pengembalian;
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
public final class PengembalianDao_Impl implements PengembalianDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Pengembalian> __insertionAdapterOfPengembalian;

  private final EntityDeletionOrUpdateAdapter<Pengembalian> __deletionAdapterOfPengembalian;

  private final EntityDeletionOrUpdateAdapter<Pengembalian> __updateAdapterOfPengembalian;

  public PengembalianDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPengembalian = new EntityInsertionAdapter<Pengembalian>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `pengembalian` (`id`,`peminjamanId`,`tanggalKembali`,`kondisiKembali`,`keterangan`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pengembalian entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPeminjamanId());
        statement.bindLong(3, entity.getTanggalKembali());
        statement.bindString(4, entity.getKondisiKembali());
        statement.bindString(5, entity.getKeterangan());
      }
    };
    this.__deletionAdapterOfPengembalian = new EntityDeletionOrUpdateAdapter<Pengembalian>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `pengembalian` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pengembalian entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPengembalian = new EntityDeletionOrUpdateAdapter<Pengembalian>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `pengembalian` SET `id` = ?,`peminjamanId` = ?,`tanggalKembali` = ?,`kondisiKembali` = ?,`keterangan` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Pengembalian entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getPeminjamanId());
        statement.bindLong(3, entity.getTanggalKembali());
        statement.bindString(4, entity.getKondisiKembali());
        statement.bindString(5, entity.getKeterangan());
        statement.bindLong(6, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Pengembalian item, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPengembalian.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Pengembalian item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPengembalian.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Pengembalian item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPengembalian.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Pengembalian>> getAll() {
    final String _sql = "SELECT * FROM pengembalian ORDER BY tanggalKembali DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"pengembalian"}, new Callable<List<Pengembalian>>() {
      @Override
      @NonNull
      public List<Pengembalian> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPeminjamanId = CursorUtil.getColumnIndexOrThrow(_cursor, "peminjamanId");
          final int _cursorIndexOfTanggalKembali = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalKembali");
          final int _cursorIndexOfKondisiKembali = CursorUtil.getColumnIndexOrThrow(_cursor, "kondisiKembali");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<Pengembalian> _result = new ArrayList<Pengembalian>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Pengembalian _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPeminjamanId;
            _tmpPeminjamanId = _cursor.getLong(_cursorIndexOfPeminjamanId);
            final long _tmpTanggalKembali;
            _tmpTanggalKembali = _cursor.getLong(_cursorIndexOfTanggalKembali);
            final String _tmpKondisiKembali;
            _tmpKondisiKembali = _cursor.getString(_cursorIndexOfKondisiKembali);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new Pengembalian(_tmpId,_tmpPeminjamanId,_tmpTanggalKembali,_tmpKondisiKembali,_tmpKeterangan);
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
  public Object getByPeminjaman(final long peminjamanId,
      final Continuation<? super Pengembalian> $completion) {
    final String _sql = "SELECT * FROM pengembalian WHERE peminjamanId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, peminjamanId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Pengembalian>() {
      @Override
      @Nullable
      public Pengembalian call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPeminjamanId = CursorUtil.getColumnIndexOrThrow(_cursor, "peminjamanId");
          final int _cursorIndexOfTanggalKembali = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggalKembali");
          final int _cursorIndexOfKondisiKembali = CursorUtil.getColumnIndexOrThrow(_cursor, "kondisiKembali");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final Pengembalian _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpPeminjamanId;
            _tmpPeminjamanId = _cursor.getLong(_cursorIndexOfPeminjamanId);
            final long _tmpTanggalKembali;
            _tmpTanggalKembali = _cursor.getLong(_cursorIndexOfTanggalKembali);
            final String _tmpKondisiKembali;
            _tmpKondisiKembali = _cursor.getString(_cursorIndexOfKondisiKembali);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _result = new Pengembalian(_tmpId,_tmpPeminjamanId,_tmpTanggalKembali,_tmpKondisiKembali,_tmpKeterangan);
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
    final String _sql = "SELECT COUNT(*) FROM pengembalian";
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
