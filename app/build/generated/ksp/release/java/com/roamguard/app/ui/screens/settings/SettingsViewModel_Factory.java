package com.roamguard.app.ui.screens.settings;

import com.roamguard.domain.usecase.ManageWhitelistUseCase;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<ManageWhitelistUseCase> manageWhitelistUseCaseProvider;

  public SettingsViewModel_Factory(
      Provider<ManageWhitelistUseCase> manageWhitelistUseCaseProvider) {
    this.manageWhitelistUseCaseProvider = manageWhitelistUseCaseProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(manageWhitelistUseCaseProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<ManageWhitelistUseCase> manageWhitelistUseCaseProvider) {
    return new SettingsViewModel_Factory(manageWhitelistUseCaseProvider);
  }

  public static SettingsViewModel newInstance(ManageWhitelistUseCase manageWhitelistUseCase) {
    return new SettingsViewModel(manageWhitelistUseCase);
  }
}
