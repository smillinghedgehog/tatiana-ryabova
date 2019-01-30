package com.example.tanya.tatianaryabova;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UpdateWork extends Worker {

    public UpdateWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Intent startUpdate = new Intent(getApplicationContext(), UpdateService.class);
        getApplicationContext().startService(startUpdate);
        Log.v("Worker", "Hi");
        return Result.success();
    }
}
