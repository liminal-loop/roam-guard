package com.roamguard.domain.usecase;

import com.roamguard.domain.repository.NetworkRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ScanNetworksUseCase_Factory implements Factory<ScanNetworksUseCase> {
  private final Provider<NetworkRepository> networkRepositoryProvider;

  public ScanNetworksUseCase_Factory(Provider<NetworkRepository> networkRepositoryProvider) {
    this.networkRepositoryProvider = networkRepositoryProvider;
  }

  @Override
  public ScanNetworksUseCase get() {
    return newInstance(networkRepositoryProvider.get());
  }

  public static ScanNetworksUseCase_Factory create(
      Provider<NetworkRepository> networkRepositoryProvider) {
    return new ScanNetworksUseCase_Factory(networkRepositoryProvider);
  }

  public static ScanNetworksUseCase newInstance(NetworkRepository networkRepository) {
    return new ScanNetworksUseCase(networkRepository);
  }
}
