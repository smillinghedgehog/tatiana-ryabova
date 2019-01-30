package com.example.tanya.tatianaryabova;

import android.app.Application;
import android.util.Log;

import com.example.tanya.tatianaryabova.UpdateWork;

import java.util.concurrent.TimeUnit;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class NYApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        WorkRequest uploadWork = new PeriodicWorkRequest.Builder(UpdateWork.class, 3, TimeUnit.HOURS).build();
        WorkManager.getInstance().enqueue(uploadWork);
        Log.v("Application", "Hi");
    }
}
