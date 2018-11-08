package com.example.tanya.tatianaryabova;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    private static String SHARED_PREF_NAME = "INTRO_SHARED_PREF";
    private static String OPEN_KEY = "TO_OPEN";

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
        }
        editor.putBoolean(OPEN_KEY, toOpen);
        editor.apply();
    }

    private void openNews(){
        Intent openNews = new Intent(this, NewsListActivity.class);
        startActivity(openNews);
    }
}
