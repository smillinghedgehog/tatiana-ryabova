package com.example.tanya.tatianaryabova;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanya.tatianaryabova.models.News;
import com.example.tanya.tatianaryabova.utils.DataUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class FullNewsActivity extends AppCompatActivity {

    private String NEWS_URL = "NEWS_URL";
    private String SECTION = "SECTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        String newsUrl = getIntent().getStringExtra(NEWS_URL);
        String section = getIntent().getStringExtra(SECTION);

        WebView fullNews = new WebView(this);
        setContentView(fullNews);
        fullNews.loadUrl(newsUrl);
        setTitle(section);
    }

}
