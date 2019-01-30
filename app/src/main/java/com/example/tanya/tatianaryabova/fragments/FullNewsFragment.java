package com.example.tanya.tatianaryabova.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanya.tatianaryabova.R;
import com.example.tanya.tatianaryabova.persistency.Converter;
import com.example.tanya.tatianaryabova.persistency.NewsEntity;

import java.util.Date;

public class FullNewsFragment extends Fragment {

    private static final String NEWS_ID = "NEWS_ID";
    private Converter converter;
    private TextView title;
    private TextView text;
    private String newsID;
    private NewsEntity news;
    private CoordinatorLayout root;

    public static FullNewsFragment newInstance(String newsID){
        FullNewsFragment fullNewsFragment = new FullNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_ID, newsID);
        fullNewsFragment.setArguments(bundle);

        return fullNewsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_full_news, container, false);
        newsID = getArguments().getString(NEWS_ID);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity.setSupportActionBar((Toolbar) view.findViewById(R.id.full_news_toolbar));
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActivity().findViewById(R.id.news_toolbar).setVisibility(View.GONE);
            view.findViewById(R.id.full_news_toolbar).setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.full_news_toolbar).setVisibility(View.GONE);
            getActivity().findViewById(R.id.news_toolbar).setVisibility(View.VISIBLE);
        }

        converter = new Converter(getActivity());

        openFull(newsID);

        root = getActivity().findViewById(R.id.full_news_layout);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_full_news, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_menu_item:
                title.setVisibility(View.GONE);
                final EditText titleEdit = getActivity().findViewById(R.id.title_edit);
                titleEdit.setText(title.getText());
                titleEdit.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);
                final EditText textEdit = getActivity().findViewById(R.id.text_full_edit);
                textEdit.setText(text.getText());
                textEdit.setVisibility(View.VISIBLE);
                final Button saveBtn = getActivity().findViewById(R.id.save_btn);
                saveBtn.setVisibility(View.VISIBLE);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        converter.deleteNewsByID(newsID);
                        NewsEntity editedNews = new NewsEntity();
                        newsID = titleEdit.getText().toString() + news.getUrl();
                        editedNews.setId(newsID);
                        editedNews.setTitle(titleEdit.getText().toString());
                        editedNews.setUrl(news.getUrl());
                        editedNews.setSection(news.getSection());
                        editedNews.setPreview(textEdit.getText().toString());
                        editedNews.setMultimediaUrl(news.getMultimediaUrl());
                        editedNews.setPublishedDate(news.getPublishedDate());
                        converter.insertNews(editedNews);

                        title.setText(titleEdit.getText().toString());
                        text.setText(textEdit.getText().toString());
                        title.setVisibility(View.VISIBLE);
                        titleEdit.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        textEdit.setVisibility(View.GONE);
                        saveBtn.setVisibility(View.GONE);
                    }
                });
                return true;

            case R.id.delete_menu_item:
                converter.deleteNewsByID(newsID);

                Intent backToNews = new Intent(getActivity(), NewsListFragment.class);
                startActivity(backToNews);
                return true;
        }
        return false;
    }

    private void openFull(String newsID) {
        news = converter.findNewsById(newsID);

        if (news != null) {
            ImageView photo = getActivity().findViewById(R.id.full_news_photo);
            if (news.getMultimediaUrl() != null) {
                Glide.with(this).load(news.getMultimediaUrl()).into(photo);
            }
            title = getActivity().findViewById(R.id.title_full);
            title.setText(news.getTitle());
            TextView date = getActivity().findViewById(R.id.date_full);
            Date publishedDate = new Date(news.getPublishedDate());
            date.setText(DateUtils.getRelativeDateTimeString(getActivity(), publishedDate.getTime(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_YEAR));
            text = getActivity().findViewById(R.id.text_full);
            text.setText(news.getPreview());
            Toolbar toolbar = getActivity().findViewById(R.id.full_news_toolbar);
            toolbar.setTitle(news.getSection());
            getActivity().findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }

}
