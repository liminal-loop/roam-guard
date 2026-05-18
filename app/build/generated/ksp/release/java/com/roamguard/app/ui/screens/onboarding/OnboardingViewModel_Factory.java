package com.roamguard.app.ui.screens.onboarding;

import com.roamguard.domain.repository.SettingsRepository;
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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  public OnboardingViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), whitelistRepositoryProvider.get());
  }

  public static OnboardingViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    return new OnboardingViewModel_Factory(settingsRepositoryProvider, whitelistRepositoryProvider);
  }

  public static OnboardingViewModel newInstance(SettingsRepository settingsRepository,
      WhitelistRepository whitelistRepository) {
    return new OnboardingViewModel(settingsRepository, whitelistRepository);
  }
}
