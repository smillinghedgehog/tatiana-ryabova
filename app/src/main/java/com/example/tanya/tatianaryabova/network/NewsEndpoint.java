package com.example.tanya.tatianaryabova.network;

import android.support.annotation.NonNull;

import com.example.tanya.tatianaryabova.dto.NewsDTO;
import com.example.tanya.tatianaryabova.network.DefaultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsEndpoint {

    @NonNull
    @GET("/svc/topstories/v2/{section}.json")
    Call<DefaultResponse<List<NewsDTO>>> results(@Path("section") String section);
}
