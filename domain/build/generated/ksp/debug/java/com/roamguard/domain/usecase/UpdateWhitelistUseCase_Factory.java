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
public final class UpdateWhitelistUseCase_Factory implements Factory<UpdateWhitelistUseCase> {
  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  public UpdateWhitelistUseCase_Factory(Provider<WhitelistRepository> whitelistRepositoryProvider) {
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
  }

  @Override
  public UpdateWhitelistUseCase get() {
    return newInstance(whitelistRepositoryProvider.get());
  }

  public static UpdateWhitelistUseCase_Factory create(
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    return new UpdateWhitelistUseCase_Factory(whitelistRepositoryProvider);
  }

  public static UpdateWhitelistUseCase newInstance(WhitelistRepository whitelistRepository) {
    return new UpdateWhitelistUseCase(whitelistRepository);
  }
}
