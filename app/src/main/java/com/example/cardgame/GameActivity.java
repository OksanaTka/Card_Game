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

    private final int DECK_SIZE = 52;

    private ImageButton game_IBTN_start;
    private ImageView game_IMG_card1;
    private ImageView game_IMG_card2;
    private TextView game_LBL_score1;
    private TextView game_LBL_score2;

    private Cards cards;
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
        cards = new Cards();
        initializeCardsDeck(cards);

        game_IBTN_start = findViewById(R.id.game_IBTN_play);
        game_IMG_card1 = findViewById(R.id.game_IMG_card1);
        game_IMG_card2 = findViewById(R.id.game_IMG_card2);
        game_LBL_score1 = findViewById(R.id.game_LBL_score1);
        game_LBL_score2 = findViewById(R.id.game_LBL_score2);

        game_IBTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all the cards showed
                if (check != DECK_SIZE / 2) {
                    showCardsAndGetScore();
                    check++;
                } else {
                    getWinnerAndBestScore();
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
        int first = 0, second = 0;

        // get two random cards
        first = randomCards();
        second = randomCards();

        //set image of the cards
        game_IMG_card1.setImageResource(first);
        game_IMG_card2.setImageResource(second);

        //get cards score
        getScore(first, second);
    }

    // compare cards value
    // increase player score
    private void getScore(int first, int second) {
        if (cards.compareCards(first, second) > 0) {
            score1++;
            game_LBL_score1.setText("" + score1);
        }
        if (cards.compareCards(first, second) < 0) {
            score2++;
            game_LBL_score2.setText("" + score2);
        }
    }

    private int randomCards() {
        int rand = (int) (Math.random() * DECK_SIZE) + 1;

        // get an unused card
        while (cards.checkCard(rand) == true)
            rand = (int) (Math.random() * DECK_SIZE) + 1;

        //mark card as used
        cards.setDeck(rand);
        //return image
        return getImage(rand);
    }


    private void initializeCardsDeck(Cards cards) {
        int cardValue = 2;

        // add all the cards to HashMap in Cards class
        // key- card image
        // value- card value
        for (int i = 1; i <= DECK_SIZE; i++) {
            cards.addCard(getImage(i), cardValue);
            //cards with the same number have the same value
            if ((i % 4) == 0)
                cardValue++;
        }
    }

    // get image by card id (i)
    private int getImage(int img_num) {
        String url = "drawable/" + "poker_" + img_num;
        int img = getResources().getIdentifier(url, "drawable", getPackageName());
        return img;
    }
}

