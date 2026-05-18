package com.roamguard.data.local.mcc;

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
public final class MccDatasetLoader_Factory implements Factory<MccDatasetLoader> {
  private final Provider<Context> contextProvider;

  public MccDatasetLoader_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MccDatasetLoader get() {
    return newInstance(contextProvider.get());
  }

  public static MccDatasetLoader_Factory create(Provider<Context> contextProvider) {
    return new MccDatasetLoader_Factory(contextProvider);
  }

  public static MccDatasetLoader newInstance(Context context) {
    return new MccDatasetLoader(context);
  }
}
