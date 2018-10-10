package com.example.tanya.tatianaryabova;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FullNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        int position = getIntent().getIntExtra("POSITION", 1);
        ImageView photo = findViewById(R.id.full_news_photo);
        Glide.with(this).load(DataUtils.generateNews().get(position).getImageUrl()).into(photo);
        TextView title = findViewById(R.id.title_full);
        title.setText(DataUtils.generateNews().get(position).getTitle());
        TextView date = findViewById(R.id.date_full);
        date.setText(DateUtils.getRelativeDateTimeString(this, DataUtils.generateNews().get(position).getPublishDate().getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR));
        TextView text = findViewById(R.id.text_full);
        text.setText(DataUtils.generateNews().get(position).getFullText());
        setTitle(DataUtils.generateNews().get(position).getCategory().getName());
    }
}
