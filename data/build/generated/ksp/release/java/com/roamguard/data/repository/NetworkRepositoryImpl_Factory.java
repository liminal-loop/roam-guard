package com.roamguard.data.repository;

import android.content.Context;
import com.roamguard.domain.repository.WhitelistRepository;
import com.roamguard.root.RootHelper;
import com.roamguard.shizuku.ShizukuHelper;
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
public final class NetworkRepositoryImpl_Factory implements Factory<NetworkRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  private final Provider<RootHelper> rootHelperProvider;

  private final Provider<ShizukuHelper> shizukuHelperProvider;

  public NetworkRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider,
      Provider<RootHelper> rootHelperProvider, Provider<ShizukuHelper> shizukuHelperProvider) {
    this.contextProvider = contextProvider;
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
    this.rootHelperProvider = rootHelperProvider;
    this.shizukuHelperProvider = shizukuHelperProvider;
  }

  @Override
  public NetworkRepositoryImpl get() {
    return newInstance(contextProvider.get(), whitelistRepositoryProvider.get(), rootHelperProvider.get(), shizukuHelperProvider.get());
  }

  public static NetworkRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider,
      Provider<RootHelper> rootHelperProvider, Provider<ShizukuHelper> shizukuHelperProvider) {
    return new NetworkRepositoryImpl_Factory(contextProvider, whitelistRepositoryProvider, rootHelperProvider, shizukuHelperProvider);
  }

  public static NetworkRepositoryImpl newInstance(Context context,
      WhitelistRepository whitelistRepository, RootHelper rootHelper, ShizukuHelper shizukuHelper) {
    return new NetworkRepositoryImpl(context, whitelistRepository, rootHelper, shizukuHelper);
  }
}
