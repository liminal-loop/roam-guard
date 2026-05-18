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
public final class GetHomeCountryUseCase_Factory implements Factory<GetHomeCountryUseCase> {
  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  public GetHomeCountryUseCase_Factory(Provider<WhitelistRepository> whitelistRepositoryProvider) {
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
  }

  @Override
  public GetHomeCountryUseCase get() {
    return newInstance(whitelistRepositoryProvider.get());
  }

  public static GetHomeCountryUseCase_Factory create(
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    return new GetHomeCountryUseCase_Factory(whitelistRepositoryProvider);
  }

  public static GetHomeCountryUseCase newInstance(WhitelistRepository whitelistRepository) {
    return new GetHomeCountryUseCase(whitelistRepository);
  }
}
