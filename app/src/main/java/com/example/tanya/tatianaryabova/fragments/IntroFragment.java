package com.example.tanya.tatianaryabova.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tanya.tatianaryabova.R;

public class IntroFragment extends Fragment {

    private int position;
    private static String POSITION = "POSITION";

    public static IntroFragment newInstance(int position){
        IntroFragment fragment = new IntroFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        Log.v(POSITION, String.valueOf(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(POSITION);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_fragment_intro, container, false);

        ImageView image = view.findViewById(R.id.intro_image);
        Log.v(POSITION, String.valueOf(position));
        switch (position){
            case 0:
                image.setImageResource(R.drawable.screenshot_intro);
                break;
            case 1:
                image.setImageResource(R.drawable.news_example);
                break;
            case 2:
                image.setImageResource(R.drawable.about_screenshot);
        }

        return view;
    }
}
