package com.roamguard.root.di;

import com.roamguard.root.RootCommandExecutor;
import com.roamguard.root.RootHelper;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class RootModule_ProvideRootHelperFactory implements Factory<RootHelper> {
  private final Provider<RootCommandExecutor> executorProvider;

  public RootModule_ProvideRootHelperFactory(Provider<RootCommandExecutor> executorProvider) {
    this.executorProvider = executorProvider;
  }

  @Override
  public RootHelper get() {
    return provideRootHelper(executorProvider.get());
  }

  public static RootModule_ProvideRootHelperFactory create(
      Provider<RootCommandExecutor> executorProvider) {
    return new RootModule_ProvideRootHelperFactory(executorProvider);
  }

  public static RootHelper provideRootHelper(RootCommandExecutor executor) {
    return Preconditions.checkNotNullFromProvides(RootModule.INSTANCE.provideRootHelper(executor));
  }
}
