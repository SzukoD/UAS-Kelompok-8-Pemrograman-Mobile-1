package com.utb.inventoryapp.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utb.inventoryapp.data.local.entity.BarangHilang;
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
public final class BarangHilangDao_Impl implements BarangHilangDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BarangHilang> __insertionAdapterOfBarangHilang;

  private final EntityDeletionOrUpdateAdapter<BarangHilang> __deletionAdapterOfBarangHilang;

  public BarangHilangDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBarangHilang = new EntityInsertionAdapter<BarangHilang>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `barang_hilang` (`id`,`barangId`,`jumlah`,`tanggal`,`keterangan`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BarangHilang entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBarangId());
        statement.bindLong(3, entity.getJumlah());
        statement.bindLong(4, entity.getTanggal());
        statement.bindString(5, entity.getKeterangan());
      }
    };
    this.__deletionAdapterOfBarangHilang = new EntityDeletionOrUpdateAdapter<BarangHilang>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `barang_hilang` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BarangHilang entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final BarangHilang item, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBarangHilang.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final BarangHilang item, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBarangHilang.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BarangHilang>> getAll() {
    final String _sql = "SELECT * FROM barang_hilang ORDER BY tanggal DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"barang_hilang"}, new Callable<List<BarangHilang>>() {
      @Override
      @NonNull
      public List<BarangHilang> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<BarangHilang> _result = new ArrayList<BarangHilang>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BarangHilang _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new BarangHilang(_tmpId,_tmpBarangId,_tmpJumlah,_tmpTanggal,_tmpKeterangan);
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
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM barang_hilang";
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
