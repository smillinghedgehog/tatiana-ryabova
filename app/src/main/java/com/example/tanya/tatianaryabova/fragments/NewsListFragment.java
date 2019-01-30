package com.example.tanya.tatianaryabova.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.tanya.tatianaryabova.AboutActivity;
import com.example.tanya.tatianaryabova.NewsRecyclerAdapter;
import com.example.tanya.tatianaryabova.R;
import com.example.tanya.tatianaryabova.dto.NewsDTO;
import com.example.tanya.tatianaryabova.network.DefaultResponse;
import com.example.tanya.tatianaryabova.network.RestApi;
import com.example.tanya.tatianaryabova.persistency.Converter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private Spinner spinner;
    private FloatingActionButton updateBtn;
    private String[] sections = new String[26];
    private int sectionPosition = 0;
    private Converter converter;
    private View view;
    private String newsID;

    @Nullable
    private Call<DefaultResponse<List<NewsDTO>>> loadRequest;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news_list, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.about_menu_item:
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);

        }
        return true;
    }

    private void setupUI(){
        view = getView();
        recyclerView = view.findViewById(R.id.news_activity);
        progressBar = view.findViewById(R.id.progress_bar);
        setRecyclerView();
        setSpinner();
        setSections();
        setUpdate();
        converter = new Converter(getActivity());
        progressBar.setVisibility(View.VISIBLE);
        loadNews(sections[sectionPosition]);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void loadNews(String section){
        loadRequest = RestApi.getInstance().news().results(section);

        loadRequest.enqueue(new Callback<DefaultResponse<List<NewsDTO>>>() {
            @Override
            public void onResponse(Call<DefaultResponse<List<NewsDTO>>> call, Response<DefaultResponse<List<NewsDTO>>> response) {

                final DefaultResponse<List<NewsDTO>> body = response.body();

                final List<NewsDTO> results = body.getResults();

                Collections.sort(results, new Comparator<NewsDTO>() {
                            @Override
                            public int compare(NewsDTO newsDTO, NewsDTO t1) {
                                return t1.getPublishedDate().compareTo(newsDTO.getPublishedDate());
                            }
                        });

                adapter.addNews(results);

                recyclerView.scrollToPosition(0);

                converter.toDatabase(results);

                progressBar.setVisibility(View.INVISIBLE);

                if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    newsID = results.get(0).getTitle() + results.get(0).getUrl();
                    FullNewsFragment firstNewsFragment = FullNewsFragment.newInstance(newsID);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_frame, firstNewsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse<List<NewsDTO>>> call, Throwable t) {
                Log.v("MESSAGE", t.getMessage());

                List<NewsDTO> resultsFromDataBase = converter.fromDatabase();
                if(resultsFromDataBase != null){
                    adapter.addNews(resultsFromDataBase);
                }

                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setRecyclerView(){
        int spanCount = 1;

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        adapter = new NewsRecyclerAdapter(getActivity(), Glide.with(this));
        recyclerView.setAdapter(adapter);

    }

    private void setSpinner(){
        getActivity().findViewById(R.id.news_toolbar).setVisibility(View.VISIBLE);
        spinner = getActivity().findViewById(R.id.sections);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.section_array, R.layout.activity_spinner_main_item);
        adapter.setDropDownViewResource(R.layout.activity_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadNews(sections[i]);
                sectionPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUpdate(){
        updateBtn = view.findViewById(R.id.update_btn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loadNews(sections[sectionPosition]);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setSections(){
        sections[0] = "home";
        sections[1] = "opinion";
        sections[2] = "world";
        sections[3] = "national";
        sections[4] = "politics";
        sections[5] = "upshot";
        sections[6] = "nyregion";
        sections[7] = "business";
        sections[8] = "technology";
        sections[9] = "science";
        sections[10] = "health";
        sections[11] = "sports";
        sections[12] = "arts";
        sections[13] = "books";
        sections[14] = "movies";
        sections[15] = "theater";
        sections[16] = "sundayreview";
        sections[17] = "fashion";
        sections[18] = "tmagazine";
        sections[19] = "food";
        sections[20] = "travel";
        sections[21] = "magazine";
        sections[22] = "realestate";
        sections[23] = "automobiles";
        sections[24] = "obituaries";
        sections[25] = "insider";
    }

}
