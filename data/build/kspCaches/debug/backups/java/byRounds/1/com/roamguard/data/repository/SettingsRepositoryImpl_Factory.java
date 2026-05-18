package com.roamguard.data.repository;

import com.roamguard.data.local.datastore.PreferencesDataStore;
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
public final class SettingsRepositoryImpl_Factory implements Factory<SettingsRepositoryImpl> {
  private final Provider<PreferencesDataStore> dataStoreProvider;

  public SettingsRepositoryImpl_Factory(Provider<PreferencesDataStore> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public SettingsRepositoryImpl get() {
    return newInstance(dataStoreProvider.get());
  }

  public static SettingsRepositoryImpl_Factory create(
      Provider<PreferencesDataStore> dataStoreProvider) {
    return new SettingsRepositoryImpl_Factory(dataStoreProvider);
  }

  public static SettingsRepositoryImpl newInstance(PreferencesDataStore dataStore) {
    return new SettingsRepositoryImpl(dataStore);
  }
}
