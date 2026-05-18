package com.roamguard.app.ui.screens.whitelist;

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
public final class WhitelistViewModel_Factory implements Factory<WhitelistViewModel> {
  private final Provider<ManageWhitelistUseCase> manageWhitelistUseCaseProvider;

  public WhitelistViewModel_Factory(
      Provider<ManageWhitelistUseCase> manageWhitelistUseCaseProvider) {
    this.manageWhitelistUseCaseProvider = manageWhitelistUseCaseProvider;
  }

  @Override
  public WhitelistViewModel get() {
    return newInstance(manageWhitelistUseCaseProvider.get());
  }

  public static WhitelistViewModel_Factory create(
      Provider<ManageWhitelistUseCase> manageWhitelistUseCaseProvider) {
    return new WhitelistViewModel_Factory(manageWhitelistUseCaseProvider);
  }

  public static WhitelistViewModel newInstance(ManageWhitelistUseCase manageWhitelistUseCase) {
    return new WhitelistViewModel(manageWhitelistUseCase);
  }
}
