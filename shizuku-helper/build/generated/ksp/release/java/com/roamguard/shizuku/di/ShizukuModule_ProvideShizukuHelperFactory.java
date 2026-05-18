package com.roamguard.shizuku.di;

import com.roamguard.shizuku.ShizukuHelper;
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
public final class ShizukuModule_ProvideShizukuHelperFactory implements Factory<ShizukuHelper> {
  @Override
  public ShizukuHelper get() {
    return provideShizukuHelper();
  }

  public static ShizukuModule_ProvideShizukuHelperFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ShizukuHelper provideShizukuHelper() {
    return Preconditions.checkNotNullFromProvides(ShizukuModule.INSTANCE.provideShizukuHelper());
  }

  private static final class InstanceHolder {
    private static final ShizukuModule_ProvideShizukuHelperFactory INSTANCE = new ShizukuModule_ProvideShizukuHelperFactory();
  }
}
