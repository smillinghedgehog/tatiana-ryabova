package com.example.tanya.tatianaryabova;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanya.tatianaryabova.persistency.Converter;
import com.example.tanya.tatianaryabova.persistency.NewsEntity;

import java.util.Date;

public class FullNewsActivity extends AppCompatActivity {

    private String NEWS_ID = "NEWS_ID";
    private Converter converter;
    private TextView title;
    private TextView text;
    private String newsID;
    private NewsEntity news;
    private CoordinatorLayout root;
    private ViewTreeObserver.OnGlobalLayoutListener listener;
    private boolean isKeyboardOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newsID = getIntent().getStringExtra(NEWS_ID);

        converter = new Converter(this);

        openFull(newsID);

        root = findViewById(R.id.full_news_layout);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int windowHeight = getResources().getDisplayMetrics().heightPixels;
                int rootHeight = root.getHeight();

                if (rootHeight < 0.6 * windowHeight && !isKeyboardOpened){
                    isKeyboardOpened = true;
                    ((NestedScrollView) findViewById(R.id.scroll_view)).scrollTo(0, windowHeight);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_full_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_menu_item:
                title.setVisibility(View.GONE);
                final EditText titleEdit = findViewById(R.id.title_edit);
                titleEdit.setText(title.getText());
                titleEdit.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);
                final EditText textEdit = findViewById(R.id.text_full_edit);
                textEdit.setText(text.getText());
                textEdit.setVisibility(View.VISIBLE);
                final Button saveBtn = findViewById(R.id.save_btn);
                saveBtn.setVisibility(View.VISIBLE);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        converter.deleteNewsByID(newsID);
                        NewsEntity editedNews = new NewsEntity();
                        newsID = titleEdit.getText().toString() + news.getUrl();
                        editedNews.setId(newsID);
                        editedNews.setTitle(titleEdit.getText().toString());
                        editedNews.setUrl(news.getUrl());
                        editedNews.setSection(news.getSection());
                        editedNews.setPreview(textEdit.getText().toString());
                        editedNews.setMultimediaUrl(news.getMultimediaUrl());
                        editedNews.setPublishedDate(news.getPublishedDate());
                        converter.insertNews(editedNews);

                        title.setText(titleEdit.getText().toString());
                        text.setText(textEdit.getText().toString());
                        title.setVisibility(View.VISIBLE);
                        titleEdit.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        textEdit.setVisibility(View.GONE);
                        saveBtn.setVisibility(View.GONE);
                    }
                });
                return true;

            case R.id.delete_menu_item:
                converter.deleteNewsByID(newsID);

                Intent backToNews = new Intent(this, NewsListFragment.class);
                startActivity(backToNews);
                return true;
        }
        return false;
    }

    private void openFull(String newsID) {
        news = converter.findNewsById(newsID);

        if (news != null) {
            ImageView photo = findViewById(R.id.full_news_photo);
            if (news.getMultimediaUrl() != null) {
                Glide.with(this).load(news.getMultimediaUrl()).into(photo);
            }
            title = findViewById(R.id.title_full);
            title.setText(news.getTitle());
            TextView date = findViewById(R.id.date_full);
            Date publishedDate = new Date(news.getPublishedDate());
            date.setText(DateUtils.getRelativeDateTimeString(this, publishedDate.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR));
            text = findViewById(R.id.text_full);
            text.setText(news.getPreview());
            this.setTitle(news.getSection());
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }

}
