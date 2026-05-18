package com.roamguard.shizuku;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class ShizukuSystemController_Factory implements Factory<ShizukuSystemController> {
  private final Provider<Context> contextProvider;

  private final Provider<ShizukuHelper> shizukuHelperProvider;

  public ShizukuSystemController_Factory(Provider<Context> contextProvider,
      Provider<ShizukuHelper> shizukuHelperProvider) {
    this.contextProvider = contextProvider;
    this.shizukuHelperProvider = shizukuHelperProvider;
  }

  @Override
  public ShizukuSystemController get() {
    return newInstance(contextProvider.get(), shizukuHelperProvider.get());
  }

  public static ShizukuSystemController_Factory create(Provider<Context> contextProvider,
      Provider<ShizukuHelper> shizukuHelperProvider) {
    return new ShizukuSystemController_Factory(contextProvider, shizukuHelperProvider);
  }

  public static ShizukuSystemController newInstance(Context context, ShizukuHelper shizukuHelper) {
    return new ShizukuSystemController(context, shizukuHelper);
  }
}
