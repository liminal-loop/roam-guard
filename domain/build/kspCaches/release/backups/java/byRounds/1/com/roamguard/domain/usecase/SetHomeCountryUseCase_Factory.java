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
public final class SetHomeCountryUseCase_Factory implements Factory<SetHomeCountryUseCase> {
  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  public SetHomeCountryUseCase_Factory(Provider<WhitelistRepository> whitelistRepositoryProvider) {
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
  }

  @Override
  public SetHomeCountryUseCase get() {
    return newInstance(whitelistRepositoryProvider.get());
  }

  public static SetHomeCountryUseCase_Factory create(
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    return new SetHomeCountryUseCase_Factory(whitelistRepositoryProvider);
  }

  public static SetHomeCountryUseCase newInstance(WhitelistRepository whitelistRepository) {
    return new SetHomeCountryUseCase(whitelistRepository);
  }
}
