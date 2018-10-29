package com.example.tanya.tatianaryabova;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.tanya.tatianaryabova.dto.NewsDTO;
import com.example.tanya.tatianaryabova.network.DefaultResponse;
import com.example.tanya.tatianaryabova.network.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private String homeSection = "home";
    private Spinner spinner;
    private String[] sections = new String[26];
    private int sectionPosition = 0;

    @Nullable
    private Call<DefaultResponse<List<NewsDTO>>> loadRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        setupUI();
    }

    @Override
    protected void onStart(){
        super.onStart();
        setupUX();
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

    private void setupUI(){
        recyclerView = findViewById(R.id.news_activity);
        progressBar = findViewById(R.id.progress_bar);
        setRecyclerView();
        setSupportActionBar((Toolbar)findViewById(R.id.news_toolbar));
        setSpinner();
        setSections();
    }

    private void setupUX(){
        loadNews(sections[sectionPosition]);
    }

    private void loadNews(String section){
        loadRequest = RestApi.getInstance().news().results(section);

        loadRequest.enqueue(new Callback<DefaultResponse<List<NewsDTO>>>() {
            @Override
            public void onResponse(Call<DefaultResponse<List<NewsDTO>>> call, Response<DefaultResponse<List<NewsDTO>>> response) {

                final DefaultResponse<List<NewsDTO>> body = response.body();

                final List<NewsDTO> results = body.getResults();

                adapter.addNews(results);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<DefaultResponse<List<NewsDTO>>> call, Throwable t) {
                Log.v("MESSAGE", t.getMessage());
            }
        });
    }

    private void setRecyclerView(){
        int orientation = getResources().getConfiguration().orientation;
        int spanCount = 1;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 2;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        adapter = new NewsRecyclerAdapter(this, Glide.with(this));
        recyclerView.setAdapter(adapter);

    }

    private void setSpinner(){
        spinner = findViewById(R.id.sections);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.section_array, R.layout.activity_spinner_main_item);
        adapter.setDropDownViewResource(R.layout.activity_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadNews(sections[i]);
                sectionPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSections(){
        sections[0] = "home";
        sections[1] = "opinion";
        sections[2] = "world";
        sections[3] = "national";
        sections[4] = "politics";
        sections[5] = "upshot";
        sections[6] = "nyregion";
        sections[7] = "business";
        sections[8] = "technology";
        sections[9] = "science";
        sections[10] = "health";
        sections[11] = "sports";
        sections[12] = "arts";
        sections[13] = "books";
        sections[14] = "movies";
        sections[15] = "theater";
        sections[16] = "sundayreview";
        sections[17] = "fashion";
        sections[18] = "tmagazine";
        sections[19] = "food";
        sections[20] = "travel";
        sections[21] = "magazine";
        sections[22] = "realestate";
        sections[23] = "automobiles";
        sections[24] = "obituaries";
        sections[25] = "insider";
    }

}
