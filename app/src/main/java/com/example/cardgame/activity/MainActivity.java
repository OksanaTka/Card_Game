package com.example.cardgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cardgame.R;

import java.io.IOException;

public class MainActivity extends BaseActivity {

    private Button main_BTN_start;
    private Button main_BTN_scoreTable;
    private Button main_BTN_exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setAudio(this, MAIN_ACTIVITY);
        findViews();

        main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewGameActivity(MainActivity.this);
            }
        });
        main_BTN_scoreTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewScoreActivity(MainActivity.this);
            }
        });

        main_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpRelease = true;
                finish();
            }
        });
    }

    private void findViews() {
        main_BTN_start = findViewById(R.id.main_BTN_start);
        main_BTN_scoreTable = findViewById(R.id.main_BTN_scoreTable);
        main_BTN_exit = findViewById(R.id.main_BTN_exit);
    }

    private void openNewScoreActivity(Activity activity) {
        Intent myIntent = new Intent(activity, ScoreActivity.class);
        startActivity(myIntent);
    }

    private void openNewGameActivity(Activity activity) {
        Intent myIntent = new Intent(activity, GameActivity.class);
        startActivity(myIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startAudio();
        setAudioLooping();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAudio();
        prepareAudio();
        if (mpRelease == true)
            releaseAudio();
    }

}