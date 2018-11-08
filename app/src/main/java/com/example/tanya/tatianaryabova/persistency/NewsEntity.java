package com.example.tanya.tatianaryabova.persistency;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class NewsEntity {

    @PrimaryKey
    @NonNull
    private String id;

    private String section;
    private String title;
    private String url;
    private long publishedDate;
    private String preview;
    private String multimediaUrl;

    public String getId() { return id; }

    public String getSection(){
        return section;
    }

    public String getTitle(){
        return title;
    }

    public String getUrl(){
        return url;
    }

    public long getPublishedDate(){
        return publishedDate;
    }

    public String getPreview(){
        return preview;
    }

    public String getMultimediaUrl() { return multimediaUrl; }

    public void setId(String id) { this.id = id; }

    public void setSection(String section){ this.section = section; }

    public void setTitle(String title){ this.title = title; }

    public void setUrl(String url){ this.url = url; }

    public void setPublishedDate(long publishedDate){ this.publishedDate = publishedDate; }

    public void setPreview(String preview){ this.preview = preview; }

    public void setMultimediaUrl(String multimediaUrl){ this.multimediaUrl = multimediaUrl; }
}
