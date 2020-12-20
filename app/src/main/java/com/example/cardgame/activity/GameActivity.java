package com.example.cardgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cardgame.game.GamaManage;
import com.example.cardgame.R;
import com.example.cardgame.objects.Player;
import com.example.cardgame.objects.Record;
import com.example.cardgame.utils.MyAlertDialog;
import com.example.cardgame.utils.MyLocation;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends BaseActivity implements MyAlertDialog.DialogListener {


    private ImageButton game_IBTN_start;
    private ImageView game_IMG_card1;
    private ImageView game_IMG_card2;
    private TextView game_LBL_score1;
    private TextView game_LBL_score2;
    private TextView game_LBL_player1;
    private TextView game_LBL_player2;
    private ProgressBar game_SB_statusBar;
    private GamaManage game;
    private MyLocation myLocation;
    private boolean hasStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //set background audio
        setAudio(this, GAME_ACTIVITY);
        //get location of the player
        myLocation = new MyLocation(this);

        findViews();
        // ask for players name
        openDialog();

            game_IBTN_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // set timer and start game
                    start();
                    stopButton();
                }
            });
    }

    //create and show Dialog
    public void openDialog() {
        MyAlertDialog dialog = new MyAlertDialog();
        dialog.setCancelable(false);

        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void applyTexts(String name_player1, String name_player2) {
        // create game
        game = new GamaManage(name_player1, name_player2);

        //set player's name
        game_LBL_player1.setText(game.getPlayer1().getName());
        game_LBL_player2.setText(game.getPlayer2().getName());
    }


    private void findViews() {
        game_IBTN_start = findViewById(R.id.game_IBTN_play);
        game_IMG_card1 = findViewById(R.id.game_IMG_card1);
        game_IMG_card2 = findViewById(R.id.game_IMG_card2);
        game_LBL_score1 = findViewById(R.id.game_LBL_score1);
        game_LBL_score2 = findViewById(R.id.game_LBL_score2);
        game_LBL_player1 = findViewById(R.id.game_LBL_player1);
        game_LBL_player2 = findViewById(R.id.game_LBL_player2);
        game_SB_statusBar = findViewById(R.id.game_SB_statusBar);

    }

    //set timer image
    //set button enabled
    private void stopButton() {
        game_IBTN_start.setImageResource(R.drawable.img_time);
        game_IBTN_start.setEnabled(false);
    }

    //open new finishActivity
    private void openFinishActivity(Activity activity) {
        Intent myIntent = new Intent(activity, FinishActivity.class);
        Player winner = game.getWinner();
        Record record = new Record(winner.getId(), winner.getName(), winner.getScore(), myLocation.getLat(), myLocation.getLng());
        //send winner record to finishActivity
        myIntent.putExtra(FinishActivity.RECORD, record);
        startActivity(myIntent);
        finish();
    }


    // create Timer
    private Timer carousalTimer;

    public void start() {
        carousalTimer = new Timer();
        hasStarted = true;
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //check if all the cards already showed
                        if (game.playGame()) {
                            showCards();
                            updateScore();
                            updateStatusBar();
                        } else {
                            //stop timer
                            stop();
                            //open finish activity
                            openFinishActivity(GameActivity.this);
                        }
                    }
                });
            }
        }, 0, 500);
    }

    //update StatusBar
    private void updateStatusBar() {
        game_SB_statusBar.setProgress(game.getCheck());
    }

    // update score
    private void updateScore() {
        game_LBL_score1.setText("" + game.getScore1());
        game_LBL_score2.setText("" + game.getScore2());
    }

    // show cards
    private void showCards() {
        startAudio();
        game_IMG_card1.setImageResource(getImage(game.getCard1().getCard_name()));
        game_IMG_card2.setImageResource(getImage(game.getCard2().getCard_name()));
    }


    // get image by card name
    private int getImage(String img_name) {
        int img = getResources().getIdentifier(img_name, "drawable", getPackageName());
        return img;
    }

    //Request for location Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == myLocation.PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myLocation.getLocation();
            }
        } else {
            myLocation.setLocationAccepted(false);
        }
    }

    //stop timer
    public void stop() {
        carousalTimer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // stop timer - if the player exit in the middle of the game
        if (hasStarted) {
            stop();
        }
        if (isPlaying()) {
            stopAudio();
        }
        releaseAudio();
        finish();
    }

}

