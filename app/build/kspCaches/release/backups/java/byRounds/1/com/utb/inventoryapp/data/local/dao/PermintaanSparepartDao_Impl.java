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
import com.utb.inventoryapp.data.local.entity.PermintaanSparepart;
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
public final class PermintaanSparepartDao_Impl implements PermintaanSparepartDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PermintaanSparepart> __insertionAdapterOfPermintaanSparepart;

  private final EntityDeletionOrUpdateAdapter<PermintaanSparepart> __deletionAdapterOfPermintaanSparepart;

  private final EntityDeletionOrUpdateAdapter<PermintaanSparepart> __updateAdapterOfPermintaanSparepart;

  public PermintaanSparepartDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPermintaanSparepart = new EntityInsertionAdapter<PermintaanSparepart>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `permintaan_sparepart` (`id`,`barangId`,`picUserId`,`jumlah`,`tanggal`,`status`,`keterangan`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PermintaanSparepart entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBarangId());
        statement.bindLong(3, entity.getPicUserId());
        statement.bindLong(4, entity.getJumlah());
        statement.bindLong(5, entity.getTanggal());
        statement.bindString(6, entity.getStatus());
        statement.bindString(7, entity.getKeterangan());
      }
    };
    this.__deletionAdapterOfPermintaanSparepart = new EntityDeletionOrUpdateAdapter<PermintaanSparepart>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `permintaan_sparepart` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PermintaanSparepart entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPermintaanSparepart = new EntityDeletionOrUpdateAdapter<PermintaanSparepart>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `permintaan_sparepart` SET `id` = ?,`barangId` = ?,`picUserId` = ?,`jumlah` = ?,`tanggal` = ?,`status` = ?,`keterangan` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PermintaanSparepart entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBarangId());
        statement.bindLong(3, entity.getPicUserId());
        statement.bindLong(4, entity.getJumlah());
        statement.bindLong(5, entity.getTanggal());
        statement.bindString(6, entity.getStatus());
        statement.bindString(7, entity.getKeterangan());
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final PermintaanSparepart item,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPermintaanSparepart.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PermintaanSparepart item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPermintaanSparepart.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PermintaanSparepart item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPermintaanSparepart.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PermintaanSparepart>> getAll() {
    final String _sql = "SELECT * FROM permintaan_sparepart ORDER BY tanggal DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"permintaan_sparepart"}, new Callable<List<PermintaanSparepart>>() {
      @Override
      @NonNull
      public List<PermintaanSparepart> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfPicUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "picUserId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<PermintaanSparepart> _result = new ArrayList<PermintaanSparepart>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PermintaanSparepart _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final long _tmpPicUserId;
            _tmpPicUserId = _cursor.getLong(_cursorIndexOfPicUserId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new PermintaanSparepart(_tmpId,_tmpBarangId,_tmpPicUserId,_tmpJumlah,_tmpTanggal,_tmpStatus,_tmpKeterangan);
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
  public Flow<List<PermintaanSparepart>> getByUser(final long userId) {
    final String _sql = "SELECT * FROM permintaan_sparepart WHERE picUserId = ? ORDER BY tanggal DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"permintaan_sparepart"}, new Callable<List<PermintaanSparepart>>() {
      @Override
      @NonNull
      public List<PermintaanSparepart> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfPicUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "picUserId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<PermintaanSparepart> _result = new ArrayList<PermintaanSparepart>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PermintaanSparepart _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final long _tmpPicUserId;
            _tmpPicUserId = _cursor.getLong(_cursorIndexOfPicUserId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new PermintaanSparepart(_tmpId,_tmpBarangId,_tmpPicUserId,_tmpJumlah,_tmpTanggal,_tmpStatus,_tmpKeterangan);
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
  public Flow<List<PermintaanSparepart>> getByStatus(final String status) {
    final String _sql = "SELECT * FROM permintaan_sparepart WHERE status = ? ORDER BY tanggal DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, status);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"permintaan_sparepart"}, new Callable<List<PermintaanSparepart>>() {
      @Override
      @NonNull
      public List<PermintaanSparepart> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfPicUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "picUserId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final List<PermintaanSparepart> _result = new ArrayList<PermintaanSparepart>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PermintaanSparepart _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final long _tmpPicUserId;
            _tmpPicUserId = _cursor.getLong(_cursorIndexOfPicUserId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _item = new PermintaanSparepart(_tmpId,_tmpBarangId,_tmpPicUserId,_tmpJumlah,_tmpTanggal,_tmpStatus,_tmpKeterangan);
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
  public Object getById(final long id,
      final Continuation<? super PermintaanSparepart> $completion) {
    final String _sql = "SELECT * FROM permintaan_sparepart WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PermintaanSparepart>() {
      @Override
      @Nullable
      public PermintaanSparepart call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBarangId = CursorUtil.getColumnIndexOrThrow(_cursor, "barangId");
          final int _cursorIndexOfPicUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "picUserId");
          final int _cursorIndexOfJumlah = CursorUtil.getColumnIndexOrThrow(_cursor, "jumlah");
          final int _cursorIndexOfTanggal = CursorUtil.getColumnIndexOrThrow(_cursor, "tanggal");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfKeterangan = CursorUtil.getColumnIndexOrThrow(_cursor, "keterangan");
          final PermintaanSparepart _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBarangId;
            _tmpBarangId = _cursor.getLong(_cursorIndexOfBarangId);
            final long _tmpPicUserId;
            _tmpPicUserId = _cursor.getLong(_cursorIndexOfPicUserId);
            final int _tmpJumlah;
            _tmpJumlah = _cursor.getInt(_cursorIndexOfJumlah);
            final long _tmpTanggal;
            _tmpTanggal = _cursor.getLong(_cursorIndexOfTanggal);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpKeterangan;
            _tmpKeterangan = _cursor.getString(_cursorIndexOfKeterangan);
            _result = new PermintaanSparepart(_tmpId,_tmpBarangId,_tmpPicUserId,_tmpJumlah,_tmpTanggal,_tmpStatus,_tmpKeterangan);
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
    final String _sql = "SELECT COUNT(*) FROM permintaan_sparepart WHERE status = 'PENDING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"permintaan_sparepart"}, new Callable<Integer>() {
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
    final String _sql = "SELECT COUNT(*) FROM permintaan_sparepart";
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
