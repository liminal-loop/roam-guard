package com.roamguard.domain.usecase;

import com.roamguard.domain.repository.NetworkRepository;
import com.roamguard.domain.repository.WhitelistRepository;
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
public final class CheckRoamingUseCase_Factory implements Factory<CheckRoamingUseCase> {
  private final Provider<NetworkRepository> networkRepositoryProvider;

  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  public CheckRoamingUseCase_Factory(Provider<NetworkRepository> networkRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    this.networkRepositoryProvider = networkRepositoryProvider;
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
  }

  @Override
  public CheckRoamingUseCase get() {
    return newInstance(networkRepositoryProvider.get(), whitelistRepositoryProvider.get());
  }

  public static CheckRoamingUseCase_Factory create(
      Provider<NetworkRepository> networkRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    return new CheckRoamingUseCase_Factory(networkRepositoryProvider, whitelistRepositoryProvider);
  }

  public static CheckRoamingUseCase newInstance(NetworkRepository networkRepository,
      WhitelistRepository whitelistRepository) {
    return new CheckRoamingUseCase(networkRepository, whitelistRepository);
  }
}
