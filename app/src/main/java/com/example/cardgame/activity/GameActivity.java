package com.example.cardgame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cardgame.objects.Deck;
import com.example.cardgame.game.GamaManage;
import com.example.cardgame.R;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private ImageButton game_IBTN_start;
    private ImageView game_IMG_card1;
    private ImageView game_IMG_card2;
    private TextView game_LBL_score1;
    private TextView game_LBL_score2;
    private ProgressBar game_SB_statusBar;
    private GamaManage game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);


        game = new GamaManage();


        game_IBTN_start = findViewById(R.id.game_IBTN_play);
        game_IMG_card1 = findViewById(R.id.game_IMG_card1);
        game_IMG_card2 = findViewById(R.id.game_IMG_card2);
        game_LBL_score1 = findViewById(R.id.game_LBL_score1);
        game_LBL_score2 = findViewById(R.id.game_LBL_score2);
        game_SB_statusBar = findViewById(R.id.game_SB_statusBar);

        game_IBTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
                game_IBTN_start.setImageResource(R.drawable.ic_time);
                game_IBTN_start.setEnabled(false);

            }
        });
    }

    //open new finishActivity
    private void openFinishActivity(Activity activity) {
        Intent myIntent = new Intent(activity, FinishActivity.class);
        //send best score and the winner to finishActivity
        myIntent.putExtra(FinishActivity.BEST_SCORE, game.getBest_score());
        myIntent.putExtra(FinishActivity.WINNER, game.getWinner());

        startActivity(myIntent);
        finish();
    }

    private Timer carousalTimer;



    public void start() {
        carousalTimer = new Timer();
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //check if all the cards already showed
                        if (game.getCheck() != Deck.DECK_SIZE / 2) {
                            game.getCards();
                            showCards();
                            updateScore();
                            game.increaseCheck();
                            updateStatusBar();
                        } else {
                            stop();
                            //get the winner and best score
                            game.getWinnerAndBestScore();
                            //open finish com.example.cardgame.activity
                            openFinishActivity(GameActivity.this);
                        }
                    }
                });
            }
        }, 0, 600);
    }

    private void updateStatusBar() {
        game_SB_statusBar.setProgress(game.getCheck());
    }

    // update score
    private void updateScore() {
        int temp = game.getScore();
        if (temp > 0) {
            game_LBL_score1.setText("" + game.getScore1());
        }
        if (temp < 0) {
            game_LBL_score2.setText("" + game.getScore2());
        }
    }

    // show cards
    private void showCards() {
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound_card_turn_over);
//        mp.start();
        game_IMG_card1.setImageResource(getImage(game.getCard1().getCard_name()));

        game_IMG_card2.setImageResource(getImage(game.getCard2().getCard_name()));
    }

    private void playSound() {

    }

    // get image by card name
    private int getImage(String img_name) {
        int img = getResources().getIdentifier(img_name, "drawable", getPackageName());
        return img;
    }

    public void stop() {
        carousalTimer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
        finish();
    }
}

