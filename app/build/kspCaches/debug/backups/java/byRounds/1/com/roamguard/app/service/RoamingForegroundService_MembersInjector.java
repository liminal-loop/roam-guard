package com.roamguard.app.service;

import com.roamguard.common.system.SystemNetworkController;
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
  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<SystemNetworkController> systemControllerProvider;

  public RoamingForegroundService_MembersInjector(
      Provider<WhitelistRepository> whitelistRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SystemNetworkController> systemControllerProvider) {
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.systemControllerProvider = systemControllerProvider;
  }

  public static MembersInjector<RoamingForegroundService> create(
      Provider<WhitelistRepository> whitelistRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SystemNetworkController> systemControllerProvider) {
    return new RoamingForegroundService_MembersInjector(whitelistRepositoryProvider, settingsRepositoryProvider, systemControllerProvider);
  }

  @Override
  public void injectMembers(RoamingForegroundService instance) {
    injectWhitelistRepository(instance, whitelistRepositoryProvider.get());
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
    injectSystemController(instance, systemControllerProvider.get());
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

  @InjectedFieldSignature("com.roamguard.app.service.RoamingForegroundService.systemController")
  public static void injectSystemController(RoamingForegroundService instance,
      SystemNetworkController systemController) {
    instance.systemController = systemController;
  }
}
