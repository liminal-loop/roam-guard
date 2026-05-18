package com.roamguard.data.di;

import com.roamguard.data.local.dao.HomeCountryDao;
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
public final class DatabaseModule_ProvideHomeCountryDaoFactory implements Factory<HomeCountryDao> {
  private final Provider<RoamGuardDatabase> databaseProvider;

  public DatabaseModule_ProvideHomeCountryDaoFactory(Provider<RoamGuardDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public HomeCountryDao get() {
    return provideHomeCountryDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideHomeCountryDaoFactory create(
      Provider<RoamGuardDatabase> databaseProvider) {
    return new DatabaseModule_ProvideHomeCountryDaoFactory(databaseProvider);
  }

  public static HomeCountryDao provideHomeCountryDao(RoamGuardDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHomeCountryDao(database));
  }
}
