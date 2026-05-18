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
public final class RootHelper_Factory implements Factory<RootHelper> {
  private final Provider<RootCommandExecutor> executorProvider;

  public RootHelper_Factory(Provider<RootCommandExecutor> executorProvider) {
    this.executorProvider = executorProvider;
  }

  @Override
  public RootHelper get() {
    return newInstance(executorProvider.get());
  }

  public static RootHelper_Factory create(Provider<RootCommandExecutor> executorProvider) {
    return new RootHelper_Factory(executorProvider);
  }

  public static RootHelper newInstance(RootCommandExecutor executor) {
    return new RootHelper(executor);
  }
}
