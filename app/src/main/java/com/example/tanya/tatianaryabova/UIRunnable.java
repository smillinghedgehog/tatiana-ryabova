package com.example.tanya.tatianaryabova;

import com.example.tanya.tatianaryabova.models.News;

import java.util.List;

public abstract class UIRunnable implements Runnable {
    protected List<News> news;

    public void getNews(List<News> news){
        this.news = news;
    }
}
