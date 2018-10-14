package com.example.tanya.tatianaryabova;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import com.example.tanya.tatianaryabova.models.News;
import com.example.tanya.tatianaryabova.utils.DataUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        int orientation = getResources().getConfiguration().orientation;
        int spanCount = 1;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 2;
        }

        new Thread(new LoadingRunnable(this)).start();

        RecyclerView recyclerView = findViewById(R.id.news_activity);
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about_menu_item:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
        }
        return true;
    }

    private static class LoadingRunnable implements Runnable {
        @Nullable private final WeakReference<Activity> activityWeakReference;
        LoadingRunnable(Activity activity){
            activityWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void run(){
            List<News> news = DataUtils.generateNews();
            Activity activity = activityWeakReference.get();
            if (activity != null){
                activity.runOnUiThread(new UIRunnable(news, activity));
            }
        }
    }

    private static class UIRunnable implements Runnable{
        private List<News> news;
        private final WeakReference<Activity> activityWeakReference;
        UIRunnable(List<News> news, Activity activity){
            this.news = news;
            activityWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void run(){
            Activity activity = activityWeakReference.get();
            if (activity != null) {
                RecyclerView recyclerView = activity.findViewById(R.id.news_activity);
                NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(activity, news);
                recyclerView.setAdapter(adapter);
                activity.findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }
        }
    }

}
