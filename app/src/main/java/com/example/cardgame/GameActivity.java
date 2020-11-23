package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private ImageButton game_IBTN_start;
    private ImageView game_IMG_card1;
    private ImageView game_IMG_card2;
    private TextView game_LBL_score1;
    private TextView game_LBL_score2;

    private Deck deck, cards2;
    private int score1, score2, best_score, theWinner;
    private int check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        score1 = 0;
        score2 = 0;
        check = 0;

        deck = new Deck();

        game_IBTN_start = findViewById(R.id.game_IBTN_play);
        game_IMG_card1 = findViewById(R.id.game_IMG_card1);
        game_IMG_card2 = findViewById(R.id.game_IMG_card2);
        game_LBL_score1 = findViewById(R.id.game_LBL_score1);
        game_LBL_score2 = findViewById(R.id.game_LBL_score2);

        game_IBTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if all the cards already showed
                if (check != Deck.DECK_SIZE / 2) {
                    showCardsAndGetScore();
                    check++;
                } else {
                    //get the winner and best score
                    getWinnerAndBestScore();
                    //open finish activity
                    openFinishActivity(GameActivity.this);
                }
            }
        });
    }

    //get the best score and the winner in the game
    private void getWinnerAndBestScore() {
        if (score1 > score2) {
            theWinner = 1;
            best_score = score1;
        } else if (score1 < score2) {
            theWinner = 2;
            best_score = score2;
        } else {
            //draw
            theWinner = 0;
            best_score = score1;
        }
    }

    //open new finishActivity
    private void openFinishActivity(Activity activity) {
        Intent myIntent = new Intent(activity, FinishActivity.class);
        //send best score and the winner to finishActivity
        myIntent.putExtra(FinishActivity.BEST_SCORE, best_score);
        myIntent.putExtra(FinishActivity.WINNER, theWinner);

        startActivity(myIntent);
        finish();
    }

    private void showCardsAndGetScore() {
        String first, second;

        // get two random cards
        first = deck.getNextCard();
        second = deck.getNextCard();
        if(first == null || second == null)
            return;

        //set image of the cards
        game_IMG_card1.setImageResource(getImage(first));
        game_IMG_card2.setImageResource(getImage(second));

        //get cards score
        getScore(first, second);
    }

    // compare cards value
    // increase player score
    private void getScore(String first, String second) {
        if (deck.compareCards(first, second) > 0) {
            score1++;
            game_LBL_score1.setText("" + score1);
        }
        if (deck.compareCards(first, second) < 0) {
            score2++;
            game_LBL_score2.setText("" + score2);
        }
    }

    // get image by card name
    private int getImage(String img_name) {
        int img = getResources().getIdentifier(img_name, "drawable", getPackageName());
        return img;
    }

}

