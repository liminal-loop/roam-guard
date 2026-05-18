package com.roamguard.root;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RootSystemController_Factory implements Factory<RootSystemController> {
  private final Provider<RootHelper> rootHelperProvider;

  public RootSystemController_Factory(Provider<RootHelper> rootHelperProvider) {
    this.rootHelperProvider = rootHelperProvider;
  }

  @Override
  public RootSystemController get() {
    return newInstance(rootHelperProvider.get());
  }

  public static RootSystemController_Factory create(Provider<RootHelper> rootHelperProvider) {
    return new RootSystemController_Factory(rootHelperProvider);
  }

  public static RootSystemController newInstance(RootHelper rootHelper) {
    return new RootSystemController(rootHelper);
  }
}
