package com.roamguard.data.di;

import com.roamguard.data.local.dao.WhitelistDao;
import com.roamguard.data.local.db.RoamGuardDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DatabaseModule_ProvideWhitelistDaoFactory implements Factory<WhitelistDao> {
  private final Provider<RoamGuardDatabase> databaseProvider;

  public DatabaseModule_ProvideWhitelistDaoFactory(Provider<RoamGuardDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WhitelistDao get() {
    return provideWhitelistDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideWhitelistDaoFactory create(
      Provider<RoamGuardDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWhitelistDaoFactory(databaseProvider);
  }

  public static WhitelistDao provideWhitelistDao(RoamGuardDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWhitelistDao(database));
  }
}
