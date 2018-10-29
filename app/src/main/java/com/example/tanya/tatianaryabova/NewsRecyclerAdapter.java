package com.example.tanya.tatianaryabova;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.tanya.tatianaryabova.dto.NewsDTO;
import com.example.tanya.tatianaryabova.models.News;

import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsItem> {

    private RequestManager glideRequestManager;
    private final List<NewsDTO> news = new ArrayList<>();
    private final Context context;

    public NewsRecyclerAdapter(Context context, RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        return NewsItem.create(viewGroup, glideRequestManager, context);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItem newsItem, int position){
        final NewsDTO newsOne = news.get(position);
        newsItem.bindItem(newsOne);
    }

    public void addNews(@NonNull List<NewsDTO> news){
        this.news.clear();
        this.news.addAll(news);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return news.size();
    }

}
