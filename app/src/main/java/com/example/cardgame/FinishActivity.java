package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    public static final String BEST_SCORE = "BEST_SCORE";
    public static final String WINNER = "WINNER";


    private TextView finish_LBL_score;
    private ImageView finish_IMG_winner;
    private TextView finish_LBL_playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_finish);

        finish_LBL_score = findViewById(R.id.finish_LBL_score);
        finish_IMG_winner = findViewById(R.id.finish_IMG_winner);
        finish_LBL_playerName = findViewById(R.id.finish_LBL_playerName);

        showResults();
    }

    private void showResults(){
        int scoreFromGameActivity = getIntent().getIntExtra(BEST_SCORE, -1);
        // set Score
        finish_LBL_score.setText("Best Score: " + scoreFromGameActivity);
        int winnerFromGameActivity = getIntent().getIntExtra(WINNER, -1);

        if(winnerFromGameActivity != 0){
            //get winner image
            int imgWinner = getWinnerImage(winnerFromGameActivity);
            //set winner image
            finish_IMG_winner.setImageResource(imgWinner);
        }

    }
    private int getWinnerImage(int winner) {
        String url = "drawable/" + "player_" + winner;
        finish_LBL_playerName.setText("player " + winner);
        int img = getResources().getIdentifier(url, "drawable", getPackageName());
        return img;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
