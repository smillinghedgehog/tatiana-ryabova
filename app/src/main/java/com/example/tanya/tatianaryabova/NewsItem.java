package com.example.tanya.tatianaryabova;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.tanya.tatianaryabova.dto.NewsDTO;

public class NewsItem extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RequestManager imageLoader;
    private ImageView photo;
    private TextView category;
    private TextView title;
    private TextView preview;
    private TextView date;
    private Context context;
    private String newsUrl;
    private String newsTitle;

    private String NEWS_ID = "NEWS_ID";

    public static NewsItem create(@NonNull ViewGroup parent, RequestManager glideRequestManager, Context context) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news_item, parent, false);
        return new NewsItem(view, glideRequestManager, context);
    }

    private NewsItem(@NonNull View itemView, RequestManager glideRequestManager, Context context) {
        super(itemView);
        this.imageLoader = glideRequestManager;
        this.context = context;
        findViews(itemView);
        itemView.setOnClickListener(this);
    }

    public void bindItem(@NonNull NewsDTO news) {
        if (news.getMultimedia() != null && news.getMultimedia().size() != 0) {
            imageLoader.load(news.getMultimediaUrl())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .thumbnail(0.3f)
                    .into(photo);
        } else {
            photo.setVisibility(View.GONE);
        }

        category.setText(news.getSection());
        title.setText(news.getTitle());
        preview.setText(news.getPreview());
        date.setText(DateUtils.getRelativeDateTimeString(context, news.getPublishedDate().getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR));
        newsUrl = news.getUrl();
        newsTitle = news.getTitle();
    }

    @Override
    public void onClick(View view) {
        Intent openFull = new Intent(context, FullNewsActivity.class);
        openFull.putExtra(NEWS_ID, newsTitle + newsUrl);
        context.startActivity(openFull);
    }

    private void findViews(@NonNull View itemView) {
        photo = itemView.findViewById(R.id.image_news);
        category = itemView.findViewById(R.id.category);
        title = itemView.findViewById(R.id.title);
        preview = itemView.findViewById(R.id.preview_news);
        date = itemView.findViewById(R.id.date);
    }
}
