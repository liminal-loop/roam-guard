package com.roamguard.app.ui.screens.home;

import android.app.Application;
import com.roamguard.domain.repository.NetworkRepository;
import com.roamguard.domain.repository.SettingsRepository;
import com.roamguard.domain.repository.WhitelistRepository;
import com.roamguard.domain.usecase.CheckRoamingUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<Application> applicationProvider;

  private final Provider<CheckRoamingUseCase> checkRoamingUseCaseProvider;

  private final Provider<NetworkRepository> networkRepositoryProvider;

  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public HomeViewModel_Factory(Provider<Application> applicationProvider,
      Provider<CheckRoamingUseCase> checkRoamingUseCaseProvider,
      Provider<NetworkRepository> networkRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.applicationProvider = applicationProvider;
    this.checkRoamingUseCaseProvider = checkRoamingUseCaseProvider;
    this.networkRepositoryProvider = networkRepositoryProvider;
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(applicationProvider.get(), checkRoamingUseCaseProvider.get(), networkRepositoryProvider.get(), whitelistRepositoryProvider.get(), settingsRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<Application> applicationProvider,
      Provider<CheckRoamingUseCase> checkRoamingUseCaseProvider,
      Provider<NetworkRepository> networkRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new HomeViewModel_Factory(applicationProvider, checkRoamingUseCaseProvider, networkRepositoryProvider, whitelistRepositoryProvider, settingsRepositoryProvider);
  }

  public static HomeViewModel newInstance(Application application,
      CheckRoamingUseCase checkRoamingUseCase, NetworkRepository networkRepository,
      WhitelistRepository whitelistRepository, SettingsRepository settingsRepository) {
    return new HomeViewModel(application, checkRoamingUseCase, networkRepository, whitelistRepository, settingsRepository);
  }
}
