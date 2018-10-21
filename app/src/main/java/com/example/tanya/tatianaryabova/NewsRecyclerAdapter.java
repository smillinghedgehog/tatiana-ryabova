package com.example.tanya.tatianaryabova;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanya.tatianaryabova.models.News;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    private final List<News> news;
    private final Context context;
    private final LayoutInflater inflater;
    private final String POSITION = "POSITION";

    public NewsRecyclerAdapter(Context context, List<News> news) {
        this.news = news;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView photoView;
        public final TextView category;
        public final TextView title;
        public final TextView preview;
        public final TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            photoView = itemView.findViewById(R.id.image_news);
            category = itemView.findViewById(R.id.category);
            title = itemView.findViewById(R.id.title);
            preview = itemView.findViewById(R.id.preview_news);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int position = getLayoutPosition();
            openFullNews(position);
        }

        private void openFullNews(int position){
            Intent openFull = new Intent(context, FullNewsActivity.class);
            openFull.putExtra(POSITION, position);
            context.startActivity(openFull);
        }

    }

    @Override
    public int getItemCount(){
        return news.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(inflater.inflate(R.layout.activity_news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        News news_one = news.get(position);

        holder.category.setText(news_one.getCategory().getName());
        holder.title.setText(news_one.getTitle());
        holder.preview.setText(news_one.getPreviewText());
        holder.date.setText(DateUtils.getRelativeDateTimeString(context, news_one.getPublishDate().getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR));
        Glide.with(context).load(news_one.getImageUrl()).into(holder.photoView);
    }
}
