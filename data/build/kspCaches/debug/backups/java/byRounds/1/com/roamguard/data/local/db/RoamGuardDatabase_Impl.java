package com.roamguard.data.local.db;

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
import com.roamguard.data.local.dao.HomeCountryDao;
import com.roamguard.data.local.dao.HomeCountryDao_Impl;
import com.roamguard.data.local.dao.WhitelistDao;
import com.roamguard.data.local.dao.WhitelistDao_Impl;
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
public final class RoamGuardDatabase_Impl extends RoamGuardDatabase {
  private volatile HomeCountryDao _homeCountryDao;

  private volatile WhitelistDao _whitelistDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `home_country` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mcc` INTEGER NOT NULL, `country_name` TEXT NOT NULL, `country_code` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `whitelist_country` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mcc` INTEGER NOT NULL, `country_name` TEXT NOT NULL, `country_code` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3f9b0eabbbf2efb5b34de5724e2cdf21')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `home_country`");
        db.execSQL("DROP TABLE IF EXISTS `whitelist_country`");
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
        final HashMap<String, TableInfo.Column> _columnsHomeCountry = new HashMap<String, TableInfo.Column>(4);
        _columnsHomeCountry.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeCountry.put("mcc", new TableInfo.Column("mcc", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeCountry.put("country_name", new TableInfo.Column("country_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHomeCountry.put("country_code", new TableInfo.Column("country_code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHomeCountry = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHomeCountry = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHomeCountry = new TableInfo("home_country", _columnsHomeCountry, _foreignKeysHomeCountry, _indicesHomeCountry);
        final TableInfo _existingHomeCountry = TableInfo.read(db, "home_country");
        if (!_infoHomeCountry.equals(_existingHomeCountry)) {
          return new RoomOpenHelper.ValidationResult(false, "home_country(com.roamguard.data.local.entity.HomeCountryEntity).\n"
                  + " Expected:\n" + _infoHomeCountry + "\n"
                  + " Found:\n" + _existingHomeCountry);
        }
        final HashMap<String, TableInfo.Column> _columnsWhitelistCountry = new HashMap<String, TableInfo.Column>(4);
        _columnsWhitelistCountry.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWhitelistCountry.put("mcc", new TableInfo.Column("mcc", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWhitelistCountry.put("country_name", new TableInfo.Column("country_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWhitelistCountry.put("country_code", new TableInfo.Column("country_code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWhitelistCountry = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWhitelistCountry = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWhitelistCountry = new TableInfo("whitelist_country", _columnsWhitelistCountry, _foreignKeysWhitelistCountry, _indicesWhitelistCountry);
        final TableInfo _existingWhitelistCountry = TableInfo.read(db, "whitelist_country");
        if (!_infoWhitelistCountry.equals(_existingWhitelistCountry)) {
          return new RoomOpenHelper.ValidationResult(false, "whitelist_country(com.roamguard.data.local.entity.WhitelistCountryEntity).\n"
                  + " Expected:\n" + _infoWhitelistCountry + "\n"
                  + " Found:\n" + _existingWhitelistCountry);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3f9b0eabbbf2efb5b34de5724e2cdf21", "7243e42867ffd5956ecd7f14895dd30e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "home_country","whitelist_country");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `home_country`");
      _db.execSQL("DELETE FROM `whitelist_country`");
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
    _typeConvertersMap.put(HomeCountryDao.class, HomeCountryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WhitelistDao.class, WhitelistDao_Impl.getRequiredConverters());
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
  public HomeCountryDao homeCountryDao() {
    if (_homeCountryDao != null) {
      return _homeCountryDao;
    } else {
      synchronized(this) {
        if(_homeCountryDao == null) {
          _homeCountryDao = new HomeCountryDao_Impl(this);
        }
        return _homeCountryDao;
      }
    }
  }

  @Override
  public WhitelistDao whitelistDao() {
    if (_whitelistDao != null) {
      return _whitelistDao;
    } else {
      synchronized(this) {
        if(_whitelistDao == null) {
          _whitelistDao = new WhitelistDao_Impl(this);
        }
        return _whitelistDao;
      }
    }
  }
}
