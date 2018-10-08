package com.example.tanya.tatianaryabova;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;

public class NewsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RecyclerView recyclerView = findViewById(R.id.news_activity);
        recyclerView.setAdapter(new NewsRecyclerAdapter(this, DataUtils.generateNews()));

        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int spanCount = 2;
        if (display.getOrientation() == Surface.ROTATION_0){
            spanCount = 1;
        }

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
}
