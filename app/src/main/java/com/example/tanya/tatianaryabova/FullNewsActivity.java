package com.example.tanya.tatianaryabova;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanya.tatianaryabova.models.News;
import com.example.tanya.tatianaryabova.utils.DataUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class FullNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        int position = getIntent().getIntExtra(getString(R.string.position), 1);

        new Thread(new LoadingRunnable(this, position)).start();
    }

    private static class LoadingRunnable implements Runnable {
        @Nullable
        private final WeakReference<Activity> activityWeakReference;
        private int position;

        LoadingRunnable(Activity activity, int position){
            activityWeakReference = new WeakReference<Activity>(activity);
            this.position = position;
        }

        @Override
        public void run(){
            List<News> news = DataUtils.generateNews();
            Activity activity = activityWeakReference.get();
            if (activity != null){
                activity.runOnUiThread(new UIRunnable(news.get(position), activity));
            }
        }
    }

    private static class UIRunnable implements Runnable{
        private News newsOne;
        private final WeakReference<Activity> activityWeakReference;
        UIRunnable(News newsOne, Activity activity){
            this.newsOne = newsOne;
            activityWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void run(){
            Activity activity = activityWeakReference.get();
            if (activity != null) {
                ImageView photo = activity.findViewById(R.id.full_news_photo);
                Glide.with(activity).load(newsOne.getImageUrl()).into(photo);
                TextView title = activity.findViewById(R.id.title_full);
                title.setText(newsOne.getTitle());
                TextView date = activity.findViewById(R.id.date_full);
                date.setText(DateUtils.getRelativeDateTimeString(activity, newsOne.getPublishDate().getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR));
                TextView text = activity.findViewById(R.id.text_full);
                text.setText(newsOne.getFullText());
                activity.setTitle(newsOne.getCategory().getName());
                activity.findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }
        }
    }
}
