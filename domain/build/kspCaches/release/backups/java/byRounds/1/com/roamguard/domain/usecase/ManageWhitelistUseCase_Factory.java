package com.roamguard.domain.usecase;

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
public final class ManageWhitelistUseCase_Factory implements Factory<ManageWhitelistUseCase> {
  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  public ManageWhitelistUseCase_Factory(Provider<WhitelistRepository> whitelistRepositoryProvider) {
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
  }

  @Override
  public ManageWhitelistUseCase get() {
    return newInstance(whitelistRepositoryProvider.get());
  }

  public static ManageWhitelistUseCase_Factory create(
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    return new ManageWhitelistUseCase_Factory(whitelistRepositoryProvider);
  }

  public static ManageWhitelistUseCase newInstance(WhitelistRepository whitelistRepository) {
    return new ManageWhitelistUseCase(whitelistRepository);
  }
}
