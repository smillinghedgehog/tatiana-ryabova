package com.example.tanya.tatianaryabova.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class NewsDTO {

    private String section;
    private String title;
    private String url;
    @SerializedName("published_date")
    Date publishedDate;
    @SerializedName("abstract")
    String preview;
    private List<MultimediaDTO> multimedia;


    public String getSection(){
        return section;
    }

    public String getTitle(){
        return title;
    }

    public String getUrl(){
        return url;
    }

    public Date getPublishedDate(){
        return publishedDate;
    }

    public String getPreview(){
        return preview;
    }

    public String getMultimediaUrl(){
        return multimedia.get(2).getUrl();
    }

    public List<MultimediaDTO> getMultimedia(){
        return multimedia;
    }
}
