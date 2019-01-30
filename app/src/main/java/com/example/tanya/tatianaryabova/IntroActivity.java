package com.example.tanya.tatianaryabova;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tanya.tatianaryabova.fragments.IntroFragment;
import com.example.tanya.tatianaryabova.fragments.NewsListFragment;

import me.relex.circleindicator.CircleIndicator;

public class IntroActivity extends AppCompatActivity {

    private static String SHARED_PREF_NAME = "INTRO_SHARED_PREF";
    private static String OPEN_KEY = "TO_OPEN";
    private static int numberOfItems = 3;
    private IntroPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        SharedPreferences introPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor= introPref.edit();

        boolean toOpen = introPref.getBoolean(OPEN_KEY, true);
        if (toOpen){
            toOpen = false;
            openNews();
        } else{
            toOpen = true;

            Button getStarted = findViewById(R.id.get_started_btn);
            getStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNews();
                }
            });
            pagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
            ViewPager viewPager = findViewById(R.id.intro_view_pager);
            viewPager.setAdapter(pagerAdapter);

            CircleIndicator indicator = findViewById(R.id.intro_circle_indicator);
            indicator.setViewPager(viewPager);
        }
        editor.putBoolean(OPEN_KEY, toOpen);
        editor.apply();
    }

    private void openNews(){
        Intent openNews = new Intent(this, NewsListFragment.class);
        this.finish();
        startActivity(openNews);
    }

    public class IntroPagerAdapter extends FragmentPagerAdapter {

        public IntroPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public int getCount(){
            return numberOfItems;
        }

        @Override
        public Fragment getItem(int position){
            Log.v("PosFromAdapter", String.valueOf(position));
            return IntroFragment.newInstance(position);
        }

    }


}
