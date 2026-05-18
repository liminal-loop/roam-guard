package com.roamguard.app.di;

import com.roamguard.common.system.SystemNetworkController;
import com.roamguard.root.RootHelper;
import com.roamguard.root.RootSystemController;
import com.roamguard.shizuku.ShizukuHelper;
import com.roamguard.shizuku.ShizukuSystemController;
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
public final class SystemControllerModule_ProvideSystemNetworkControllerFactory implements Factory<SystemNetworkController> {
  private final Provider<RootHelper> rootHelperProvider;

  private final Provider<RootSystemController> rootControllerProvider;

  private final Provider<ShizukuHelper> shizukuHelperProvider;

  private final Provider<ShizukuSystemController> shizukuControllerProvider;

  public SystemControllerModule_ProvideSystemNetworkControllerFactory(
      Provider<RootHelper> rootHelperProvider,
      Provider<RootSystemController> rootControllerProvider,
      Provider<ShizukuHelper> shizukuHelperProvider,
      Provider<ShizukuSystemController> shizukuControllerProvider) {
    this.rootHelperProvider = rootHelperProvider;
    this.rootControllerProvider = rootControllerProvider;
    this.shizukuHelperProvider = shizukuHelperProvider;
    this.shizukuControllerProvider = shizukuControllerProvider;
  }

  @Override
  public SystemNetworkController get() {
    return provideSystemNetworkController(rootHelperProvider.get(), rootControllerProvider.get(), shizukuHelperProvider.get(), shizukuControllerProvider.get());
  }

  public static SystemControllerModule_ProvideSystemNetworkControllerFactory create(
      Provider<RootHelper> rootHelperProvider,
      Provider<RootSystemController> rootControllerProvider,
      Provider<ShizukuHelper> shizukuHelperProvider,
      Provider<ShizukuSystemController> shizukuControllerProvider) {
    return new SystemControllerModule_ProvideSystemNetworkControllerFactory(rootHelperProvider, rootControllerProvider, shizukuHelperProvider, shizukuControllerProvider);
  }

  public static SystemNetworkController provideSystemNetworkController(RootHelper rootHelper,
      RootSystemController rootController, ShizukuHelper shizukuHelper,
      ShizukuSystemController shizukuController) {
    return Preconditions.checkNotNullFromProvides(SystemControllerModule.INSTANCE.provideSystemNetworkController(rootHelper, rootController, shizukuHelper, shizukuController));
  }
}
