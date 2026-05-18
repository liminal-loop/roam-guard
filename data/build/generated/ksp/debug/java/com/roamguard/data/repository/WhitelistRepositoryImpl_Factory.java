package com.roamguard.data.repository;

import com.roamguard.data.local.dao.HomeCountryDao;
import com.roamguard.data.local.dao.WhitelistDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class WhitelistRepositoryImpl_Factory implements Factory<WhitelistRepositoryImpl> {
  private final Provider<HomeCountryDao> homeCountryDaoProvider;

  private final Provider<WhitelistDao> whitelistDaoProvider;

  public WhitelistRepositoryImpl_Factory(Provider<HomeCountryDao> homeCountryDaoProvider,
      Provider<WhitelistDao> whitelistDaoProvider) {
    this.homeCountryDaoProvider = homeCountryDaoProvider;
    this.whitelistDaoProvider = whitelistDaoProvider;
  }

  @Override
  public WhitelistRepositoryImpl get() {
    return newInstance(homeCountryDaoProvider.get(), whitelistDaoProvider.get());
  }

  public static WhitelistRepositoryImpl_Factory create(
      Provider<HomeCountryDao> homeCountryDaoProvider,
      Provider<WhitelistDao> whitelistDaoProvider) {
    return new WhitelistRepositoryImpl_Factory(homeCountryDaoProvider, whitelistDaoProvider);
  }

  public static WhitelistRepositoryImpl newInstance(HomeCountryDao homeCountryDao,
      WhitelistDao whitelistDao) {
    return new WhitelistRepositoryImpl(homeCountryDao, whitelistDao);
  }
}
