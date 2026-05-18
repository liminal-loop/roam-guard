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
public final class ConnectToNetworkUseCase_Factory implements Factory<ConnectToNetworkUseCase> {
  private final Provider<NetworkRepository> networkRepositoryProvider;

  public ConnectToNetworkUseCase_Factory(Provider<NetworkRepository> networkRepositoryProvider) {
    this.networkRepositoryProvider = networkRepositoryProvider;
  }

  @Override
  public ConnectToNetworkUseCase get() {
    return newInstance(networkRepositoryProvider.get());
  }

  public static ConnectToNetworkUseCase_Factory create(
      Provider<NetworkRepository> networkRepositoryProvider) {
    return new ConnectToNetworkUseCase_Factory(networkRepositoryProvider);
  }

  public static ConnectToNetworkUseCase newInstance(NetworkRepository networkRepository) {
    return new ConnectToNetworkUseCase(networkRepository);
  }
}
