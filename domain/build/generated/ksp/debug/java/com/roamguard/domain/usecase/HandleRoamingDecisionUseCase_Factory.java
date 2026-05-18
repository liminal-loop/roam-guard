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
public final class HandleRoamingDecisionUseCase_Factory implements Factory<HandleRoamingDecisionUseCase> {
  private final Provider<NetworkRepository> networkRepositoryProvider;

  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  public HandleRoamingDecisionUseCase_Factory(Provider<NetworkRepository> networkRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    this.networkRepositoryProvider = networkRepositoryProvider;
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
  }

  @Override
  public HandleRoamingDecisionUseCase get() {
    return newInstance(networkRepositoryProvider.get(), whitelistRepositoryProvider.get());
  }

  public static HandleRoamingDecisionUseCase_Factory create(
      Provider<NetworkRepository> networkRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    return new HandleRoamingDecisionUseCase_Factory(networkRepositoryProvider, whitelistRepositoryProvider);
  }

  public static HandleRoamingDecisionUseCase newInstance(NetworkRepository networkRepository,
      WhitelistRepository whitelistRepository) {
    return new HandleRoamingDecisionUseCase(networkRepository, whitelistRepository);
  }
}
