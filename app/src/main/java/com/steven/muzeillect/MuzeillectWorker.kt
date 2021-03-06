package com.steven.muzeillect

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.apps.muzei.api.provider.ProviderContract

class MuzeillectWorker(private val context: Context, workerParams: WorkerParameters)
  : Worker(context, workerParams) {

  override fun doWork(): Result {
    val core = MuzeillectCore(context)
    core.generateMaxToken()
    val artwork = core.getArtwork() ?: return Result.retry()
    val provider = ProviderContract.getProviderClient<MuzeillectArtProvider>(context)
    provider.addArtwork(artwork)
    return Result.success()
  }
}
