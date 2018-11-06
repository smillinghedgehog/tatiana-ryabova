package com.example.tanya.tatianaryabova;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanya.tatianaryabova.models.News;
import com.example.tanya.tatianaryabova.utils.DataUtils;

import java.lang.ref.WeakReference;
import java.util.List;

public class FullNewsActivity extends AppCompatActivity {

    private String NEWS_ID = "NEWS_ID";
    private Converter converter;
    private TextView title;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        String newsID = getIntent().getStringExtra(NEWS_ID);

        converter = new Converter(this);

        openFull(newsID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_full_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.edit_menu_item:
                title.setVisibility(View.GONE);
                EditText titleEdit = findViewById(R.id.title_edit);
                titleEdit.setText(title.getText());
                titleEdit.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);
                EditText textEdit = findViewById(R.id.text_full_edit);
                textEdit.setText(text.getText());
                textEdit.setVisibility(View.VISIBLE);
                Button saveBtn = findViewById(R.id.save_btn);
                saveBtn.setVisibility(View.VISIBLE);
        }
        return true;
    }

    private void openFull(String newsID){
        NewsEntity news = converter.findNewsById(newsID);

        if (news != null){
            ImageView photo = findViewById(R.id.full_news_photo);
            if(news.getMultimediaUrl() != null) {
                Glide.with(this).load(news.getMultimediaUrl()).into(photo);
            }
            title = findViewById(R.id.title_full);
            title.setText(news.getTitle());
            TextView date = findViewById(R.id.date_full);
           // date.setText(DateUtils.getRelativeDateTimeString(this, news.getPublishedDate()), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR));
            text = findViewById(R.id.text_full);
            text.setText(news.getPreview());
            this.setTitle(news.getSection());
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }

}
