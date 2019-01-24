package com.example.tanya.tatianaryabova;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        NewsListFragment fragment = new NewsListFragment();
        FrameLayout frame = findViewById(R.id.main_frame);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_frame, fragment)
                .commit();
    }
}
