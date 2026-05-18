package com.roamguard.data.repository;

import com.roamguard.data.local.mcc.MccDatasetLoader;
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
public final class CountryRepositoryImpl_Factory implements Factory<CountryRepositoryImpl> {
  private final Provider<MccDatasetLoader> mccDatasetLoaderProvider;

  public CountryRepositoryImpl_Factory(Provider<MccDatasetLoader> mccDatasetLoaderProvider) {
    this.mccDatasetLoaderProvider = mccDatasetLoaderProvider;
  }

  @Override
  public CountryRepositoryImpl get() {
    return newInstance(mccDatasetLoaderProvider.get());
  }

  public static CountryRepositoryImpl_Factory create(
      Provider<MccDatasetLoader> mccDatasetLoaderProvider) {
    return new CountryRepositoryImpl_Factory(mccDatasetLoaderProvider);
  }

  public static CountryRepositoryImpl newInstance(MccDatasetLoader mccDatasetLoader) {
    return new CountryRepositoryImpl(mccDatasetLoader);
  }
}
