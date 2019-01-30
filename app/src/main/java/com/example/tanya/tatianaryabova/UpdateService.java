package com.example.tanya.tatianaryabova;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.example.tanya.tatianaryabova.dto.NewsDTO;
import com.example.tanya.tatianaryabova.fragments.FullNewsFragment;
import com.example.tanya.tatianaryabova.network.DefaultResponse;
import com.example.tanya.tatianaryabova.network.RestApi;
import com.example.tanya.tatianaryabova.persistency.Converter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateService extends Service {

    @Nullable
    private Call<DefaultResponse<List<NewsDTO>>> loadRequest;

    private Converter converter;
    private Context context;
    private NotificationChannel chanel;
    private String chanelID = "CHANEL_NOTIF";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            chanel = new NotificationChannel(chanelID, "Update notification", NotificationManager.IMPORTANCE_DEFAULT);
            nManager.createNotificationChannel(chanel);
        }

        Notification notification = new NotificationCompat.Builder(this, chanelID)
                .setSmallIcon(R.drawable.hedgehog)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        synchronized (notification) {
            Log.v("Note", "Time to notify");
            notification.notify();
        }
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = this;
        Log.v("Works", "Yup!");
            loadRequest = RestApi.getInstance().news().results("home");

            loadRequest.enqueue(new Callback<DefaultResponse<List<NewsDTO>>>() {
                @Override
                public void onResponse(Call<DefaultResponse<List<NewsDTO>>> call, Response<DefaultResponse<List<NewsDTO>>> response) {

                    final DefaultResponse<List<NewsDTO>> body = response.body();

                    final List<NewsDTO> results = body.getResults();

                    Collections.sort(results, new Comparator<NewsDTO>() {
                        @Override
                        public int compare(NewsDTO newsDTO, NewsDTO t1) {
                            return t1.getPublishedDate().compareTo(newsDTO.getPublishedDate());
                        }
                    });

                    converter = new Converter(context);
                    converter.toDatabase(results);
                    if(Build.VERSION.SDK_INT > 23) {
                        Log.v("Stop", "STop IT");
                        stopForeground(STOP_FOREGROUND_REMOVE);
                    }
                    String newChanelID = "NEW_CHANEL";
                    NotificationManager nManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel chanel = new NotificationChannel(newChanelID, "Update notification", NotificationManager.IMPORTANCE_DEFAULT);
                        nManager.createNotificationChannel(chanel);
                    }
                    Notification notification = new NotificationCompat.Builder(context, newChanelID)
                            .setSmallIcon(R.drawable.hedgehog)
                            .setContentTitle(getString(R.string.notification_title))
                            .setContentText("News are successfully updated!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .build();
                    synchronized (notification) {
                        Log.v("Note", "Time to notifyfyfy");
                        nManager.notify(0, notification);
                    }
                    stopSelf();
                }

                @Override
                public void onFailure(Call<DefaultResponse<List<NewsDTO>>> call, Throwable t) {
                    Log.v("MESSAGE", t.getMessage());

                    String newChanelID = "NEW_CHANEL";
                    NotificationManager nManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel chanel = new NotificationChannel(newChanelID, "Update notification", NotificationManager.IMPORTANCE_DEFAULT);
                        nManager.createNotificationChannel(chanel);
                    }
                    Notification notification = new NotificationCompat.Builder(context, newChanelID)
                            .setSmallIcon(R.drawable.hedgehog)
                            .setContentTitle(getString(R.string.notification_title))
                            .setContentText("Can't update")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .build();
                    synchronized (notification) {
                        Log.v("Note", "Time to notifyfyfy");
                        nManager.notify(0, notification);
                    }
                    stopSelf();
                }
            });
            return START_STICKY;
    }
}
