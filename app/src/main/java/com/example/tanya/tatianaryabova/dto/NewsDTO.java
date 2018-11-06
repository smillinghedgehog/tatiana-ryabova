package com.example.tanya.tatianaryabova.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

    public void setMultimediaUrl(String url) {
        this.multimedia = new ArrayList<>();
        MultimediaDTO multimediaDTO = new MultimediaDTO();
        multimediaDTO.setUrl(url);
        this.multimedia.add(multimediaDTO);
        this.multimedia.add(multimediaDTO);
        this.multimedia.add(multimediaDTO);
    }

    public void setSection(String section) { this.section = section; }

    public void setTitle(String title) { this.title = title; }

    public void setUrl(String url) { this.url = url; }

    public void setPublishedDate(Date date) { this.publishedDate = date; }

    public void setPreview(String preview) { this.preview = preview; }
}
