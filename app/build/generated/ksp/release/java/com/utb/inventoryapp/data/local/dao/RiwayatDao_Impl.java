package com.utb.inventoryapp.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utb.inventoryapp.data.local.entity.Riwayat;
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
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class RiwayatDao_Impl implements RiwayatDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Riwayat> __insertionAdapterOfRiwayat;

  public RiwayatDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRiwayat = new EntityInsertionAdapter<Riwayat>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `riwayat` (`id`,`userId`,`aktivitas`,`tanggal`,`keterangan`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Riwayat entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getAktivitas());
        statement.bindLong(4, entity.getTanggal());
        statement.bindString(5, entity.getKeterangan());
      }
    };
  }

  @Override
  public Object insert(final Riwayat item, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRiwayat.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Riwayat>> getAll() {
    final String _sql = "SELECT * FROM riwayat ORDER BY tanggal DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"riwayat"}, new Callable<List<Riwayat>>() {
      @Override
      @NonNull
      public List<Riwayat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfAktivitas = CursorUtil.getColumnIndexOrThrow(_cursor, "aktivitas");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<Riwayat> _result = new ArrayList<Riwayat>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Riwayat _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final String _tmpAktivitas;
            _tmpAktivitas = _cursor.getString(_cursorIndexOfAktivitas);
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new Riwayat(_tmpId,_tmpUserId,_tmpAktivitas,_tmpTanggal,_tmpKeterangan);
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
  public Flow<List<Riwayat>> getByUser(final long userId) {
    final String _sql = "SELECT * FROM riwayat WHERE userId = ? ORDER BY tanggal DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"riwayat"}, new Callable<List<Riwayat>>() {
      @Override
      @NonNull
      public List<Riwayat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfAktivitas = CursorUtil.getColumnIndexOrThrow(_cursor, "aktivitas");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<Riwayat> _result = new ArrayList<Riwayat>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Riwayat _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final String _tmpAktivitas;
            _tmpAktivitas = _cursor.getString(_cursorIndexOfAktivitas);
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new Riwayat(_tmpId,_tmpUserId,_tmpAktivitas,_tmpTanggal,_tmpKeterangan);
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
    final String _sql = "SELECT COUNT(*) FROM riwayat";
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
