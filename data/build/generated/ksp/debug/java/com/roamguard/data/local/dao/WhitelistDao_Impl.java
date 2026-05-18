package com.roamguard.data.local.dao;

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
import com.roamguard.data.local.entity.WhitelistCountryEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class WhitelistDao_Impl implements WhitelistDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WhitelistCountryEntity> __insertionAdapterOfWhitelistCountryEntity;

  private final EntityDeletionOrUpdateAdapter<WhitelistCountryEntity> __deletionAdapterOfWhitelistCountryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByMcc;

  public WhitelistDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWhitelistCountryEntity = new EntityInsertionAdapter<WhitelistCountryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `whitelist_country` (`id`,`mcc`,`country_name`,`country_code`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WhitelistCountryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMcc());
        statement.bindString(3, entity.getCountryName());
        statement.bindString(4, entity.getCountryCode());
      }
    };
    this.__deletionAdapterOfWhitelistCountryEntity = new EntityDeletionOrUpdateAdapter<WhitelistCountryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `whitelist_country` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WhitelistCountryEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByMcc = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM whitelist_country WHERE mcc = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final WhitelistCountryEntity country,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWhitelistCountryEntity.insert(country);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<WhitelistCountryEntity> countries,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWhitelistCountryEntity.insert(countries);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final WhitelistCountryEntity country,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWhitelistCountryEntity.handle(country);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByMcc(final int mcc, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByMcc.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, mcc);
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
          __preparedStmtOfDeleteByMcc.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WhitelistCountryEntity>> getAll() {
    final String _sql = "SELECT * FROM whitelist_country ORDER BY country_name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"whitelist_country"}, new Callable<List<WhitelistCountryEntity>>() {
      @Override
      @NonNull
      public List<WhitelistCountryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMcc = CursorUtil.getColumnIndexOrThrow(_cursor, "mcc");
          final int _cursorIndexOfCountryName = CursorUtil.getColumnIndexOrThrow(_cursor, "country_name");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "country_code");
          final List<WhitelistCountryEntity> _result = new ArrayList<WhitelistCountryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WhitelistCountryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMcc;
            _tmpMcc = _cursor.getInt(_cursorIndexOfMcc);
            final String _tmpCountryName;
            _tmpCountryName = _cursor.getString(_cursorIndexOfCountryName);
            final String _tmpCountryCode;
            _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            _item = new WhitelistCountryEntity(_tmpId,_tmpMcc,_tmpCountryName,_tmpCountryCode);
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
  public Object getByMcc(final int mcc,
      final Continuation<? super WhitelistCountryEntity> $completion) {
    final String _sql = "SELECT * FROM whitelist_country WHERE mcc = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, mcc);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WhitelistCountryEntity>() {
      @Override
      @Nullable
      public WhitelistCountryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMcc = CursorUtil.getColumnIndexOrThrow(_cursor, "mcc");
          final int _cursorIndexOfCountryName = CursorUtil.getColumnIndexOrThrow(_cursor, "country_name");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "country_code");
          final WhitelistCountryEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMcc;
            _tmpMcc = _cursor.getInt(_cursorIndexOfMcc);
            final String _tmpCountryName;
            _tmpCountryName = _cursor.getString(_cursorIndexOfCountryName);
            final String _tmpCountryCode;
            _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            _result = new WhitelistCountryEntity(_tmpId,_tmpMcc,_tmpCountryName,_tmpCountryCode);
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
  public Object exists(final int mcc, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM whitelist_country WHERE mcc = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, mcc);
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
