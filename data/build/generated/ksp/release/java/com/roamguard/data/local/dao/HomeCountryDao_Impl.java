package com.roamguard.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.roamguard.data.local.entity.HomeCountryEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class HomeCountryDao_Impl implements HomeCountryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HomeCountryEntity> __insertionAdapterOfHomeCountryEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearHomeCountry;

  public HomeCountryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHomeCountryEntity = new EntityInsertionAdapter<HomeCountryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `home_country` (`id`,`mcc`,`country_name`,`country_code`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HomeCountryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMcc());
        statement.bindString(3, entity.getCountryName());
        statement.bindString(4, entity.getCountryCode());
      }
    };
    this.__preparedStmtOfClearHomeCountry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM home_country";
        return _query;
      }
    };
  }

  @Override
  public Object doInsert(final HomeCountryEntity country,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfHomeCountryEntity.insert(country);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setHomeCountry(final HomeCountryEntity country,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> HomeCountryDao.DefaultImpls.setHomeCountry(HomeCountryDao_Impl.this, country, __cont), $completion);
  }

  @Override
  public Object clearHomeCountry(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearHomeCountry.acquire();
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
          __preparedStmtOfClearHomeCountry.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<HomeCountryEntity> getHomeCountry() {
    final String _sql = "SELECT * FROM home_country LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"home_country"}, new Callable<HomeCountryEntity>() {
      @Override
      @Nullable
      public HomeCountryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMcc = CursorUtil.getColumnIndexOrThrow(_cursor, "mcc");
          final int _cursorIndexOfCountryName = CursorUtil.getColumnIndexOrThrow(_cursor, "country_name");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "country_code");
          final HomeCountryEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMcc;
            _tmpMcc = _cursor.getInt(_cursorIndexOfMcc);
            final String _tmpCountryName;
            _tmpCountryName = _cursor.getString(_cursorIndexOfCountryName);
            final String _tmpCountryCode;
            _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            _result = new HomeCountryEntity(_tmpId,_tmpMcc,_tmpCountryName,_tmpCountryCode);
          } else {
            _result = null;
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
