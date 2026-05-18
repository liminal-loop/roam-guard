package com.roamguard.app.ui.screens.networkscan;

import com.roamguard.domain.usecase.ConnectToNetworkUseCase;
import com.roamguard.domain.usecase.ScanNetworksUseCase;
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
public final class NetworkScanViewModel_Factory implements Factory<NetworkScanViewModel> {
  private final Provider<ScanNetworksUseCase> scanNetworksUseCaseProvider;

  private final Provider<ConnectToNetworkUseCase> connectToNetworkUseCaseProvider;

  public NetworkScanViewModel_Factory(Provider<ScanNetworksUseCase> scanNetworksUseCaseProvider,
      Provider<ConnectToNetworkUseCase> connectToNetworkUseCaseProvider) {
    this.scanNetworksUseCaseProvider = scanNetworksUseCaseProvider;
    this.connectToNetworkUseCaseProvider = connectToNetworkUseCaseProvider;
  }

  @Override
  public NetworkScanViewModel get() {
    return newInstance(scanNetworksUseCaseProvider.get(), connectToNetworkUseCaseProvider.get());
  }

  public static NetworkScanViewModel_Factory create(
      Provider<ScanNetworksUseCase> scanNetworksUseCaseProvider,
      Provider<ConnectToNetworkUseCase> connectToNetworkUseCaseProvider) {
    return new NetworkScanViewModel_Factory(scanNetworksUseCaseProvider, connectToNetworkUseCaseProvider);
  }

  public static NetworkScanViewModel newInstance(ScanNetworksUseCase scanNetworksUseCase,
      ConnectToNetworkUseCase connectToNetworkUseCase) {
    return new NetworkScanViewModel(scanNetworksUseCase, connectToNetworkUseCase);
  }
}
