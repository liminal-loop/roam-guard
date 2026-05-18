package com.roamguard.app.service;

import com.roamguard.domain.repository.NetworkRepository;
import com.roamguard.domain.repository.SettingsRepository;
import com.roamguard.domain.repository.WhitelistRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class RoamingForegroundService_MembersInjector implements MembersInjector<RoamingForegroundService> {
  private final Provider<NetworkRepository> networkRepositoryProvider;

  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public RoamingForegroundService_MembersInjector(
      Provider<NetworkRepository> networkRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.networkRepositoryProvider = networkRepositoryProvider;
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  public static MembersInjector<RoamingForegroundService> create(
      Provider<NetworkRepository> networkRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new RoamingForegroundService_MembersInjector(networkRepositoryProvider, whitelistRepositoryProvider, settingsRepositoryProvider);
  }

  @Override
  public void injectMembers(RoamingForegroundService instance) {
    injectNetworkRepository(instance, networkRepositoryProvider.get());
    injectWhitelistRepository(instance, whitelistRepositoryProvider.get());
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.roamguard.app.service.RoamingForegroundService.networkRepository")
  public static void injectNetworkRepository(RoamingForegroundService instance,
      NetworkRepository networkRepository) {
    instance.networkRepository = networkRepository;
  }

  @InjectedFieldSignature("com.roamguard.app.service.RoamingForegroundService.whitelistRepository")
  public static void injectWhitelistRepository(RoamingForegroundService instance,
      WhitelistRepository whitelistRepository) {
    instance.whitelistRepository = whitelistRepository;
  }

  @InjectedFieldSignature("com.roamguard.app.service.RoamingForegroundService.settingsRepository")
  public static void injectSettingsRepository(RoamingForegroundService instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }
}
