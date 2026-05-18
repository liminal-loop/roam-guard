package com.roamguard.app.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.roamguard.common.system.SystemNetworkController;
import com.roamguard.domain.repository.WhitelistRepository;
import dagger.internal.DaggerGenerated;
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
public final class RoamingCheckWorker_Factory {
  private final Provider<SystemNetworkController> systemControllerProvider;

  private final Provider<WhitelistRepository> whitelistRepositoryProvider;

  public RoamingCheckWorker_Factory(Provider<SystemNetworkController> systemControllerProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    this.systemControllerProvider = systemControllerProvider;
    this.whitelistRepositoryProvider = whitelistRepositoryProvider;
  }

  public RoamingCheckWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, systemControllerProvider.get(), whitelistRepositoryProvider.get());
  }

  public static RoamingCheckWorker_Factory create(
      Provider<SystemNetworkController> systemControllerProvider,
      Provider<WhitelistRepository> whitelistRepositoryProvider) {
    return new RoamingCheckWorker_Factory(systemControllerProvider, whitelistRepositoryProvider);
  }

  public static RoamingCheckWorker newInstance(Context appContext, WorkerParameters workerParams,
      SystemNetworkController systemController, WhitelistRepository whitelistRepository) {
    return new RoamingCheckWorker(appContext, workerParams, systemController, whitelistRepository);
  }
}
