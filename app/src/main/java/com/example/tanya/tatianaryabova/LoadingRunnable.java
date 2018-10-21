package com.example.tanya.tatianaryabova;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.example.tanya.tatianaryabova.models.News;
import com.example.tanya.tatianaryabova.utils.DataUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class LoadingRunnable implements Runnable {
    @Nullable private final WeakReference<Activity> activityWeakReference;
    private UIRunnable uiRunnable;
    LoadingRunnable(Activity activity, UIRunnable uiRunnable){
        activityWeakReference = new WeakReference<Activity>(activity);
        this.uiRunnable = uiRunnable;
    }

    @Override
    public void run(){
        List<News> news = DataUtils.generateNews();
        Activity activity = activityWeakReference.get();
        if (activity != null){
            uiRunnable.getNews(news);
            activity.runOnUiThread(uiRunnable);
        }
    }
}
