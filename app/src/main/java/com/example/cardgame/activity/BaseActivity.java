package com.example.cardgame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import com.example.cardgame.R;
import com.example.cardgame.utils.MyScreenUtils;

import java.io.IOException;

public class BaseActivity extends AppCompatActivity {
    public static final String MAIN_ACTIVITY = "MAIN_ACTIVITY";
    public static final String GAME_ACTIVITY = "GAME_ACTIVITY";
    public static final String FINISH_ACTIVITY = "FINISH_ACTIVITY";

    protected MediaPlayer mp;
    protected Boolean mpRelease = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyScreenUtils.hideSystemUI2(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            MyScreenUtils.hideSystemUI2(this);
        }
    }

    //set audio
    protected void setAudio(Activity activity, String name) {
        if (name == MAIN_ACTIVITY) {
            mp = MediaPlayer.create(activity, R.raw.opening_melody);
        }
        if (name == GAME_ACTIVITY) {
            mp = MediaPlayer.create(activity, R.raw.card_playing);
        }
        if (name == FINISH_ACTIVITY) {
            mp = MediaPlayer.create(activity, R.raw.winner_melody);
        }
    }

    protected void startAudio() {
        mp.start();
    }

    protected void prepareAudio() {
        mp.prepareAsync();
    }


    protected void setAudioLooping() {
        mp.setLooping(true);
    }

    protected void stopAudio() {
        mp.stop();
    }

    protected void pauseAudio() {
        mp.pause();
    }

    protected void releaseAudio() {
        mp.reset();
        mp.release();
    }

    protected boolean isPlaying() {
        return mp.isPlaying();
    }

}