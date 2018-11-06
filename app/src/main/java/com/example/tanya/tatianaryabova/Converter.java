package com.example.tanya.tatianaryabova;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.tanya.tatianaryabova.dto.NewsDTO;

import java.util.ArrayList;
import java.util.List;
import  java.util.Date;

public class Converter {

    private final Context context;

    public Converter(Context context){
        this.context = context;
    }

    public void toDatabase(final List<NewsDTO> newsList) {

        AppDatabase db = AppDatabase.getAppDatabase(context);

        List<NewsEntity> newsEntityList = getNewsEntityList(newsList);
        NewsEntity[] news = newsEntityList.toArray(new NewsEntity[newsEntityList.size()]);

        db.newsDao().deleteAll();
        db.newsDao().insertAll(news);
    }

    public List<NewsDTO> fromDatabase(){
        AppDatabase db = AppDatabase.getAppDatabase(context);

        return getNewsDTOList(db.newsDao().getAll());
    }

    public NewsEntity findNewsById(String id){
        AppDatabase db = AppDatabase.getAppDatabase(context);

        return db.newsDao().getNewsById(id);
    }

    private List<NewsEntity> getNewsEntityList(List<NewsDTO> newsDTOList){
        List<NewsEntity> newsEntityList = new ArrayList<>();
        int size = newsDTOList.size();
        for(int i = 0; i < size; i++){
            NewsDTO newsDTO = newsDTOList.get(i);
            NewsEntity newsEntity = new NewsEntity();

            newsEntity.setId(newsDTO.getTitle() + newsDTO.getUrl());
            newsEntity.setTitle(newsDTO.getTitle());
            if (newsDTO.getMultimedia().size() != 0){
            newsEntity.setMultimediaUrl(newsDTO.getMultimediaUrl());
            }
            newsEntity.setPreview(newsDTO.getPreview());
            newsEntity.setPublishedDate(newsDTO.getPublishedDate().getTime());
            newsEntity.setSection(newsDTO.getTitle());
            newsEntity.setSection(newsDTO.getSection());
            newsEntity.setUrl(newsDTO.getUrl());

            newsEntityList.add(newsEntity);
        }

        return newsEntityList;
    }

    private List<NewsDTO> getNewsDTOList(List<NewsEntity> newsEntityList){
        List<NewsDTO> newsDTOList = new ArrayList<>();
        int size = newsEntityList.size();
        for(int i = 0; i < size; i++){
            NewsEntity newsEntity = newsEntityList.get(i);
            NewsDTO newsDTO = new NewsDTO();

            newsDTO.setTitle(newsEntity.getTitle());
            if (newsEntity.getMultimediaUrl() != null) {
                newsDTO.setMultimediaUrl(newsEntity.getMultimediaUrl());
            }
            newsDTO.setPreview(newsEntity.getPreview());
            newsDTO.setPublishedDate(new Date(newsEntity.getPublishedDate()));
            newsDTO.setSection(newsEntity.getTitle());
            newsDTO.setSection(newsEntity.getSection());
            newsDTO.setUrl(newsEntity.getUrl());

            newsDTOList.add(newsDTO);
        }

        return newsDTOList;
    }
}
