package com.roamguard.root.di;

import com.roamguard.root.RootCommandExecutor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class RootModule_ProvideRootCommandExecutorFactory implements Factory<RootCommandExecutor> {
  @Override
  public RootCommandExecutor get() {
    return provideRootCommandExecutor();
  }

  public static RootModule_ProvideRootCommandExecutorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RootCommandExecutor provideRootCommandExecutor() {
    return Preconditions.checkNotNullFromProvides(RootModule.INSTANCE.provideRootCommandExecutor());
  }

  private static final class InstanceHolder {
    private static final RootModule_ProvideRootCommandExecutorFactory INSTANCE = new RootModule_ProvideRootCommandExecutorFactory();
  }
}
