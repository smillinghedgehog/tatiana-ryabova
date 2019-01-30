package com.example.tanya.tatianaryabova;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.tanya.tatianaryabova.fragments.NewsListFragment;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class MainActivity extends AppCompatActivity {
    NewsListFragment fragment;
    String CONTEXT = "CONTEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

  //      Intent startUpdate = new Intent(this, UpdateService.class);
  //      startService(startUpdate);

        fragment = new NewsListFragment();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_frame, fragment)
                    .addToBackStack(null)
                    .commit();
        }

        setSupportActionBar((Toolbar) findViewById(R.id.news_toolbar));
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return super.onSupportNavigateUp();
    }
}
