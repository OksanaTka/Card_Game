package com.example.cardgame.activity;


import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.cardgame.R;
import com.example.cardgame.objects.Player;
import com.example.cardgame.objects.Record;
import com.example.cardgame.objects.TopTen;
import com.example.cardgame.utils.MyLocation;
import com.example.cardgame.utils.MySPV;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;


public class FinishActivity extends BaseActivity {


    public static final String RECORD = "RECORD";


    private TextView finish_LBL_score;
    private ImageView finish_IMG_winner;
    private TextView finish_LBL_playerName;
    private Button finish_BTN_backMenu;
    private Record winner;
    private MyLocation myLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        setAudio(this, FINISH_ACTIVITY);

        myLocation = new MyLocation(FinishActivity.this);
        winner = new Record();

        findViews();
        //show winner
        showResults();

        //add winner to Score Board
        if (!winner.getName().equals("Draw")) {
            updateScoreBoard();
        }
    }

    private void findViews() {
        finish_LBL_score = findViewById(R.id.finish_LBL_score);
        finish_IMG_winner = findViewById(R.id.finish_IMG_winner);
        finish_LBL_playerName = findViewById(R.id.finish_LBL_playerName);
        finish_BTN_backMenu = findViewById(R.id.finish_BTN_backMenu);
    }


    /*
    open score board
    add winner to score board
     */
    private void updateScoreBoard() {
        TopTen score_board;
        Gson gson = new Gson();

        String topTen = MySPV.getInstance().getString(MySPV.KEYS.KEY_SCORE_BOARD, null);
        if (topTen == null) {
            score_board = new TopTen();
        } else {
            score_board = gson.fromJson(topTen, TopTen.class);
        }
        if (score_board.addRecord(winner) == true) {
            String ttJson = gson.toJson(score_board);
            MySPV.getInstance().putString(MySPV.KEYS.KEY_SCORE_BOARD, ttJson);
            Toast.makeText(this, "You are added to TOP-TEN", Toast.LENGTH_SHORT).show();
        }
    }


    private void showResults() {
        winner = (Record) getIntent().getSerializableExtra("RECORD");
        //set score
        finish_LBL_score.setText("Best Score: " + winner.getScore());
        //set winner image
        finish_IMG_winner.setImageResource(getWinnerImage(winner.getId()));
        //set winner name
        finish_LBL_playerName.setText(winner.getName());


        finish_BTN_backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private int getWinnerImage(int winner) {
        String url = "drawable/" + "player_" + winner;
        int img = getResources().getIdentifier(url, "drawable", getPackageName());
        return img;
    }

    @Override
    protected void onStart() {
        super.onStart();
        startAudio();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isPlaying()) {
            stopAudio();
        }
        releaseAudio();
        finish();
    }
}
